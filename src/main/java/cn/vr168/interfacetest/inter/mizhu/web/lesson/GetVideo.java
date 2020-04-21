package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.people.Jigou;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class GetVideo extends BasicsInterface {

    @Step
    public JSONObject getVideo(String token, String lessonId) {
        return post(Body.create()
                .add("token", token)
                .add("lessonId", lessonId)
                .build());
    }

    @Test
    public void test() {
        JSONObject object = getVideo(Jigou.getInstance().getToken(), LessonFactory.takeOut().getLessonId());
        SampleAssert.assertCode200(object);
    }
    
    @Override
    public String route() {
        return "mizhu/web/lesson/getVideo";
    }
}
