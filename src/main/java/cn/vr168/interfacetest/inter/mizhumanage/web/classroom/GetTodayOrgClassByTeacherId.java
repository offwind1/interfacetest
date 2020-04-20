package cn.vr168.interfacetest.inter.mizhumanage.web.classroom;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Teacher;
import cn.vr168.interfacetest.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class GetTodayOrgClassByTeacherId extends BasicsInterface {

    @Step("通过userId，获取当天的课堂信息")
    public JSONObject getTodayOrgClassByTeacherId(String token, String teacherId, String fromDate) {
        return post(Body.create()
                .add("token", token)
                .add("teacherId", teacherId)
                .add("fromDate", fromDate)
                .build()
        );
    }

    @Test(description = "正常流程")
    public void test() {
        JSONObject jsonObject = getTodayOrgClassByTeacherId(
                Teacher.getInstance().getToken(),
                Teacher.getInstance().getUserId(),
                DateUtil.today()
        );
    }


    @Override
    public String route() {
        return "mizhumanage/web/classroom/getTodayOrgClassByTeacherId";
    }
}
