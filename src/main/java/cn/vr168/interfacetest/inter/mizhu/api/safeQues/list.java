package cn.vr168.interfacetest.inter.mizhu.api.safeQues;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class list extends BasicsInterface {

    @Step
    public JSONObject list() {
        return getJson(HttpRequest.get(getUrl()));
    }


    @Test
    public void test() {
        JSONObject object = list();
        SampleAssert.assertResult0(object);
    }


    @Override
    public String route() {
        return "mizhu/api/safeQues/list";
    }
}
