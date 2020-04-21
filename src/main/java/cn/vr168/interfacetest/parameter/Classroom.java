package cn.vr168.interfacetest.parameter;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.inter.mizhu.web.classroom.Edit;
import cn.vr168.interfacetest.inter.mizhu.web.classroom.SetTeacher;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.GetLessonInfoById;
import lombok.Data;
import cn.vr168.interfacetest.parameter.people.Teacher;
import cn.vr168.interfacetest.parameter.people.User;
import cn.vr168.interfacetest.kit.util.SampleAssert;

public class Classroom {
    private JSONObject data;
    private String token;

    public Classroom(String token, JSONObject object) {
        this.data = object;
        this.token = token;
    }

    /**
     * 获取 课时ID
     *
     * @return
     */
    public String getClassroomId() {
        return data.getStr("classroomId");
    }

    /**
     * 获取 classroomOrdery
     *
     * @return
     */
    public Integer getClassroomOrdery() {
        return data.getInt("classroomOrdery");
    }

    /**
     * 获取课时名称
     *
     * @return
     */
    public String getClassroomName() {
        return data.getStr("classroomName");
    }

    /**
     * 修改课时
     *
     * @return
     */
    public ClassroomBuilder edit() {
        return new ClassroomBuilder(token, data);
    }

    /**
     * 设置主讲教师
     *
     * @param userId
     */
    public void setTeacher(String userId) {
        Classroom classroom = edit().teacherId(userId).build();
        this.data = classroom.data;
    }

    /**
     * 设置助教
     *
     * @param user 用户对象
     */
    public void setSupportTeacher(User user) {
        setSupportTeacher(user.getUserId());
    }

    /**
     * 设置助教
     *
     * @param userId 用户id
     */
    public void setSupportTeacher(String userId) {
        JSONObject jsonObject = SetTeacher.of().setTeacher(token, userId, getClassroomId());
        SampleAssert.assertResult0(jsonObject);
    }

    @Data
    public static class ClassroomBuilder {
        private String lessonId;
        private String classroomId;
        private String classroomName;
        private String interaction = "4";
        private String startTime;
        private String coursewareId = "";
        private String timeLong = "0";
        private String classroomOrdery = "0";
        private String classroomRemark = "";
        private String teacherId;
        private String token;

        public ClassroomBuilder(String token, JSONObject object) {
            this.token = token;
            this.classroomId = object.getStr("classroomId");
            this.lessonId = object.getStr("lessonId");
            this.classroomName = object.getStr("classroomName");
            this.startTime = object.getStr("startTime");
            this.teacherId = object.getStr("teacherId");
        }

        public ClassroomBuilder teacherId(String teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public ClassroomBuilder startTime(DateTime dateTime) {
            this.startTime = dateTime.toString();
            return this;
        }

        public ClassroomBuilder startTime(String dateTime) {
            this.startTime = dateTime;
            return this;
        }

        public Classroom build() {
            JSONObject object = Edit.of().edit(Edit.Bean.builder()
                    .classroomId(classroomId)
                    .classroomName(classroomName)
                    .classroomOrdery(classroomOrdery)
                    .classroomRemark(classroomRemark)
                    .coursewareId(coursewareId)
                    .interaction(interaction)
                    .lessonId(lessonId)
                    .startTime(startTime)
                    .teacherId(teacherId)
                    .timeLong(timeLong)
                    .token(token)
                    .build());
            SampleAssert.assertCode200(object);

            JSONObject lessonInfo = GetLessonInfoById.of().getLessonInfoById(token, lessonId);
            SampleAssert.assertCode200(lessonInfo);
            for (JSONObject o : lessonInfo.getJSONObject("data").getJSONArray("classroomList").jsonIter()) {
                if (o.getStr("classroomId").equals(classroomId)) {
                    return new Classroom(token, o);
                }
            }
            throw new RuntimeException("");
        }
    }


    public static void main(String[] args) {
        LessonFactory.creat().getClassRoom(0).setTeacher(Teacher.getInstance().getUserId());

    }

}
