package cn.vr168.interfacetest.inter.mizhu.web.item;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import cn.vr168.interfacetest.kit.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class GetKachaListByUser extends BasicsInterface {

    @Step
    public JSONObject getKachaListByUser(String token) {
        return post(Body.create()
                .add("token", token)
                .build());
    }


    @Override
    public String route() {
        return "mizhu/web/item/getKachaListByUser";
    }
}
