package inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Teacher;
import util.Body;

@RequiredArgsConstructor(staticName = "of")
public class TeacherSearchUser extends BasicsInterface {

    @Step
    public JSONObject teacherSearchUser(String token, String name) {
        return post(Body.create()
                .add("token", token)
                .add("name", name)
                .build());
    }

    @Test(description = "查找robot0222")
    public void test() {
        JSONObject jsonObject = teacherSearchUser(Teacher.getInstance().getToken(), "robot0222");
        assert jsonObject.getJSONObject("data").getJSONArray("list").stream().allMatch(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("account").equals("robot0222");
        });
    }

//    @Test(description = "name为空")
    public void test1() {
        JSONObject jsonObject = teacherSearchUser(Teacher.getInstance().getToken(), "");
        assert !jsonObject.getStr("code").equals("200");
    }

//    @Test(description = "token为空")
    public void test2() {
        JSONObject jsonObject = teacherSearchUser("", "robot0222");
        assert !jsonObject.getStr("code").equals("200");
    }

    @Test(description = "查找手机号")
    public void test3() {
        JSONObject jsonObject = teacherSearchUser(Teacher.getInstance().getToken(), "18767126032");
        assert jsonObject.getJSONObject("data").getJSONArray("list").stream().allMatch(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("userPhone").equals("18767126032");
        });
    }

    @Test(description = "查找昵称")
    public void test4() {
        JSONObject jsonObject = teacherSearchUser(Teacher.getInstance().getToken(), "杨某某");
        assert jsonObject.getJSONObject("data").getJSONArray("list").stream().allMatch(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("nickname").equals("杨某某");
        });
    }


    @Override
    public String route() {
        return "/mizhu/web/usr/teacherSearchUser";
    }
}
