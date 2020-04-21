package cn.vr168.interfacetest.inter.flow.lessonManage;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.mizhu.web.grade.AddClass;
import cn.vr168.interfacetest.inter.mizhu.web.grade.ClassList;
import cn.vr168.interfacetest.inter.mizhu.web.grade.DelClass;
import cn.vr168.interfacetest.inter.mizhu.web.grade.UserByStuId;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.*;
import cn.vr168.interfacetest.inter.mizhu.web.usr.LessonStudent;
import cn.vr168.interfacetest.kit.excelCreator.StudentExcelCreator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.parameter.people.Student;
import cn.vr168.interfacetest.kit.util.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class LessonAddStudentFlow {
    private static String phoneTeacherFormat = Environment.getValue("phone.teacherFormat");


    private static String token = Jigou.getInstance().getToken();


    private List<String> getClasses(int count) {
        JSONObject classList = ClassList.of().classList(token, Jigou.getInstance().getOrgName());

        List<String> list = classList.getJSONObject("data").getJSONArray("list").stream().filter(i -> {
            JSONObject o = (JSONObject) i;
            if (o.getInt("studentCount") > 0) {
                return true;
            }
            return false;
        }).map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("stuId");
        }).collect(Collectors.toList());
        if (count > list.size()) {
            count = list.size();
        }
        return list.subList(0, count);
    }

    /**
     * 通过班级添加学生
     * 断言：
     * *检查班级是否加入
     * *检查学生是否加入
     * *检查数据库是否有重复
     */
    @Test(description = "通过班级添加学生", groups = {"database", "select"})
    public void test() throws IOException {
        Lesson lesson = LessonFactory.creat();
        List<String> classIds = getClasses(2);
        System.out.println(classIds);

        JSONObject studentByClassId = StudentByClassId.of().studentByClassId(
                token,
                lesson.getLessonId(),
                String.join(",", classIds));
        SampleAssert.assertCode200(studentByClassId);
        Set<String> studentInClass = new HashSet<>();

        for (String classId : classIds) {
            studentInClass.addAll(getStudentFromClassId(classId));
        }

        // 学生检查
        JSONObject lessonStudent = LessonStudent.of().lessonStudent(token, lesson.getLessonId(), 500);
        Set<String> studentInLesson = lessonStudent.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("userId");
        }).collect(Collectors.toSet());

        assert studentInClass.equals(studentInLesson);

        // 班级id检查
        JSONObject object = GroupByClass.of().groupByClass(token, lesson.getLessonId());
        Set<String> set = object.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("stuId");
        }).collect(Collectors.toSet());

        assert set.equals(new HashSet<>(classIds));

        // 查看是否能重复导入
        String ids = String.join(",", new ArrayList<>(studentInClass).subList(0, 10));
        JSONObject addTeacherStudent = AddTeacherStudent.of().addTeacherStudent(token, lesson.getLessonId(), String.join(",", ids));
        SampleAssert.assertMsg(addTeacherStudent, "添加失败，重复添加！");

        //数据库检查 是否重复
        try {
            List<Entity> list = DBUtil.selectClassroomDiligent(lesson.getLessonId(), lesson.getClassRoom(0).getClassroomId());
            List<String> user_ids = list.stream().map(i -> {
                return i.getStr("user_id");
            }).collect(Collectors.toList());

            Set<String> dbSet = new HashSet<String>(user_ids);
            assert user_ids.size() == dbSet.size() : "数据库中的数据有重复: 数量" + String.valueOf(user_ids.size() - dbSet.size());
            assert studentInClass.equals(dbSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String classId;

    /**
     * 从课程中添加学生到班级。
     * 班级需要同步添加的新学生
     *
     * @return
     */
    @Test
    public void test1() throws InterruptedException {
        Lesson lesson = LessonFactory.creat();
        // 新增班级
        JSONObject addClass = AddClass.of().addClass(Jigou.getInstance(), "班级名称" + RandomUtil.randomString(6), "1");
        SampleAssert.assertCode200(addClass);
        classId = addClass.getJSONObject("data").getStr("classId");

        //上传文件
        StudentExcelCreator excelCreator = StudentExcelCreator.of();
        excelCreator = excelCreator.addStudent("新生娃%04d", "baby%04d", "111111", 5);
        JSONObject uploadFile = UploadFile.of().uploadFile(token, excelCreator.build());
        SampleAssert.assertCode200(uploadFile);
        String fileName = uploadFile.getJSONObject("data").getStr("fileName");

        Thread.sleep(1000);
        //通过文件，添加学生
        JSONObject jsonObject = JoinClassByFile.of().joinClassByFile(token, fileName, classId);
        SampleAssert.assertCode200(jsonObject);

        JSONObject studentByClassId = StudentByClassId.of().studentByClassId(
                token,
                lesson.getLessonId(),
                classId);
        SampleAssert.assertCode200(studentByClassId);

        JSONObject addTeacherStudent = AddTeacherStudent.of().addTeacherStudent(AddTeacherStudent.Bean.builder()
                .ids(Student.getInstance().getUserId())
                .lessonId(lesson.getLessonId())
                .stuId(classId)
                .token(token)
                .build());
        SampleAssert.assertCode200(addTeacherStudent);

        assert getStudentFromClassId(classId).contains(Student.getInstance().getUserId());

        //再次添加
        JSONObject addTeacherStudentAgain = AddTeacherStudent.of().addTeacherStudent(AddTeacherStudent.Bean.builder()
                .ids(Student.getInstance().getUserId())
                .lessonId(lesson.getLessonId())
                .stuId(classId)
                .token(token)
                .build());
        SampleAssert.assertMsg(addTeacherStudentAgain, "添加失败，重复添加！");

        JSONObject delClass = DelClass.of().delClass(token, classId);
        SampleAssert.assertCode200(delClass);
    }


    @AfterClass
    public void afterClass() {
        if (classId != null) {
            JSONObject delete = DelClass.of().delClass(Jigou.getInstance().getToken(), classId);
            SampleAssert.assertCode200(delete);
        }
    }


    private Set<String> getStudentFromClassId(String classId) {
        JSONObject userByStuId = UserByStuId.of().userByStuId(token, classId, 500);
        Set<String> students = userByStuId.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("studentId");
        }).collect(Collectors.toSet());

        System.out.println(students);
        return students;
    }


}
