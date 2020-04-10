package inter.mizhu.api.classInfo;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

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
