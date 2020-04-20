package cn.vr168.interfacetest.parameter.people;

import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.mizhumanage.web.usr.ManageLogin;

public class Admin extends Account {
    private static Admin single = null;

    public Admin(String account, String password) {
        super(account, password);
    }

    @Override
    protected String login(String account, String password) {
        return ManageLogin.of().manageLogin(account, password).getStr("token");
    }

    public static Admin getInstance() {
        if (single == null) {
            single = new Admin(Environment.getValue("admin.account"),
                    Environment.getValue("admin.password"));
        }
        return single;
    }
}
