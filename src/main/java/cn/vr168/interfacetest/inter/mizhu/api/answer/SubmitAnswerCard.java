package cn.vr168.interfacetest.inter.mizhu.api.answer;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor(staticName = "of")
public class SubmitAnswerCard extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String token;
        private String lessonId;
        private String classroomId;
        private String answerCardId;
        private JSONArray _array;
    }

    @Step
    public JSONObject submitAnswerCard(Bean bean) {
        Map<String, Object> map = beanToMap(bean);
        map.put("answerListJson", bean._array.toString());
        return post(map);
    }

    @Override
    public String route() {
        return "mizhu/api/answer/submitAnswerCard";
    }
}
