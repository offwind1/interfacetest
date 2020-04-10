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
public class JoinClassByFile extends BasicsInterface {


    @Step
    public JSONObject joinClassByFile(String token, String fileName, String stuId) {
        return post(Body.create()
                .add("fileName", fileName)
                .add("stuId", stuId)
                .add("token", token)
                .build());
    }

    @Test
    public void test() {
        JSONObject object = joinClassByFile(Jigou.getInstance().getToken(), "ce1190f90a0b4372999bc34427b8ab3f", "1874");
        SampleAssert.assertCode200(object);
    }

    @Override
    public String route() {
        return "mizhu/web/lesson/joinClassByFile";
    }
}
