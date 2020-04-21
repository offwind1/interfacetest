package cn.vr168.interfacetest.inter.mizhumanage.web.classroom;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.Body;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class GetOnlineRecord extends BasicsInterface {


    @Step
    public JSONObject getOnlineRecord(String token, String classroomId) {
        return post(Body.create()
                .add("token", token)
                .add("classroomId", classroomId)
                .build());
    }


    @Override
    public String route() {
        return "mizhumanage/web/classroom/getOnlineRecord";
    }
}
