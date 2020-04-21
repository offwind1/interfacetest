package cn.vr168.interfacetest.inter.mizhumanage.web.usr;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.inter.mizhu.web.usr.Login;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Teacher;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class ChangePasswords extends BasicsInterface {

    @Step
    public JSONObject changePasswords(String token, String accounts, String password) {
        return post(Body.create()
                .add("token", token)
                .add("accounts", accounts)
                .add("password", password)
                .build());
    }

    public JSONObject changePasswords(String token, String accounts) {
        return changePasswords(token, accounts, "111111");
    }

    @Test(description = "正常调用")
    public void test() {
        String password = RandomUtil.randomString(6);
        String phone = Environment.getValue("lisi.phone");
        JSONObject jsonObject = changePasswords(Teacher.getInstance().getToken(),
                phone,
                password
        );
        SampleAssert.assertCode200(jsonObject);

        JSONObject phoneLogin = Login.of().login(phone, password);
        SampleAssert.assertCode200(phoneLogin);
    }

//    @Test(description = "账号为空")
    public void test1() {
        JSONObject jsonObject = changePasswords(Teacher.getInstance().getToken(),
                "");
        SampleAssert.assertCode(jsonObject, "300");
        SampleAssert.assertMsg(jsonObject, "参数有问题");
    }

//    @Test(description = "密码为空")
    public void test2() {
        JSONObject jsonObject = changePasswords(Teacher.getInstance().getToken(),
                "random001", "");
        SampleAssert.assertCode(jsonObject, "300");
        SampleAssert.assertMsg(jsonObject, "密码不能为空");
    }

//    @Test(description = "密码过短")
    public void test3() {
        JSONObject jsonObject = changePasswords(Teacher.getInstance().getToken(),
                "random001", "1");
        SampleAssert.assertCode(jsonObject, "300");
        SampleAssert.assertMsg(jsonObject, "密码过短");
    }

//    @Test(description = "密码过长")
    public void test4() {
        JSONObject jsonObject = changePasswords(Teacher.getInstance().getToken(),
                "random001", "1111111111111111111111111111111111111");
        SampleAssert.assertCode(jsonObject, "300");
        SampleAssert.assertMsg(jsonObject, "密码过长");
    }


    @Override
    public String route() {
        return "mizhumanage/web/usr/changePasswords";
    }
}
