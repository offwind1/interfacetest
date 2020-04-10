package parameter.people;

import cn.hutool.json.JSONObject;

public abstract class Account implements HasToken {
    protected String token;

    public Account(String account, String password) {
        token = login(account, password);
    }

    protected abstract String login(String account, String password);

    public String getToken() {
        return token;
    }


}
