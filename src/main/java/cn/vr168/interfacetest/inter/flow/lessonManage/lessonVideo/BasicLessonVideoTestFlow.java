package cn.vr168.interfacetest.inter.flow.lessonManage.lessonVideo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.OptionList;
import cn.vr168.interfacetest.inter.mizhu.web.classroom.AddClassVideo;
import cn.vr168.interfacetest.inter.mizhu.web.classroom.DeleteClassVideo;
import cn.vr168.interfacetest.inter.mizhu.web.classroom.EditClassVideo;
import cn.vr168.interfacetest.inter.mizhu.web.course.MyList;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.GetVideo;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.Classroom;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicLessonVideoTestFlow {

    private Lesson lesson = LessonFactory.creat();
    private Classroom classroom = lesson.getClassRoom(0);

    private Boolean check() {
        JSONObject video = GetVideo.of().getVideo(Jigou.getInstance().getToken(), lesson.getLessonId());
        SampleAssert.assertCode200(video);
        Set<String> set = video.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("classroomVideoId");
        }).collect(Collectors.toSet());

        JSONObject optionList = OptionList.of().optionList(Jigou.getInstance().getToken(), classroom.getClassroomId());
        SampleAssert.assertResult0(optionList);
        Set<String> op_set = optionList.getJSONObject("data").getJSONArray("videoList").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("classroomVideoId");
        }).collect(Collectors.toSet());

        return set.equals(op_set);
    }

    @Test(description = "增")
    public void test() {
        JSONObject classVideo = AddClassVideo.of().addClassVideo(Jigou.getInstance().getToken(), classroom.getClassroomId());
        SampleAssert.assertCode200(classVideo);
        assert check() : "课堂详情中的视频和视频管理中的不正确";
    }

    @Test(description = "改", dependsOnMethods = {"test"})
    public void test1() {
        JSONObject video = GetVideo.of().getVideo(Jigou.getInstance().getToken(), lesson.getLessonId());
        SampleAssert.assertCode200(video);
        String classroomVideoId = video.getJSONObject("data").getJSONArray("list").getJSONObject(0).getStr("classroomVideoId");

        JSONObject object = MyList.of().myList(MyList.Bean.builder()
                .coursewareType("3").reply("0")
                .token(Jigou.getInstance().getToken())
                .build());
        List<String> list = object.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("coursewareContext");
        }).collect(Collectors.toList());

        if (list.size() < 1) {
            return;
        }

        JSONObject classVideo = EditClassVideo.of().editClassVideo(EditClassVideo.Bean.builder()
                .classroomId(classroom.getClassroomId())
                .token(Jigou.getInstance().getToken())
                .videoId(classroomVideoId)
                .videoPath(list.get(0))
                .build());
        SampleAssert.assertCode200(classVideo);
        assert check() : "课堂详情中的视频和视频管理中的不正确";
    }


    @Test(description = "删", dependsOnMethods = {"test", "test1"})
    public void test2() {
        JSONObject video = GetVideo.of().getVideo(Jigou.getInstance().getToken(), lesson.getLessonId());
        SampleAssert.assertCode200(video);
        String classroomVideoId = video.getJSONObject("data").getJSONArray("list").getJSONObject(0).getStr("classroomVideoId");

        JSONObject classVideo = DeleteClassVideo.of().deleteClassVideo(Jigou.getInstance().getToken(), classroomVideoId);
        SampleAssert.assertCode200(classVideo);
        assert check() : "删了的的视频依旧存在";
    }

}
