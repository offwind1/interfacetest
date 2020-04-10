package inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class OrgTeacherInfo extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private String token;
        private String phone;
        private String name;
        private String currentPage;
        private String pageSize;
    }

    @Step
    public JSONObject orgTeacherInfo(Bean bean) {
        return post(Body.create()
                .add("token", bean.token)
                .add("pageSize", bean.pageSize)
                .add("name", bean.name)
                .add("phone", bean.phone)
                .add("currentPage", bean.currentPage)
                .build());
    }

    @Test(description = "正常调用")
    public void test() {
        JSONObject jsonObject = orgTeacherInfo(Bean.builder()
                .token(Jigou.getInstance().getToken())
                .currentPage("1")
                .pageSize("10")
                .build());
        SampleAssert.assertStr(jsonObject, "code", "200");
    }


    @Override
    public String route() {
        return "mizhu/web/usr/orgTeacherInfo";
    }
}
