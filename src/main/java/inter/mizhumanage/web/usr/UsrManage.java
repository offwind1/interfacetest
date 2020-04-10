package inter.mizhumanage.web.usr;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Admin;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class UsrManage extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private String token;
        private String currentPage;
        private String pageSize;
        private String phone;
        private String account;
        private String authenResult;
        private String queryType;
    }

    @Step
    public JSONObject userManage(Bean bean) {
        return post(beanToMap(bean));
    }

    @Test
    public void test() {
        JSONObject jsonObject = userManage(Bean.builder()
                .token(Admin.getInstance().getToken())
                .currentPage("1")
                .pageSize("10")
                .queryType("1")
                .build());

        SampleAssert.assertCode200(jsonObject);
    }


    @Override
    public String route() {
        return "mizhumanage/web/usr/usrManage";
    }
}
