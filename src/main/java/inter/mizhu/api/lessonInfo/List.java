package inter.mizhu.api.lessonInfo;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.HasToken;
import parameter.people.Teacher;
import util.Body;

@RequiredArgsConstructor(staticName = "of")
public class List extends BasicsInterface {

    public JSONObject list(HasToken hasToken, String lessonName, String page, String pageSize, String orgId) {
        return post(Body.create()
                .add("token", hasToken.getToken())
                .add("lessonName", lessonName)
                .add("page", page)
                .add("pageSize", pageSize)
                .add("orgId", orgId)
                .build());
    }

    public JSONObject list(HasToken hasToken, String orgId) {
        return list(hasToken, "", "1", "10", orgId);
    }

    public JSONObject list(HasToken hasToken) {
        return list(hasToken, "", "1", "10", "");
    }


    @Test(description = "正常调用")
    public void test() {
        JSONObject jsonObject = list(Teacher.getInstance());
        assert jsonObject.getStr("result").equals("0");
    }

    @Test(description = "机构列表")
    public void test1() {
        JSONObject jsonObject = list(Teacher.getInstance(), Teacher.getInstance().getOrgRel());
        assert jsonObject.getStr("result").equals("0");
    }

    @Test(description = "没有token")
    public void test2() {
        JSONObject jsonObject = list(new HasToken() {
            @Override
            public String getToken() {
                return "";
            }
        });
        assert jsonObject.getStr("result").equals("0");
    }

    @Test(description = "传入userId")
    public void test3() {
        JSONObject jsonObject = list(new HasToken() {
            @Override
            public String getToken() {
                return Teacher.getInstance().getUserId();
            }
        }, Teacher.getInstance().getOrgId());
        assert jsonObject.getStr("result").equals("0");
    }

    @Override
    public String route() {
        return "/mizhu/api/lessonInfo/list";
    }
}
