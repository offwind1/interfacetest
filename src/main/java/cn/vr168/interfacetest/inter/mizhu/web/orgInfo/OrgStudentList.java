package cn.vr168.interfacetest.inter.mizhu.web.orgInfo;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor(staticName = "of")
public class OrgStudentList extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String currentPage;
        private String pageSize;
        private String name;
        private String phone;
        private String token;
    }

    @Step
    public JSONObject orgStudentList(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/orgInfo/orgStudentList";
    }
}
