package cn.vr168.interfacetest.inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.util.Body;
import cn.vr168.interfacetest.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class OrgTeacherInfo extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private String token;
        private String phone;
        private String name;
        private String currentPage;
        private String pageSize;
    }

    @Step
    public JSONObject orgTeacherInfo(Bean bean) {
        return post(beanToMap(bean));
    }

    @Test(description = "正常调用")
    public void test() {
        JSONObject jsonObject = orgTeacherInfo(Bean.builder()
                .token(Jigou.getInstance().getToken())
                .currentPage("1")
                .pageSize("10")
                .build());
        SampleAssert.assertStr(jsonObject, "code", "200");
    }


    @Override
    public String route() {
        return "mizhu/web/usr/orgTeacherInfo";
    }
}
