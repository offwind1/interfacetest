package cn.vr168.interfacetest.inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class Login extends BasicsInterface {

    public static Login getInstance() {
        return new Login();
    }

    @Step("login")
    public JSONObject login(String account, String password) {
        return post(Body.create()
                .add("userName", account)
                .add("password", password)
                .build()
        );
    }

    /*
     * 用户名，密码
     *
     * 用户名：空,正确的,不正确的
     * 密码：空，正确的，不正确，特殊字符
     */


    @Test(description = "正常账号密码")
    public void test() {
        String account = String.format(Environment.getValue("student.account.format"), 100);
        String password = Environment.getValue("student.password");

        JSONObject object = login(account, password);
        assert object.getStr("code").equals("200");
    }

    @Test(description = "空账号密码")
    public void test1() {
        JSONObject object = login("", "");
        assert object.getStr("code").equals("300");
    }

    @Test(description = "超长账号密码")
    public void test2() {
        JSONObject object = login("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "111111111111111111111111111111111111111111111111111");
        assert object.getStr("code").equals("300");
    }


    @Override
    public String route() {
        return "mizhu/web/usr/login";
    }
}
