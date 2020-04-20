package cn.vr168.interfacetest.inter.mizhu.web.orgInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import cn.vr168.interfacetest.util.Body;

//@Log4j
@RequiredArgsConstructor(staticName = "of")
public class AddTeacher extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String token;
        private String phone;
        private String orgId;
        private String userId;
        private String signIn; // 1已注册， 0 未注册
        private String nickname;
    }

    @Step("addTeacher 添加机构到教师")
    public JSONObject addTeacher(Bean bean) {
        return post(Body.create()
                .add("token", bean.token)
                .add("phone", bean.phone)
                .add("orgId", bean.orgId)
                .add("userId", bean.userId)
                .add("signIn", bean.signIn)
                .add("nickname", bean.nickname)
                .build());
    }


    @Override
    public String route() {
        return "mizhu/web/orgInfo/addTeacher";
    }
}
