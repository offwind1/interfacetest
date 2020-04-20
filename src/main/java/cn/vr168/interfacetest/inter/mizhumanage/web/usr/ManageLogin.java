package cn.vr168.interfacetest.inter.mizhumanage.web.usr;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import lombok.RequiredArgsConstructor;
import cn.vr168.interfacetest.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class ManageLogin extends BasicsInterface {

    public static ManageLogin getInstance() {
        return new ManageLogin();
    }

    public JSONObject manageLogin(String account, String password) {
        return post(Body.create()
                .add("userName", account)
                .add("password", password)
                .build());
    }

    @Override
    public String route() {
        return "/mizhumanage/web/usr/manageLogin";
    }
}
