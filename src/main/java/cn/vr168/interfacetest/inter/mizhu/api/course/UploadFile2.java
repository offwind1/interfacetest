package cn.vr168.interfacetest.inter.mizhu.api.course;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.util.JsonProperties;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor(staticName = "of")
public class UploadFile2 extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private String sourceUrl;
        private String lessonId;
        private String classroomId;
        private String coursewareType;
        private String coursewareName;
        private String token;

    }

    @Step
    public JSONObject uploadFile2(Bean bean) {
        Map<String, Object> map = beanToMap(bean);
        map.put("faceImg", "http://images.mizholdings.com/DPMIt6inrH3L~w9g.png");
        return post(map);
    }

    @Override
    public String route() {
        return "mizhu/api/course/uploadFile2";
    }
}
