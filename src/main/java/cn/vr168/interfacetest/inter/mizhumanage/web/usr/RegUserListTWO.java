package cn.vr168.interfacetest.inter.mizhumanage.web.usr;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.inter.mizhu.api.mobile.Login;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Admin;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.DBUtil;
import cn.vr168.interfacetest.kit.util.SampleAssert;

import java.sql.SQLException;

@RequiredArgsConstructor(staticName = "of")
public class RegUserListTWO extends BasicsInterface {


    @Step
    public JSONObject regUserListTwo(String token, String accountStrJson, String authen) {
        return post(Body.create()
                .add("token", token)
                .add("accountStrJson", accountStrJson)
                .add("authen", authen)
                .build());
    }

    private static final String account = "xiaowang";
    private static final String account_nickname = "小王";
    private static final String phone = "16632132132";
    private static final String phone_nickname = "166小子";

    private JSONArray getArray() {
        JSONArray array = new JSONArray();

        JSONObject acc_obj = new JSONObject();
        acc_obj.put("account", account);
        acc_obj.put("nickname", account_nickname);
        array.add(acc_obj);

        JSONObject pho_obj = new JSONObject();
        pho_obj.put("userPhone", phone);
        pho_obj.put("nickname", phone_nickname);
        array.add(pho_obj);
        return array;
    }

    @Test(description = "正常创建学生", groups = {"database"})
    public void test0() throws SQLException {
        try {
            //创建学生
            JSONObject jsonObject = regUserListTwo(Admin.getInstance().getToken(),
                    getArray().toString(), "0"
            );
            SampleAssert.assertCode200(jsonObject);

            //学生 账号登录
            JSONObject acc_login = Login.of().login(Login.Bean.builder()
                    .account(account)
                    .password("8cheng")
                    .build());
            SampleAssert.assertResult0(acc_login);
            SampleAssert.assertStr(acc_login.getJSONObject("data"), "account", account);
            SampleAssert.assertStr(acc_login.getJSONObject("data"), "nickname", account_nickname);
            SampleAssert.assertStr(acc_login.getJSONObject("data"), "authen", "0");


            //学生 手机登录
            JSONObject pho_login = Login.of().login(Login.Bean.builder()
                    .account(phone)
                    .password("8cheng")
                    .build());
            SampleAssert.assertResult0(pho_login);
            SampleAssert.assertStr(pho_login.getJSONObject("data"), "userPhone", phone);
            SampleAssert.assertStr(pho_login.getJSONObject("data"), "nickname", phone_nickname);
            SampleAssert.assertStr(pho_login.getJSONObject("data"), "authen", "0");

        } catch (AssertionError error) {
            throw error;
        } finally {
            DBUtil.delUserWithPhone(phone);
            DBUtil.delUserWithAccount(account);
        }
    }

    @Test(description = "正常创建教师", groups = {"database"})
    public void test00() throws SQLException {
        try {
            //创建学生
            JSONObject jsonObject = regUserListTwo(Admin.getInstance().getToken(),
                    getArray().toString(), "2"
            );
            SampleAssert.assertCode200(jsonObject);

            //学生 账号登录
            JSONObject acc_login = Login.of().login(Login.Bean.builder()
                    .account(account)
                    .password("8cheng")
                    .build());
            SampleAssert.assertResult0(acc_login);
            SampleAssert.assertStr(acc_login.getJSONObject("data"), "account", account);
            SampleAssert.assertStr(acc_login.getJSONObject("data"), "nickname", account_nickname);
            SampleAssert.assertStr(acc_login.getJSONObject("data"), "authen", "2");


            //学生 手机登录
            JSONObject pho_login = Login.of().login(Login.Bean.builder()
                    .account(phone)
                    .password("8cheng")
                    .build());
            SampleAssert.assertResult0(pho_login);
            SampleAssert.assertStr(pho_login.getJSONObject("data"), "userPhone", phone);
            SampleAssert.assertStr(pho_login.getJSONObject("data"), "nickname", phone_nickname);
            SampleAssert.assertStr(pho_login.getJSONObject("data"), "authen", "2");

        } catch (AssertionError error) {
            throw error;
        } finally {
            DBUtil.delUserWithPhone(phone);
            DBUtil.delUserWithAccount(account);
        }
    }

    @Test(description = "用账号创建_重复", groups = {"database"})
    public void test() {
        JSONObject jsonObject = regUserListTwo(Admin.getInstance().getToken(),
                accountJson("random001", "随机001").toString(),
                "0");
        SampleAssert.assertCode200(jsonObject);
        SampleAssert.assertStr(jsonObject, "msg", "random001，重复；");
    }

//    @Test(description = "用手机号创建_重复")
    public void test1() {
        JSONObject jsonObject = regUserListTwo(Admin.getInstance().getToken(),
                userPhoneJson("16900000001", "169").toString(),
                "0");
        SampleAssert.assertCode200(jsonObject);
    }

//    @Test(description = "用手机号创建 填入非手机号")
    public void test2() throws SQLException {
        try {
            JSONObject jsonObject = regUserListTwo(Admin.getInstance().getToken(),
                    userPhoneJson("random001", "随机001").toString(),
                    "0");

            assert !jsonObject.getStr("code").equals("200") :
                    "手机号填入非正常数据，也能创建成功";

        } catch (AssertionError error) {
            throw error;
        } finally {
            DBUtil.delUserWithPhone("random001");
        }
    }

    @Test(description = "账号昵称为空")
    public void test3() {
        JSONObject jsonObject = regUserListTwo(Admin.getInstance().getToken(),
                accountJson("", "123123").toString(),
                "0");
        assert jsonObject.getStr("code").equals("300");
    }

    private JSONArray accountJson(String account, String nickname) {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("account", account);
        object.put("nickname", nickname);
        array.add(object);
        return array;
    }

    private JSONArray userPhoneJson(String userPhone, String nickname) {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("userPhone", userPhone);
        object.put("nickname", nickname);
        array.add(object);
        return array;
    }


    @Override
    public String route() {
        return "mizhumanage/web/usr/regUserListTWO";
    }
}
