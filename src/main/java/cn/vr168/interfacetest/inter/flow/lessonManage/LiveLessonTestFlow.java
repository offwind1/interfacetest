package cn.vr168.interfacetest.inter.flow.lessonManage;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.RoomToday;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.WeekRoom;
import cn.vr168.interfacetest.parameter.Classroom;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.stream.Collectors;

public class LiveLessonTestFlow {

    private Integer count = 4;
    private String token = Jigou.getInstance().getToken();
    private Lesson lesson = Lesson.builder()
            .classroomCount(count)
            .build();

    {
        lesson.applied();
    }

    /**
     *
     */
    @Test(description = "weekRoom 获取两周内待上的课程")
    public void test() {
        String lessonId = lesson.getLessonId();

        JSONObject weekRoom = WeekRoom.of().weekRoom(token, "2");
        Set<String> classes = lesson.getClassrooms().stream()
                .map(Classroom::getClassroomId).collect(Collectors.toSet());

        Set<String> set = mapClassroom(weekRoom, lessonId);
        assert classes.equals(set) : "课程没有获取";
    }

    /**
     *
     */
    @Test(description = "roomToday 获取今天待上的课程")
    public void test1() {
        String lessonId = lesson.getLessonId();

        JSONObject roomToday = RoomToday.of().roomToday(token);
        Set<String> set = mapClassroom(roomToday, lessonId);

        assert set.size() == 1 : "今天上课的课程比1多";
        assert set.contains(lesson.getClassRoom(0).getClassroomId());
    }

    private Set<String> mapClassroom(JSONObject object, String lessonId) {
        return object.getJSONArray("data").stream().filter(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("lessonId").equals(lessonId);
        }).map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("classroomId");
        }).collect(Collectors.toSet());
    }


}
