package cn.vr168.interfacetest.inter;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.vr168.interfacetest.config.ConfigUtil;
import io.qameta.allure.Allure;
//import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Log4j
public abstract class BasicsInterface {
    private static final String host = ConfigUtil.getWebHost();//"https://api.mizholdings.com/t/";
    private static final String host_manage = ConfigUtil.getManageHost();
    private static final String host_kaca = ConfigUtil.getKacaHost();

    public abstract String route();

    protected String getUrl() {
        String route = route();
        if (route.startsWith("/")) {
            route = route.substring(1);
        }

        if (route.startsWith("mizhumanage")) {
            return host_manage + route;
        } else if (route.startsWith("mizhu")) {
            return host + route;
        } else {
            return host_kaca + route;
        }
    }

    public HttpResponse get(HttpRequest request) {
        log.debug("——————————————————————————————————————————————————");
        log.debug("url:" + getUrl());
        log.debug("body:" + request.form());
//        Allure.addAttachment("url", getUrl());
//        Allure.addAttachment("body", request.form().toString());
        Allure.addAttachment("request:" + getUrl(), request.form().toString());

        HttpResponse response = request.execute();
//        log.debug("Status:" + response.getStatus());
//        log.debug("response:" + response.body());
//        Allure.addAttachment("Status", String.valueOf(response.getStatus()));
//        Allure.addAttachment("response", response.body());
        return response;
    }

    public JSONObject post(Map<String, Object> body) {
        log.debug("——————————————————————————————————————————————————");
        log.debug("url:" + getUrl());
        log.debug("body:" + body);

        Allure.addAttachment("request:" + getUrl(), body.toString());

        HttpRequest request = HttpRequest.post(getUrl());
        request.form(body);
        HttpResponse response = request.execute();

        log.debug("Status:" + response.getStatus());
        log.debug("response:" + response.body());
        Allure.addAttachment("response", response.body());

        JSONObject jsonObject = JSONUtil.parseObj(response.body());
        return jsonObject;
    }

    public JSONObject post(HttpRequest request) {
        log.debug("——————————————————————————————————————————————————");
        log.debug("url:" + getUrl());
        log.debug("body:" + request.form());

        Allure.addAttachment("request:" + getUrl(), request.form().toString());
        HttpResponse response = request.execute();
        log.debug("Status:" + response.getStatus());
        log.debug("response:" + response.body());
        Allure.addAttachment("response", response.body());

        return JSONUtil.parseObj(response.body());
    }

    private Map<String, Object> _beanToMap(Object bean) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

        for (Field field : bean.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().startsWith("_")) {
                continue;
            }

            if (field.get(bean) != null) {
                map.put(field.getName(), field.get(bean));
            } else {
                map.put(field.getName(), "");
            }
        }
        return map;
    }

    public Map<String, Object> beanToMap(Object o) {
        try {
            return _beanToMap(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("bean 转换 map 出错");
        }
    }

}
