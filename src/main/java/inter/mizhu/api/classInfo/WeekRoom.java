package inter.mizhu.api.classInfo;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Teacher;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class WeekRoom extends BasicsInterface {

    @Step
    public JSONObject weekRoom(String token, String studyType, String orgId) {
        return post(Body.create()
                .add("token", token)
                .add("studyType", studyType)
                .add("orgId", orgId)
                .build());
    }

    @Test
    public void test() {
        JSONObject jsonObject = weekRoom(Teacher.getInstance().getToken(), "2", "0");
        SampleAssert.assertResult0(jsonObject);
    }
    
    @Override
    public String route() {
        return "mizhu/api/classInfo/weekRoom";
    }
}
