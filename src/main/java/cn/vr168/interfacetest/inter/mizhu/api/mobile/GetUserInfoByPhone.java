package cn.vr168.interfacetest.inter.mizhu.api.mobile;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.util.Body;
import cn.vr168.interfacetest.util.SampleAssert;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class GetUserInfoByPhone extends BasicsInterface {

    @Step
    public JSONObject getUserInfoByPhone(String phone) {
        return post(Body.create()
                .add("phone", phone)
                .build());
    }

    @Test
    public void test() {
        JSONObject object = getUserInfoByPhone("18767126032");
        SampleAssert.assertResult0(object);
    }

    @Override
    public String route() {
        return "mizhu/api/mobile/getUserInfoByPhone";
    }
}
