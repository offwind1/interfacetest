package cn.vr168.interfacetest.inter.flow.lessonManage.student;

import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.api.classChart.ExportByStudent;
import cn.vr168.interfacetest.inter.mizhu.api.lessonInfo.LessonInfo;
import cn.vr168.interfacetest.inter.mizhu.web.classroom.ClassroomOption;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.AddTeacherStudent;
import cn.vr168.interfacetest.inter.mizhu.web.refund.TeacherDelStu2;
import cn.vr168.interfacetest.inter.mizhu.web.usr.LessonStudent;
import cn.vr168.interfacetest.inter.mizhu.web.usr.TeacherSearchUser;
import cn.vr168.interfacetest.kit.factory.ClassFactory;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.Clazz;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.parameter.people.Student;
import io.qameta.allure.Step;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BasiceStuendTestFlow {

    private Lesson lesson = LessonFactory.creat();
    private Clazz clazz = ClassFactory.findClass();


    public Set<String> lessonStudent(Lesson lesson, String key) {
        JSONObject lessonStudent = LessonStudent.of().lessonStudent(Jigou.getInstance().getToken(), lesson.getLessonId());
        SampleAssert.assertCode200(lessonStudent);

        return lessonStudent.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr(key);
        }).collect(Collectors.toSet());
    }

    public Boolean studentInLesson(Student student, Lesson lesson) {
        JSONObject object = LessonInfo.of().lessonInfo(student.getToken(), lesson.getLessonId());
        SampleAssert.assertResult0(object);
        return object.getJSONObject("data").getStr("userPay").equals("1");
    }

    @Test
    public void addStudent() {
        lesson.addStudentByStuId(clazz.getStuId());

        Set<String> in_class_set = clazz.getStudents().stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("account");
        }).collect(Collectors.toSet());

        Set<String> in_lesson_set = lessonStudent(lesson, "account");
        assert in_class_set.equals(in_lesson_set) : "课堂学生和班级学生不匹配";
        assert studentInLesson(new Student("baby0001", "111111"), lesson) : "学生没有获得权限";
    }

    @Test(dependsOnMethods = {"addStudent"})
    public void addStudentOnly() {
        JSONObject searchUser = TeacherSearchUser.of().teacherSearchUser(Jigou.getInstance().getToken(), "robot0001");
        String userId = searchUser.getJSONObject("data").getJSONArray("list").getJSONObject(0).getStr("userId");

        JSONObject object = AddTeacherStudent.of().addTeacherStudent(AddTeacherStudent.Bean.builder()
                .ids(userId)
                .lessonId(lesson.getLessonId())
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertCode200(object);

        Set<String> in_lesson_set = lessonStudent(lesson, "userId");
        assert in_lesson_set.contains(userId) : "学生不存在";
        assert studentInLesson(new Student("robot0001", "111111"), lesson) : "学生没有获得权限";
    }

    @Test(dependsOnMethods = {"addStudent", "addStudentOnly"})
    public void delStudentOnly() {
        JSONObject searchUser = TeacherSearchUser.of().teacherSearchUser(Jigou.getInstance().getToken(), "robot0001");
        String userId = searchUser.getJSONObject("data").getJSONArray("list").getJSONObject(0).getStr("userId");

        JSONObject object = TeacherDelStu2.of().teacherDelStu2(Jigou.getInstance().getToken(),
                lesson.getLessonId(),
                userId);

        Set<String> in_lesson_set = lessonStudent(lesson, "userId");
        assert !in_lesson_set.contains(userId) : "学生还存在";
        assert !studentInLesson(new Student("robot0001", "111111"), lesson) : "删除后学生依旧获得了权限";
    }


    @Test(dependsOnMethods = {"addStudent", "addStudentOnly", "delStudentOnly"})
    public void searchStudent() {
        JSONObject lessonStudent = LessonStudent.of().lessonStudent(LessonStudent.Bean.builder()
                .currentPage("1")
                .pageSize("15")
                .lessonId(lesson.getLessonId())
                .token(Jigou.getInstance().getToken())
                .accounts("baby0001")
                .build());

        Set<String> set = lessonStudent.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("account");
        }).collect(Collectors.toSet());

        assert set.size() == 1;
        assert set.contains("baby0001");
    }


    @Test(dependsOnMethods = {"addStudent", "addStudentOnly", "delStudentOnly"})
    public void exportStudent() throws IOException {
        HttpResponse response = ExportByStudent.of().export(Jigou.getInstance().getToken(), lesson.getLessonId());
        HSSFWorkbook workbook = new HSSFWorkbook(response.bodyStream());
        HSSFSheet sheet = workbook.getSheetAt(0);

        Set<String> in_lesson_set = lessonStudent(lesson, "account");
        Set<String> set = new HashSet<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);

            assert row.getCell(0).getStringCellValue().equals(Jigou.getInstance().getOrgName()) : "机构名称不正确";
            assert row.getCell(1).getStringCellValue().equals(clazz.getClassName()) : "班级名称不正确";
            assert in_lesson_set.contains(row.getCell(2).getStringCellValue()) : "账号不存在";
            set.add(row.getCell(2).getStringCellValue());
        }
        assert set.equals(in_lesson_set) : "学生数量不正确";

    }
}
