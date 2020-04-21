package cn.vr168.interfacetest.inter.mizhu.web.classroom;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.people.Jigou;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

import java.util.Map;

@RequiredArgsConstructor(staticName = "of")
public class AddClassVideo extends BasicsInterface {


    @Step
    public JSONObject addClassVideo(String token, String classroomId) {
        Map<String, Object> map = Body.create()
                .add("token", token)
                .add("classroomId", classroomId)
                .build();
        map.put("faceImg", "http://images.mizholdings.com/DPMIt6inrH3L~w9g.png");
        map.put("videoPath", "http://images.mizholdings.com/视频.mp4");
        return post(map);
    }

    @Test
    public void test() {
        JSONObject object = addClassVideo(Jigou.getInstance().getToken(),
                LessonFactory.takeOut().getClassRoom(0).getClassroomId());
        SampleAssert.assertCode200(object);
    }

    @Override
    public String route() {
        return "mizhu/web/classroom/addClassVideo";
    }
}
