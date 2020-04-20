package cn.vr168.interfacetest.inter.mizhu.web.qiniu;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.util.Body;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class GetUptoken extends BasicsInterface {

    @Step
    public JSONObject getUptoken(String token, String fileName, String fileType) {
        return post(Body.create()
                .add("token", token)
                .add("fileName", fileName)
                .add("fileType", fileType)
                .build());
    }


    @Override
    public String route() {
        return "mizhu/web/qiniu/getUptoken";
    }
}
