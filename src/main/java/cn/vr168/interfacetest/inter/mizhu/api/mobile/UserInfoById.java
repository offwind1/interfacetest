package cn.vr168.interfacetest.inter.mizhu.api.mobile;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.Body;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class UserInfoById extends BasicsInterface {

    @Step
    public JSONObject userInfoById(String userId) {
        return post(Body.create()
                .add("userId", userId)
                .build());
    }


    @Override
    public String route() {
        return "mizhu/api/mobile/userInfoById";
    }
}
