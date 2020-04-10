package inter.mizhu.web.answer;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 答题进度
 */
@RequiredArgsConstructor(staticName = "of")
public class GetQuestionsSchedule extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String orgId;
        private String classId;
        private String answerCardId;
        private String classroomId;
        private String token;
    }

    @Step
    public JSONObject getQuestionsSchedule(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/answer/getQuestionsSchedule";
    }
}
