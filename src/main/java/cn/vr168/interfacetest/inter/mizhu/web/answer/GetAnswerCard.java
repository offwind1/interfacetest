package cn.vr168.interfacetest.inter.mizhu.web.answer;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 查看答题卡
 */
@RequiredArgsConstructor(staticName = "of")
public class GetAnswerCard extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String lessonId;
        private String classroomId;
        private String answerCardId;
        private String token;
    }

    @Step
    public JSONObject getAnswerCard(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/answer/getAnswerCard";
    }
}
