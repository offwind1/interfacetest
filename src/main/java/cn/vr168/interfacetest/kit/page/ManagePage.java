package cn.vr168.interfacetest.kit.page;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.mizhu.api.mobile.GetUserInfoByPhone;
import cn.vr168.interfacetest.inter.mizhumanage.web.usr.AccountMerge;
import cn.vr168.interfacetest.inter.mizhumanage.web.usr.AddNewUser;
import cn.vr168.interfacetest.parameter.people.Admin;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import org.testng.annotations.Test;

import java.util.Optional;

public class ManagePage {

    @Test
    public void test() {
        ManagePage.DeleteAccountByMerge("16612312111");
    }

    /**
     * 通过合并，删除用户账号
     */
    public static void DeleteAccountByMerge(String delPhone) {
        String nickname = Environment.getValue("merge.nickname");
        String account = Environment.getValue("merge.account");
        String phone = Environment.getValue("merge.phone");
        String mizhuCoin = Environment.getValue("merge.mizhuCoin");
        String mizhuTime = Environment.getValue("merge.mizhuTime");

        String userId = getUserIdByPhone(phone).orElseGet(() -> {
            newUser(account, phone);
            return getUserIdByPhone(phone).get();
        });

        String oldUserId = getUserIdByPhone(delPhone).orElseThrow(() -> {
            return new RuntimeException("未找到该手机号：" + delPhone);
        });

        JSONObject accountMerge = AccountMerge.of().accountMerge(AccountMerge.Bean.builder()
                .account(account)
                .nickname(nickname)
                .mizhuCoin(mizhuCoin)
                .mizhuTime(mizhuTime)
                .newUserId(userId)
                .oldUserId(oldUserId)
                .userPhone(phone)
                .integ("0")
                .token(Admin.getInstance().getToken())
                .build());
    }

    /**
     * 通过手机号获取用户id
     */
    public static Optional<String> getUserIdByPhone(String phone) {
        JSONObject userInfo = GetUserInfoByPhone.of().getUserInfoByPhone(phone);
        if (userInfo.getStr("msg").equals("没有该用户")) {
            return Optional.empty();
        }
        return Optional.of(userInfo.getJSONObject("data").getStr("userId"));
    }

    /**
     * 新建用户
     */
    public static void newUser(String account, String phone) {
        JSONObject newUser = AddNewUser.of().addNewUser(AddNewUser.Bean.builder()
                .account(account)
                .token(Admin.getInstance().getToken())
                .nickname("新增用户" + RandomUtil.randomString(6))
                .password("111111")
                .sex("M")
                .identity("1")
                .safeQuestionId("1")
                .phone(phone)
                .safeAnswer("大BOSS")
                .tag("")
                .build());
        SampleAssert.assertCode200(newUser);
    }

}
