package cn.vr168.interfacetest.parameter.people;

import cn.hutool.json.JSONObject;

public abstract class User extends Account {
    protected JSONObject userInfo;

    public User(String account, String password) {
        super(account, password);
        userInfo = getUserInfo();
    }

    protected abstract JSONObject getUserInfo();

    public String getUserId() {
        return userInfo.getStr("userId");
    }

    public String getName() {
        return userInfo.getStr("name");
    }

    public String getOrgId() {
        return userInfo.getStr("orgId");
    }

    public String getAccount() {
        return userInfo.getStr("account");
    }

    public String getOrgName() {
        return userInfo.getStr("orgName");
    }
}
