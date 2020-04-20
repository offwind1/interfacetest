package cn.vr168.interfacetest.inter.flow;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.RoomToday;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.WeekRoom;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.LessonStore;
import cn.vr168.interfacetest.parameter.people.Teacher;

public class zhujiaoTestFlow {


    /**
     * 后台需处理（涉及前端）
     * 1.主讲和助教都可以开课（如果调用开课接口课已开，直接返回课堂信息？）
     * 2.课堂在每个老师的系列课程中都能打开
     * 3.自动打开的课堂可以在直播课管理中查看、关闭，关闭后，只能再由主讲老师或者助教老师打开该课堂
     */

    /**
     * 加入到助教的每个老师，都能在系列课程中开课
     */
    @Test(description = "课堂在每个老师的系列课程中都能打开")
    public void test() {
        Lesson lesson = LessonStore.takeOut();
        JSONObject roomToday = RoomToday.of().roomToday(Teacher.getInstance().getToken());
        JSONObject weekRoom = WeekRoom.of().weekRoom(Teacher.getInstance().getToken(), "2", "0");
    }


}
