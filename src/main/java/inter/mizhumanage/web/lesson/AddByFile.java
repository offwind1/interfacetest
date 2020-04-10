package inter.mizhumanage.web.lesson;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import util.Body;

@RequiredArgsConstructor(staticName = "of")
public class AddByFile extends BasicsInterface {

    @Step
    public JSONObject addByFile(String token, String createUserId, String batchNum, String orgId) {
        return post(Body.create()
                .add("token", token)
                .add("createUserId", createUserId)
                .add("batchNum", batchNum)
                .add("orgId", orgId)
                .build());
    }

    @Override
    public String route() {
        return "mizhumanage/web/lesson/addByfile";
    }
}
