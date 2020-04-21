package cn.vr168.interfacetest.inter.mizhumanage.web.classroom;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.Body;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class Delete extends BasicsInterface {

    @Step
    public JSONObject delete(String token, String classroomIds) {
        return post(Body.create()
                .add("token", token)
                .add("classroomIds", classroomIds)
                .build());
    }

    @Override
    public String route() {
        return "mizhumanage/web/classroom/delete";
    }
}
