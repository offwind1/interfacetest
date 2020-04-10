package inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class UserListByAccount extends BasicsInterface {

    @Step
    public JSONObject userListByAccount(String token, String accountStr) {
        return post(Body.create()
                .add("token", token)
                .add("accountStr", accountStr)
                .build());
    }

    @Test
    public void test() {
        JSONObject jsonObject = userListByAccount(Jigou.getInstance().getToken(),
                "robot0001,robot0020");
        SampleAssert.assertCode200(jsonObject);
    }
    
    @Override
    public String route() {
        return "mizhu/web/lesson/userListByAccount";
    }
}
