package inter.mizhumanage.web.usr;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class AddNewUser extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String account;
        private String password;
        private String nickname;
        private String sex;
        private String identity;
        private String safeQuestionId;
        private String safeAnswer;
        private String tag;
        private String token;
    }

    @Step
    public JSONObject addNewUser(String token, String account, String nickname) {
        return post(beanToMap(Bean.builder()
                .account(account)
                .token(token)
                .nickname(nickname)
                .password("111111")
                .sex("M")
                .identity("1")
                .safeQuestionId("1")
                .safeAnswer("大BOSS")
                .tag("")
                .build()));
    }

    @Override
    public String route() {
        return "mizhumanage/web/usr/addNewUser";
    }
}
