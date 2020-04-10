package parameter.people;

import cn.hutool.json.JSONObject;
import config.Environment;
import inter.mizhu.web.usr.Login;
import inter.mizhu.web.usr.UsrOrgInfo;
import util.SampleAssert;

public class Jigou extends User {
    private static Jigou single = null;

    public Jigou(String account, String password) {
        super(account, password);
    }

    @Override
    protected String login(String account, String password) {
        JSONObject jsonObject = Login.getInstance().login(account, password);
        return jsonObject.getStr("token");
    }

    @Override
    protected JSONObject getUserInfo() {
        JSONObject object = UsrOrgInfo.of().usrOrgInfo(token);
        SampleAssert.assertCode200(object);
        return object.getJSONObject("data").getJSONObject("usrInfo");
    }

    public static Jigou getInstance() {
        if (single == null) {
            single = new Jigou(Environment.getValue("jigou.account"),
                    Environment.getValue("jigou.password"));
        }
        return single;
    }
}
