package cn.vr168.interfacetest.inter.mizhu.web.answer;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 试题得分分析
 */
@RequiredArgsConstructor(staticName = "of")
public class AnswerAnalyze extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String orgId;
        private String classId;
        private String answerCardId;
        private String classroomId;
        private String token;
    }

    public JSONObject answerAnalyze(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/answer/answerAnalyze";
    }
}
