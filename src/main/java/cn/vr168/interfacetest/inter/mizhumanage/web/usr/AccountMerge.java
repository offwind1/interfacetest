package cn.vr168.interfacetest.inter.mizhumanage.web.usr;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class AccountMerge extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private String nickname;
        private String account;
        private String userPhone;
        private String mizhuCoin;
        private String mizhuTime;
        private String integ;
        private String oldUserId;
        private String newUserId;
        private String token;
    }

    public JSONObject accountMerge(Bean bean) {
        return post(beanToMap(bean));
    }


    @Override
    public String route() {
        return "mizhu/web/usr/accountMerge";
    }
}
