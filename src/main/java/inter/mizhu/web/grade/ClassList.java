package inter.mizhu.web.grade;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import parameter.people.User;
import util.Body;
import util.SampleAssert;

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
