package inter.mizhu.web.lesson;

import cn.hutool.json.JSONArray;
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
public class List extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private String lessonName;
        private String lessonTypeId;
        private String pubType;
        private String recommend;
        private String currentPage;
        private String pageSize;
        private String lessonTerm;
        private String org;
        private String stockType;
        private String token;
    }

    @Step
    public JSONObject list(Bean bean) {
        return post(beanToMap(bean));
    }

    public JSONObject list(String token) {
        return list(Bean.builder()
                .token(token)
                .lessonTypeId("0")
                .currentPage("1")
                .pageSize("10")
                .lessonTerm("0")
                .org("0")
                .stockType("0")
                .build());
    }

    @Test(description = "查找非微课")
    public void test() {
        JSONObject jsonObject = list(Bean.builder()
                .token(Jigou.getInstance().getToken())
                .lessonTypeId("0")
                .currentPage("1")
                .pageSize("10")
                .lessonTerm("0")
                .org("0")
                .stockType("0")
                .build());
        SampleAssert.assertCode200(jsonObject);
        jsonObject.getJSONObject("data").getJSONArray("list").stream().forEach(i -> {
            JSONObject object = (JSONObject) i;
            if (!object.getStr("lessonTerm").equals("1")) {
                throw new RuntimeException(object.getStr("lessonId") + " lessonTerm 不等于 1");
            }
        });
    }

    @Test(description = "查找微课 lessonTerm(\"2\")")
    public void test1() {
        JSONObject jsonObject = list(Bean.builder()
                .token(Jigou.getInstance().getToken())
                .lessonTypeId("0")
                .currentPage("1")
                .pageSize("10")
                .lessonTerm("2")
                .org("")
                .stockType("0")
                .build());
        SampleAssert.assertCode200(jsonObject);
        jsonObject.getJSONObject("data").getJSONArray("list").stream().forEach(i -> {
            JSONObject object = (JSONObject) i;
            if (!object.getStr("lessonTerm").equals("2")) {
                throw new RuntimeException(object.getStr("lessonId") + " lessonTerm 不等于 2");
            }
        });
    }

//    @Test(description = "查找机构课程 ")
    public void test2() {
        JSONObject jsonObject = list(Bean.builder()
                .token(Jigou.getInstance().getToken())
                .lessonTypeId("0")
                .currentPage("1")
                .pageSize("10")
                .lessonTerm("0")
                .org("1")
                .stockType("0")
                .build());
        SampleAssert.assertCode200(jsonObject);
        jsonObject.getJSONObject("data").getJSONArray("list").stream().forEach(i -> {
            JSONObject object = (JSONObject) i;
            if (!object.getStr("lessonTerm").equals("2")) {
                throw new RuntimeException(object.getStr("lessonId") + " lessonTerm 不等于 2");
            }
        });
    }


    @Override
    public String route() {
        return "mizhu/web/lesson/list";
    }
}
