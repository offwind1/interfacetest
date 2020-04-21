package cn.vr168.interfacetest.inter.mizhu.api.mobile;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.HasToken;
import cn.vr168.interfacetest.parameter.people.Student;
import cn.vr168.interfacetest.parameter.people.Teacher;
import cn.vr168.interfacetest.kit.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class UserInfo extends BasicsInterface {

    public static UserInfo getInstance() {
        return new UserInfo();
    }

    @Step("userInfo")
    public JSONObject userInfo(HasToken hasToken) {
        return post(Body.create()
                .add("token", hasToken.getToken())
                .build());
    }

    @Test(description = "传入token")
    public void test() {
        JSONObject object = userInfo(Student.getInstance());
        assert object.getStr("code").equals("0");
    }

    @Test(description = "传入userId")
    public void test1() {
        JSONObject object = userInfo(new HasToken() {
            @Override
            public String getToken() {
                return Student.getInstance().getUserId();
            }
        });
        assert object.getStr("code").equals("0");
    }

    @Test(description = "传入空")
    public void test2() {
        JSONObject object = userInfo(new HasToken() {
            @Override
            public String getToken() {
                return "";
            }
        });

        assert object.getStr("code").equals("-100");
        assert object.getStr("msg").equals("token失效");
    }

    @Test(description = "传入随机字符串")
    public void test3() {
        JSONObject object = userInfo(new HasToken() {
            @Override
            public String getToken() {
                return "slslslslslsap";
            }
        });

        assert object.getStr("code").equals("-100");
        assert object.getStr("msg").equals("token失效");
    }

    @Test(description = "机构添加教师后，教师信息中orgRelList有机构信息，且roleType为30")
    public void test4() {
        try {
            //TODO 机构添加教师
            JSONObject object = userInfo(Teacher.getInstance());
        } catch (ArithmeticException e) {

        } finally {
            System.out.println("111");
        }
    }


    @Override
    public String route() {
        return "/mizhu/api/mobile/userInfo";
    }
}
