package inter.mizhu.web.item;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import util.Body;

@RequiredArgsConstructor(staticName = "of")
public class Delete extends BasicsInterface {

    @Step
    public JSONObject delete(String token, String itemId) {
        return post(Body.create()
                .add("token", token)
                .add("itemId", itemId)
                .build());
    }

    @Override
    public String route() {
        return "mizhu/web/item/delete";
    }
}
