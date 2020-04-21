package cn.vr168.interfacetest.inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.parameter.people.Teacher;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class GetUserCreditId extends BasicsInterface {

    @Step
    public JSONObject getUserCreditId(String token) {
        return post(Body.create()
                .add("token", token)
                .build());
    }

    @Test
    public void test() {
        JSONObject creditId = getUserCreditId(Teacher.getInstance().getToken());
        SampleAssert.assertStr(creditId.getJSONObject("data"), "authen", "2");
    }


    @Override
    public String route() {
        return "mizhu/web/usr/getUserCreditId";
    }
}
