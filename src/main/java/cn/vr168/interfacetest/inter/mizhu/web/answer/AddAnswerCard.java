package cn.vr168.interfacetest.inter.mizhu.web.answer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.kit.util.SampleAssert;

import java.util.Map;

/**
 * 创建答题卡
 */
@RequiredArgsConstructor(staticName = "of")
public class AddAnswerCard extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String lessonId;
        private String classroomId;
        private int objectiveItemCount;
        private int subjectiveItemCount;
        private String cardName;
        private String token;
        private String[] _answerList;

        public String getObjectiveItemAnswer() {
            if (ObjectUtil.isNull(_answerList) || _answerList.length != objectiveItemCount) {
                initAnswerList();
            }

            JSONArray array = new JSONArray();
            for (int i = 0; i < objectiveItemCount; i++) {
                JSONObject object = new JSONObject();
                object.put("answerId", String.valueOf(i + 1));
                object.put("questionQtype", "1");
                object.put("realAnswer", _answerList[i]);
                array.add(object);
            }
            return array.toString();
        }

        /**
         * 初始化_answerList
         * _answerList 为null，新建长度为objectiveItemCount的数组并且补全
         * _answerList 不为null,且长度小于 objectiveItemCount
         * 则进行长度补全。
         * 若大于，进行截取
         * <p>
         * 补全规则都是ABCD依次顺序
         */
        private void initAnswerList() {
            int count;
            if (ObjectUtil.isNull(_answerList)) {
                _answerList = new String[objectiveItemCount];
                count = 0;
            } else {
                String[] temp = new String[objectiveItemCount];
                System.arraycopy(_answerList, 0, temp, 0, Math.min(_answerList.length, objectiveItemCount));
                count = _answerList.length;
                _answerList = temp;
            }

            for (int i = count; i < objectiveItemCount; i++) {
                _answerList[i] = String.valueOf((char) (65 + i % 4));
            }
        }
    }


    @Step
    public JSONObject addAnswerCard(Bean bean) {
        Map<String, Object> map = beanToMap(bean);
        map.put("objectiveItemAnswer", bean.getObjectiveItemAnswer());
        return post(map);
    }

    @Test
    public void test() {
        Lesson lesson = LessonFactory.takeOut();

        JSONObject object = addAnswerCard(Bean.builder()
                .token(Jigou.getInstance().getToken())
                .lessonId(lesson.getLessonId())
                .classroomId(lesson.getClassRoom(0).getClassroomId())
                .objectiveItemCount(10)
                .subjectiveItemCount(3)
                .cardName("答题卡" + RandomUtil.randomString(6))
                .build());

        SampleAssert.assertResult0(object);
        SampleAssert.assertMsg(object, "创建成功");
        object.getJSONObject("data").getJSONObject("la").getStr("answerCardId");
    }

    @Override
    public String route() {
        return "mizhu/web/answer/addAnswerCard";
    }
}
