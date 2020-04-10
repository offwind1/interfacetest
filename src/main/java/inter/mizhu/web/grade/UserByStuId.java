package inter.mizhu.web.grade;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class UserByStuId extends BasicsInterface {


    public JSONObject userByStuId(String token, String stuId) {
        return userByStuId(token, stuId, 100);
    }

    @Step
    public JSONObject userByStuId(String token, String stuId, int pageSize) {
        return post(Body.create()
                .add("token", token)
                .add("stuId", stuId)
                .add("currentPage", "1")
                .add("pageSize", String.valueOf(pageSize))
                .build());
    }


    @Test
    public void test() {
        JSONObject object = ClassList.of().classList(Jigou.getInstance().getToken(), Jigou.getInstance().getOrgName());
        String stuId = object.getJSONObject("data").getJSONArray("list").getJSONObject(0).getStr("stuId");
        JSONObject jsonObject = userByStuId(Jigou.getInstance().getToken(),
                stuId);
        SampleAssert.assertCode200(jsonObject);
    }

    @Override
    public String route() {
        return "mizhu/web/grade/userByStuId";
    }
}
