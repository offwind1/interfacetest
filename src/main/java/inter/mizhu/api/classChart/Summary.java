package inter.mizhu.api.classChart;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class Summary extends BasicsInterface {

    @Step
    public JSONObject summary(String token, String classroomId) {
        return post(Body.create()
                .add("token", token)
                .add("classroomId", classroomId)
                .build());
    }

    @Test
    public void test() {
        JSONObject object = summary(Jigou.getInstance().getToken(), "10564c2f45cf452f933efaa17ad46350");
        SampleAssert.assertResult0(object);
    }

    @Override
    public String route() {
        return "mizhu/web/api/classChart/summary";
    }
}
