package cn.vr168.interfacetest.inter.mizhu.web.course;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.bean.BeanPage;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(staticName = "of")
public class MyList extends BasicsInterface {

    @Builder
    @Data
    public static class Bean extends BeanPage {
        private String coursewareType;
        private String reply;
        private String token;
    }

    @Step
    public JSONObject myList(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/course/mylist";
    }
}
