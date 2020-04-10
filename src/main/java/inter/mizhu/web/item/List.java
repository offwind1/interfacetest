package inter.mizhu.web.item;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

/**
 * 查询收费列表
 */
@RequiredArgsConstructor(staticName = "of")
public class List extends BasicsInterface {

    @Step
    public JSONObject list(String token, String page, String pageSize) {
        return post(Body.create()
                .add("token", token)
                .add("page", page)
                .add("pageSize", pageSize)
                .build());
    }

    @Test
    public void test() {
        JSONObject object = list(Jigou.getInstance().getToken(), "1", "10");
        SampleAssert.assertResult0(object);
    }


    @Override
    public String route() {
        return "mizhu/web/item/list";
    }
}
