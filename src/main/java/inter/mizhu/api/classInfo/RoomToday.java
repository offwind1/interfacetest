package inter.mizhu.api.classInfo;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Teacher;
import util.Body;
import util.SampleAssert;

import java.util.jar.JarInputStream;

@RequiredArgsConstructor(staticName = "of")
public class RoomToday extends BasicsInterface {

    @Step
    public JSONObject roomToday(String token) {
        return post(Body.create()
                .add("token", token)
                .build());
    }

    @Test
    public void test() {
        JSONObject jsonObject = roomToday(Teacher.getInstance().getToken());
        SampleAssert.assertResult0(jsonObject);
    }

    @Override
    public String route() {
        return "mizhu/api/classInfo/roomToday";
    }
}
