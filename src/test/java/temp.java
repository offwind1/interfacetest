import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.parameter.Lesson;
import org.testng.annotations.Test;

public class temp {


    @Test
    public void test() {
        String robot0001 = "4486398101015552";
        String robot0002 = "4486398102031360";
        String robot0003 = "4486398103063552";

        Lesson lesson = Lesson.builder().classroomCount(1).lessonName("助教测试" + DateUtil.date().toString()).build();
        lesson.setTeacher(robot0001);
        lesson.setSupportTeacher(robot0002 + "," + robot0003);
        lesson.addClass("1101");

        lesson.applied();
    }
}
