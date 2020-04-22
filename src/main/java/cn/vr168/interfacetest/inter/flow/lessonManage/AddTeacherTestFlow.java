package cn.vr168.interfacetest.inter.flow.lessonManage;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.RoomToday;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.WeekRoom;
import cn.vr168.interfacetest.inter.mizhu.web.classroom.SetTeacher;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.UserListByAccountOrPhone;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.Classroom;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AddTeacherTestFlow {

    private Lesson lesson = Lesson.builder().classroomCount(4).build();
    private Classroom classroom = lesson.getClassRoom(0);
    private String phone = Environment.getValue("phone.registered");

    @Test(description = "未认证账号不能作为教师")
    public void test() {
        JSONObject user = UserListByAccountOrPhone.of().userListByAccountOrPhone(Jigou.getInstance().getToken(), "baby0001");
        String userId = user.getJSONObject("data").getJSONArray("list").getJSONObject(0).getStr("userId");
        JSONObject teacher = SetTeacher.of().setTeacher(Jigou.getInstance().getToken(), userId, classroom.getClassroomId());
        SampleAssert.assertMsg(teacher, "所选教师中存在未认证的用户");
    }


    @Test(description = "助教加入课堂后，助教可以在开课列表中查看到该课程")
    public void test1() {
        JSONObject user = UserListByAccountOrPhone.of().userListByAccountOrPhone(Jigou.getInstance().getToken(), phone);
        String userId = user.getJSONObject("data").getJSONArray("list").getJSONObject(0).getStr("userId");
        JSONObject teacher = SetTeacher.of().setTeacher(Jigou.getInstance().getToken(), userId, classroom.getClassroomId());

        lesson.applied();

        JSONObject weekRoom = WeekRoom.of().weekRoom(Jigou.getInstance().getToken(), "2");
        Set<String> set = weekRoom.getJSONArray("data").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("classroomId");
        }).collect(Collectors.toSet());

        assert set.contains(lesson.getClassRoom(0).getClassroomId()) : "weekRoom 中不存在";

        JSONObject roomToday = RoomToday.of().roomToday(Jigou.getInstance().getToken());
        List<String> list = roomToday.getJSONArray("data").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("classroomId");
        }).collect(Collectors.toList());


        assert list.contains(lesson.getClassRoom(0).getClassroomId()) : "roomToday 中不存在";
    }
}
