package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.util.Body;
import cn.vr168.interfacetest.util.SampleAssert;

import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "of")
public class JoinClassByUserId extends BasicsInterface {

    @Step
    public JSONObject joinClassByUserId(String token, String userIds, String stuId) {
        return post(Body.create()
                .add("token", token)
                .add("userIds", userIds)
                .add("stuId", stuId)
                .build());
    }

    @Test
    public void test() {
        JSONObject accounts = UserListByAccount.of()
                .userListByAccount(Jigou.getInstance().getToken(),
                        "robot0001,robot0020");

        String userIds = accounts.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject object = (JSONObject) i;
            return object.getStr("userId");
        }).collect(Collectors.joining(","));
        System.out.println(userIds);

        JSONObject jsonObject = joinClassByUserId(Jigou.getInstance().getToken(),
                userIds, "1408");

        SampleAssert.assertCode200(jsonObject);
    }


    @Override
    public String route() {
        return "mizhu/web/lesson/joinClassByUserId";
    }
}
