package cn.vr168.interfacetest.kit.page;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.ClassroomEnd;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.ClassroomStart;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import lombok.Builder;
import lombok.Data;

public class LessonPage {
    @Data
    @Builder
    public static class ClassStartInfo {
        private String classroomVideoId;
        private String teacherCloudeAccount;
        private String classroomCode;
    }

    public static ClassStartInfo startClass(Lesson lesson) {
        JSONObject classroomStart = ClassroomStart.of().classroomStart(Jigou.getInstance().getToken(), lesson.getClassRoom(0).getClassroomId());
        SampleAssert.assertMsg(classroomStart, "开课成功");
        String classroomVideoId = classroomStart.getJSONObject("data").getStr("classroomVideoId");
        String teacherCloudeAccount = classroomStart.getJSONObject("data").getJSONObject("classroomInfo").getStr("teacherCloudeAccount");
        String classroomCode = classroomStart.getJSONObject("data").getJSONObject("classroomInfo").getStr("classroomCode");
        return ClassStartInfo.builder().classroomCode(classroomCode).teacherCloudeAccount(teacherCloudeAccount).classroomVideoId(classroomVideoId).build();
    }

    public static void endClass(ClassStartInfo info) {
        JSONObject end = ClassroomEnd.of().classroomEnd(Jigou.getInstance().getToken(),
                info.classroomVideoId, info.teacherCloudeAccount);
        SampleAssert.assertResult0(end);
    }
}
