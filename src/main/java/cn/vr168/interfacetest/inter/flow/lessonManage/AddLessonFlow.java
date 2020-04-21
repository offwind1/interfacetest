package cn.vr168.interfacetest.inter.flow.lessonManage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.mizhu.web.course.FileExits;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.GetLessonInfoById;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.List;
import cn.vr168.interfacetest.inter.mizhumanage.web.lesson.AddByFile;
import cn.vr168.interfacetest.inter.mizhumanage.web.lesson.LessonUpfile;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.parameter.people.User;
import cn.vr168.interfacetest.kit.excelCreator.LessonNeedClassroomExcelCreator;
import cn.vr168.interfacetest.kit.util.SampleAssert;

import java.io.IOException;
import java.util.ArrayList;

public class AddLessonFlow {

    private static User user = Jigou.getInstance();
    private static String phoneTeacherFormat = Environment.getValue("phone.teacherFormat");
    private static int lessonCount = 4;

    /**
     * 新增课程流程
     */
    @Test(description = "检测，带课时导入课程，是否成功 和 数据验证")
    public void test() throws IOException {
        java.util.List<LessonNeedClassroomExcelCreator.Bean> beans = new ArrayList<>();
        for (int i = 0; i < lessonCount; i++) {
            LessonNeedClassroomExcelCreator.Bean lesson = LessonNeedClassroomExcelCreator.Bean.builder()
                    .lessonName("测试课程" + RandomUtil.randomString(6))
                    .startDate(DateUtil.date())
                    .teacherPhone(String.format(phoneTeacherFormat, i))
                    .classroomCount(RandomUtil.randomInt(1, 10))
                    .build();
            beans.add(lesson);
        }

        LessonNeedClassroomExcelCreator excelCreator = LessonNeedClassroomExcelCreator.of();

        for (LessonNeedClassroomExcelCreator.Bean lesson : beans) {
            excelCreator = excelCreator.addLesson(lesson);
        }

        byte[] bytes = excelCreator.build();

        //文件检查
        String md5 = MD5.create().digestHex(bytes);
        JSONObject fileExits = FileExits.of().fileExits(user.getToken(), md5);
        SampleAssert.assertCode200(fileExits);

        //上传文件
        JSONObject lessonUpfile = LessonUpfile.of().lessonUpfile(user.getToken(), user.getUserId(), bytes);
        SampleAssert.assertCode200(lessonUpfile);
        String batchNum = lessonUpfile.getStr("data");

        //通过文件添加课程
        JSONObject addByFile = AddByFile.of().addByFile(user.getToken(), user.getUserId(), batchNum, user.getOrgId());
        SampleAssert.assertCode200(addByFile);

        //列表
        JSONObject list = List.of().list(user.getToken());
        SampleAssert.assertCode200(list);

        java.util.List<String> lessonIds = new ArrayList<>();
        for (LessonNeedClassroomExcelCreator.Bean lesson : beans) {
            lessonIds.add(getLessonId(list, lesson.getLessonName()));
        }

        for (int i = 0; i < lessonIds.size(); i++) {
            checkLesson(beans.get(i), lessonIds.get(i));
        }

        //重复上传测试
        fileExits = FileExits.of().fileExits(user.getToken(), md5);
        SampleAssert.assertMsg(fileExits, "文件重复上传!");
    }


    private void checkLesson(LessonNeedClassroomExcelCreator.Bean lesson, String lessonId) {
        JSONObject lessonInfoById = GetLessonInfoById.of().getLessonInfoById(user.getToken(), lessonId);

        JSONObject lessonInfo = lessonInfoById.getJSONObject("data").getJSONObject("lessonInfo");
        JSONArray classroomList = lessonInfoById.getJSONObject("data").getJSONArray("classroomList");

        //年级是否正确
        SampleAssert.assertStr(lessonInfo, "gradeName", lesson.getGradeName());
        //学科是否正确
        SampleAssert.assertStr(lessonInfo, "lessonTypeName", lesson.getSubjectName());
        //课时数 是否正确
        assert lesson.getClassroomCount() == classroomList.size() : "课时数不正确";

        //课时
        for (int i = 0; i < classroomList.size(); i++) {
            JSONObject o = classroomList.getJSONObject(i);
            //课时名称
            assert o.getStr("classroomName").equals("课时" + String.valueOf(i + 1)) : "课时名称不正确";
            //课时时间
            assert o.getStr("startTime").equals(DateUtil.offsetDay(lesson.getStartDate(), i).toString("yyyy-MM-dd hh:mm:" + "00")) : "开课时间不正确";
            assert o.getStr("factStartTime").equals(DateUtil.offsetDay(lesson.getStartDate(), i).toString("yyyy-MM-dd hh:mm:" + "00")) : "开课时间不正确";
            //教师
            assert o.getStr("teacherPhone").equals(lesson.getTeacherPhone()) : "教师手机号不正确";

            assert o.getInt("classroomOrdery") == i;
        }
    }

    private String getLessonId(JSONObject object, String lessonName) {
        System.out.println(lessonName);

        //测试课程9ywxae
        //测试课程h7a99i
        for (JSONObject o : object.getJSONObject("data").getJSONArray("list").jsonIter()) {
            if (lessonName.equals(o.getStr("lessonName"))) {
                return o.getStr("lessonId");
            }
        }
        throw new RuntimeException("未查询到课程");
    }

}
