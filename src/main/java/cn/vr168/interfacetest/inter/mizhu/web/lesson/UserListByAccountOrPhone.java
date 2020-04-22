package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.people.Jigou;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class UserListByAccountOrPhone extends BasicsInterface {



    @Step
    public JSONObject userListByAccountOrPhone(String token, String accountStr) {
        return post(Body.create()
                .add("token", token)
                .add("accountStr", accountStr)
                .build());
    }

    @Test
    public void test() {
        JSONObject object = userListByAccountOrPhone(Jigou.getInstance().getToken(), "baby0001");
        SampleAssert.assertCode200(object);

    }

    @Override
    public String route() {
        return "mizhu/web/lesson/userListByAccountOrPhone";
    }
}
