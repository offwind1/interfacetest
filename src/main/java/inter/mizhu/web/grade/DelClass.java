package inter.mizhu.web.grade;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class DelClass extends BasicsInterface {

    @Step
    public JSONObject delClass(String token, String stuId) {
        return post(Body.create()
                .add("token", token)
                .add("stuId", stuId)
                .build());
    }

    @Override
    public String route() {
        return "mizhu/web/grade/delClass";
    }
}
