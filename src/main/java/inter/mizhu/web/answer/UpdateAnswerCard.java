package inter.mizhu.web.answer;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * 修改答题卡
 */
@RequiredArgsConstructor(staticName = "of")
public class UpdateAnswerCard extends BasicsInterface {

    @Step
    public JSONObject updateAnswerCard(AddAnswerCard.Bean bean, String answerCardId) {
        Map<String, Object> map = beanToMap(bean);
        map.put("objectiveItemAnswer", bean.getObjectiveItemAnswer());
        map.put("answerCardId", answerCardId);
        return post(map);
    }


    @Override
    public String route() {
        return "mizhumanage/web/answer/updateAnswerCard";
    }
}
