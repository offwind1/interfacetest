package cn.vr168.interfacetest.inter.mizhu.web.grade;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.parameter.people.User;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class ClassList extends BasicsInterface {

    @Step
    public JSONObject classList(String token, String orgName) {
        return post(Body.create()
                .add("token", token)
                .add("orgName", orgName)
                .build());
    }

    public JSONObject classList(User user) {
        return classList(user.getToken(), user.getOrgName());
    }

    @Test
    public void test() {
        JSONObject jsonObject = classList(Jigou.getInstance().getToken(),
                Jigou.getInstance().getOrgName());
        SampleAssert.assertCode200(jsonObject);
    }

    @Override
    public String route() {
        return "mizhu/web/grade/classList";
    }
}
