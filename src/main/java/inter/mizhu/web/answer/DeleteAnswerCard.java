package inter.mizhu.web.answer;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 删除答题卡
 */
@RequiredArgsConstructor(staticName = "of")
public class DeleteAnswerCard extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String lessonId;
        private String classroomId;
        private String answerCardId;
        private String token;
    }

    @Step
    public JSONObject deleteAnswerCard(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/answer/deleteAnswerCard";
    }
}
