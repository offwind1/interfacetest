package cn.vr168.interfacetest.inter.mizhu.api.classInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.people.Jigou;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class RtcAppList extends BasicsInterface {

    @Step
    public JSONObject rtcAppList(String token) {
        return post(Body.create()
                .add("token", token)
                .build());
    }

    @Test
    public void test() {
        JSONObject object = rtcAppList(Jigou.getInstance().getToken());
        SampleAssert.assertResult0(object);
        assert object.getJSONArray("data").size() > 4 : "rtcAppList 接口获取的appId数量小于4";
    }

    @Override
    public String route() {
        return "mizhu/api/classInfo/rtcAppList";
    }
}
