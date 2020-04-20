package cn.vr168.interfacetest.parameter.people;

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
