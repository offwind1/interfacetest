package cn.vr168.interfacetest.inter.mizhumanage.web.qiniu;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.Body;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class GetHistoryRecords extends BasicsInterface {

    @Step
    public JSONObject getHistoryRecords(String token, String classroomId) {
        return post(Body.create()
                .add("token", token)
                .add("classroomId", classroomId)
                .build());
    }


    @Override
    public String route() {
        return "mizhumanage/web/qiniu/getHistoryRecords";
    }
}
