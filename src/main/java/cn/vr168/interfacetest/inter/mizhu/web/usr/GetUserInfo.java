package cn.vr168.interfacetest.inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class GetUserInfo extends BasicsInterface {

    public JSONObject getUserInfo(){
        return null;
    }



    @Override
    public String route() {
        return "mizhu/web/usr/getUserInfo";
    }
}
