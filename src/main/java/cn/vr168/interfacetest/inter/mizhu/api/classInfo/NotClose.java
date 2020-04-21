package cn.vr168.interfacetest.inter.mizhu.api.classInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class NotClose extends BasicsInterface {

    @Step
    public JSONObject notClose(String token) {
        return post(Body.create()
                .add("token", token)
                .build());
    }

    @Test
    public void test() {
        JSONObject object = notClose(Jigou.getInstance().getToken());
        SampleAssert.assertResult0(object);
    }


    @Override
    public String route() {
        return "mizhu/api/classInfo/notClose";
    }
}
