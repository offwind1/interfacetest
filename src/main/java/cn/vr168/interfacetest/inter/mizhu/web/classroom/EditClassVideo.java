package cn.vr168.interfacetest.inter.mizhu.web.classroom;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class EditClassVideo extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private static String faceImg = "http://images.mizholdings.com/DPMIt6inrH3L~w9g.png";
        private String videoPath;
        private String classroomId;
        private String videoId;
        private String token;
    }

    @Step
    public JSONObject editClassVideo(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/classroom/editClassVideo";
    }
}
