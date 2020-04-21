package cn.vr168.interfacetest.inter.mizhu.web.classroom;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.Body;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class DeleteClassVideo extends BasicsInterface {

    @Step
    public JSONObject deleteClassVideo(String token, String videoId) {
        return post(Body.create()
                .add("token", token)
                .add("videoId", videoId)
                .build());
    }


    @Override
    public String route() {
        return "mizhu/web/classroom/deleteClassVideo";
    }
}
