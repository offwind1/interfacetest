package cn.vr168.interfacetest.inter.mizhu.api.classInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Teacher;
import cn.vr168.interfacetest.util.Body;
import cn.vr168.interfacetest.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class RoomToday extends BasicsInterface {

    @Step
    public JSONObject roomToday(String token) {
        return post(Body.create()
                .add("token", token)
                .build());
    }

    @Test
    public void test() {
        JSONObject jsonObject = roomToday(Teacher.getInstance().getToken());
        SampleAssert.assertResult0(jsonObject);
    }

    @Override
    public String route() {
        return "mizhu/api/classInfo/roomToday";
    }
}
