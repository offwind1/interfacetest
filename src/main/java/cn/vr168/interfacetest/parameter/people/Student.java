package cn.vr168.interfacetest.parameter.people;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.mizhu.api.mobile.Login;
import cn.vr168.interfacetest.inter.mizhu.api.mobile.UserInfo;

import java.util.Random;

public class Student extends User implements HasToken {
    private static Student single = null;

    public Student(String account, String password) {
        super(account, password);
    }

    @Override
    protected String login(String account, String password) {
        JSONObject jsonObject = Login.of().login(Login.Bean.builder()
                .account(account)
                .password(password)
                .build());
        return jsonObject.getJSONObject("data").getStr("token");
    }

    public static Student getInstance() {
        if (single == null) {
            single = new Student(
                    String.format(
                            Environment.getValue("student.account.format"),
                            new Random().nextInt(200) + 1),
                    Environment.getValue("student.password"));
        }
        return single;
    }

    @Override
    protected JSONObject getUserInfo() {
        return UserInfo.of().userInfo(this).getJSONObject("data");
    }
}
