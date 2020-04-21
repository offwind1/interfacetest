package cn.vr168.interfacetest.inter.mizhu.api.lessonInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class LessonInfo extends BasicsInterface {

    @Step
    public JSONObject lessonInfo(String token, String lessonId) {
        return post(Body.create()
                .add("token", token)
                .add("lessonId", lessonId)
                .build());
    }

    @Test
    public void test() {
        SampleAssert.assertResult0(lessonInfo(Jigou.getInstance().getToken(), LessonFactory.takeOut().getLessonId()));
    }


    @Override
    public String route() {
        return "mizhu/api/lessonInfo/lessonInfo";
    }
}
