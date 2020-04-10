package inter.mizhu.api.mobile;

import cn.hutool.json.JSONObject;
import config.Environment;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import util.SampleAssert;

import java.security.SecureRandom;

@RequiredArgsConstructor(staticName = "of")
public class Login extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private String account;
        private String password;
        private String longitude;
        private String latitude;
        private String phone;
        private String verifycode;
        private String machine;
    }

    //    @Step
    public JSONObject login(Bean bean) {
        return post(beanToMap(bean));
    }

    public JSONObject login(String account, String password) {
        return login(Bean.builder()
                .account(account)
                .password(password)
                .build());
    }

    @Test(description = "账号正常登入")
    public void test() {
        String account = String.format(Environment.getValue("student.account.format"), 100);
        String password = Environment.getValue("student.password");

        JSONObject jsonObject = login(Bean.builder()
                .account(account)
                .password(password)
                .build());
        SampleAssert.assertResult0(jsonObject);
    }

    @Test(description = "手机正常登入")
    public void test1() {
        String phone = Environment.getValue("student.phone");
        String password = Environment.getValue("student.password");
        JSONObject jsonObject = login(Bean.builder()
                .account(phone)
                .password(password)
                .build());
        SampleAssert.assertResult0(jsonObject);
    }


    @Override
    public String route() {
        return "/mizhu/api/mobile/login";
    }
}
