package parameter;

import cn.hutool.core.util.RandomUtil;

public class AnswerCardFactory {
    public static AnswerCard creatCard(Lesson lesson) {
        return new AnswerCard(lesson.getLessonId(), lesson.getClassRoom(0).getClassroomId(), 5, 3);
    }
}
