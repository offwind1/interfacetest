package parameter.people;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import config.Environment;
import inter.mizhu.api.mobile.UserInfo;
import inter.mizhu.web.usr.Login;

public class Teacher extends User implements HasToken {

    private static Teacher single = null;

    public Teacher(String account, String password) {
        super(account, password);
    }

    @Override
    protected String login(String account, String password) {
        JSONObject jsonObject = Login.getInstance().login(account, password);
        return jsonObject.getStr("token");
    }

    @Override
    protected JSONObject getUserInfo() {
        return UserInfo.of().userInfo(this).getJSONObject("data");
    }

    public static Teacher getInstance() {
        if (single == null) {
            single = new Teacher(Environment.getValue("teacher.account"),
                    Environment.getValue("teacher.password"));
        }
        return single;
    }

    public String getUserPhone() {
        return userInfo.getStr("userPhone");
    }

    public String getOrgRel() {
        JSONArray array = userInfo.getJSONArray("orgRelList");
        if (array != null && array.size() > 0) {
            return array.getJSONObject(0).getStr("orgId");
        }
        return getOrgId();
    }



}
