package inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import parameter.people.Teacher;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class CheckTeacher extends BasicsInterface {


    @Step
    public JSONObject checkTeacher(String token, String phone) {
        return post(Body.create()
                .add("token", token)
                .add("phone", phone)
                .build());
    }

    @Test(description = "已注册手机号")
    public void test() {
        JSONObject jsonObject = checkTeacher(Jigou.getInstance().getToken(),
                Teacher.getInstance().getUserPhone());

        assert jsonObject.getStr("code").equals("200");
        assert jsonObject.getStr("msg").equals("已注册");
        assert jsonObject.getJSONObject("data").getJSONObject("userInfo")
                .getStr("nickname").equals(Teacher.getInstance().getName());
        assert jsonObject.getJSONObject("data").getJSONObject("userInfo")
                .getStr("userId").equals(Teacher.getInstance().getUserId());
    }

    @Test(description = "已注册手机号 token 用userId代替")
    public void test4() {
        try {
            JSONObject jsonObject = checkTeacher(Jigou.getInstance().getUserId(),
                    Teacher.getInstance().getUserPhone());
            SampleAssert.assertMsg(jsonObject, "已注册");
            SampleAssert.assertStr(jsonObject, "code", "200");
        } catch (AssertionError error) {
            throw new RuntimeException("token用userId代替，msg结果:" + error);
        }
    }


    @Test(description = "未注册手机号")
    public void test1() {
        JSONObject jsonObject = checkTeacher(Jigou.getInstance().getToken(),
                "16501010101");

        assert jsonObject.getStr("code").equals("200");
        assert jsonObject.getStr("msg").equals("未注册");
    }

//    @Test(description = "手机号为空")
    public void test2() {
        JSONObject jsonObject = checkTeacher(Jigou.getInstance().getToken(),
                "");
        assert jsonObject.getStr("code").equals("300");
        SampleAssert.assertStr(jsonObject, "msg", "手机号无效");
    }

    @Test(description = "token为空")
    public void test3() {
        JSONObject jsonObject = checkTeacher("",
                "18767126032");
        SampleAssert.assertStr(jsonObject, "msg", "token无效");
        SampleAssert.assertStr(jsonObject, "code", "300");
    }


    @Override
    public String route() {
        return "mizhu/web/usr/checkTeacher";
    }
}
