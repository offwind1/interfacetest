package inter.mizhu.web.answer;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 作业统计结果
 */
@RequiredArgsConstructor(staticName = "of")
public class GetWorkStatisticsList extends BasicsInterface {

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
    public JSONObject getWorkStatisticsList(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/answer/getWorkStatisticsList";
    }
}
