package inter.mizhu.web.grade;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.GradeUtil;
import parameter.people.Jigou;
import parameter.people.User;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class AddClass extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String token;
        private String orgName;
        private String gradeId;
        private String className;
        private String rankNum;
        private String gradeName;
        private String orgId;
    }

    @Step
    public JSONObject addClass(Bean bean) {
        return post(beanToMap(bean));
    }

    public JSONObject addClass(User user, String className, String gradeId) {
        return addClass(Bean.builder()
                .token(user.getToken())
                .orgName(user.getOrgName())
                .orgId(user.getOrgId())
                .gradeId(gradeId)
                .gradeName(GradeUtil.getGradeNames(gradeId))
                .className(className)
                .rankNum("2019")
                .build());
    }

    @Test
    public void test() {
        Jigou jigou = Jigou.getInstance();
        String className = "测试";
        try {
            JSONObject jsonObject = addClass(Jigou.getInstance(), className, "1");
            SampleAssert.assertCode200(jsonObject);
        } catch (AssertionError error) {
            throw error;
        } finally {
            ClassList.of().classList(jigou.getToken(), jigou.getOrgName())
                    .getJSONObject("data").getJSONArray("list").stream().forEach(i -> {
                JSONObject o = (JSONObject) i;
                if (o.getStr("className").equals(className)) {
                    DelClass.of().delClass(jigou.getToken(), o.getStr("stuId"));
                }
            });
        }
    }


    @Override
    public String route() {
        return "mizhu/web/grade/addClass";
    }
}
