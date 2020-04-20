package cn.vr168.interfacetest.parameter;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.web.answer.AddAnswerCard;
import cn.vr168.interfacetest.inter.mizhu.web.answer.GetAnswerCard;
import lombok.Data;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.util.SampleAssert;

@Data
public class AnswerCard {
    private int objectiveItemCount;
    private int subjectiveItemCount;
    private String lessonId;
    private String classroomId;
    private String answerCardId;
    private String cardName;
    private JSONObject data = null;

    public AnswerCard(String lessonId, String classroomId, int objectiveItemCount, int subjectiveItemCount) {
        this.lessonId = lessonId;
        this.classroomId = classroomId;
        this.objectiveItemCount = objectiveItemCount;
        this.subjectiveItemCount = subjectiveItemCount;
        this.cardName = "答题卡" + RandomUtil.randomString(6);
        AddAnswerCard.Bean bean = AddAnswerCard.Bean.builder()
                .objectiveItemCount(objectiveItemCount)
                .subjectiveItemCount(subjectiveItemCount)
                .lessonId(lessonId)
                .classroomId(classroomId)
                .cardName(this.cardName)
                .token(Jigou.getInstance().getToken())
                .build();

        JSONObject addAnswerCard = AddAnswerCard.of().addAnswerCard(bean);
        SampleAssert.assertResult0(addAnswerCard);
        this.answerCardId = addAnswerCard.getJSONObject("data").getJSONObject("la").getStr("answerCardId");
    }

    public AnswerCard(JSONObject object) {
        this.data = object;
        JSONObject la = object.getJSONObject("data").getJSONObject("la");
        this.lessonId = la.getStr("lessonId");
        this.classroomId = la.getStr("classroomId");
        this.answerCardId = la.getStr("answerCardId");
        this.subjectiveItemCount = la.getJSONArray("subjectiveItemList").size();
        this.objectiveItemCount = la.getJSONArray("objectiveItemList").size();
        this.cardName = la.getStr("cardName");
    }

    private JSONObject getData() {
        if (data == null) {
            data = GetAnswerCard.of().getAnswerCard(GetAnswerCard.Bean.builder()
                    .answerCardId(answerCardId)
                    .classroomId(classroomId)
                    .lessonId(lessonId)
                    .token(Jigou.getInstance().getToken())
                    .build());
        }
        return data;
    }

    /**
     * 获取客观题
     *
     * @return
     */
    public JSONArray getObjectiveItemList() {
        return getData().getJSONObject("data").getJSONObject("la").getJSONArray("objectiveItemList");
    }

    /**
     * 获取主观题
     *
     * @return
     */
    public JSONArray getSubjectiveItemList() {
        return getData().getJSONObject("data").getJSONObject("la").getJSONArray("subjectiveItemList");
    }

    public Integer getAnswerCount() {
        return objectiveItemCount + subjectiveItemCount;
    }
}
