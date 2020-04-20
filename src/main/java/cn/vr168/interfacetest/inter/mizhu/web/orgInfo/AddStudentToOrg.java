package cn.vr168.interfacetest.inter.mizhu.web.orgInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.parameter.people.User;
import cn.vr168.interfacetest.util.Body;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class AddStudentToOrg extends BasicsInterface {

    @Step
    public JSONObject addStudentToOrg(User user, String account) {
        return post(Body.create()
                .add("token", user.getToken())
                .add("orgId", user.getOrgId())
                .add("account", account)
                .build());
    }

    @Override
    public String route() {
        return "mizhu/web/orgInfo/addStudentToOrg";
    }
}
