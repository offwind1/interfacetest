package cn.vr168.interfacetest.kit.factory;

import cn.vr168.interfacetest.parameter.AnswerCard;
import cn.vr168.interfacetest.parameter.Lesson;

public class AnswerCardFactory {
    public static AnswerCard creatCard(Lesson lesson) {
        return new AnswerCard(lesson.getLessonId(), lesson.getClassRoom(0).getClassroomId(), 5, 3);
    }
}
