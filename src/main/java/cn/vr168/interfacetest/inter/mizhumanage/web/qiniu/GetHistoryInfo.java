package cn.vr168.interfacetest.inter.mizhumanage.web.qiniu;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Admin;
import cn.vr168.interfacetest.util.Body;
import cn.vr168.interfacetest.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class GetHistoryInfo extends BasicsInterface {

    @Step
    public JSONObject getHistoryInfo(String token, String classroomId) {
        return post(Body.create()
                .add("token", token)
                .add("classroomId", classroomId)
                .build());
    }

    @Test
    public void test() {
        JSONObject object = getHistoryInfo(Admin.getInstance().getToken(), "d2da887f37bd4590bc5405ee1947e3a6");
        SampleAssert.assertCode200(object);
    }

    @Override
    public String route() {
        return "mizhumanage/web/qiniu/getHistoryInfo";
    }
}
