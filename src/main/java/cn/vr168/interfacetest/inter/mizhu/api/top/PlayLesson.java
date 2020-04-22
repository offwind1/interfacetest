package cn.vr168.interfacetest.inter.mizhu.api.top;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.people.Jigou;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class PlayLesson extends BasicsInterface {

    @Step
    public JSONObject playLesson(String token) {
        return post(Body.create()
                .add("token", token)
                .add("page", "1")
                .add("orgId", "0")
                .build());
    }

    @Test
    public void test() {
        JSONObject object = playLesson(Jigou.getInstance().getToken());
        SampleAssert.assertResult0(object);
    }

    @Override
    public String route() {
        return "mizhu/api/top/playLesson";
    }
}
