package inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class UsrOrgInfo extends BasicsInterface {

    @Step
    public JSONObject usrOrgInfo(String token) {
        return post(Body.create().add("token", token).build());
    }

    @Test
    public void test() {
        JSONObject jsonObject = usrOrgInfo(Jigou.getInstance().getToken());
        SampleAssert.assertCode200(jsonObject);
    }

    @Override
    public String route() {
        return "mizhu/web/usr/usrOrgInfo";
    }
}
