package cn.vr168.interfacetest.inter.flow.lessonManage;

import cn.hutool.core.util.RandomUtil;
import cn.vr168.interfacetest.parameter.Classroom;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.stream.Collectors;

public class DeleteLessonFlow {


    /**
     * 课程删除后，再编辑添加课时，课时的顺序需要正确
     */
    @Test(description = "课程删除后，再编辑添加课时，课时的顺序需要正确")
    public void test() {
        Lesson lesson = Lesson.builder().classroomCount(10).build();
        lesson.deleteClassrooms(3, 7);
        lesson = lesson.edit().classroomCount(11).build();
        Set<Integer> order = lesson.getClassrooms().stream().map(Classroom::getClassroomOrdery).collect(Collectors.toSet());
        System.out.println(order);
        assert order.size() == lesson.getClassrooms().size() : "ClassroomOrder 有重复的";
    }

    /**
     * 删除课时
     * *课时数量相同
     */
    @Test(description = "删除课时后，课时显示的数量需要相同")
    public void test1() {
        int count = 4;
        Lesson lesson = Lesson.builder().classroomCount(count).build();
        lesson.applied();
        lesson.deleteClassroom(RandomUtil.randomInt(count));

        Lesson nowLesson = new Lesson(Jigou.getInstance().getToken(), lesson.getLessonId());
        assert nowLesson.getClassroomCount() == nowLesson.getClassrooms().size() : "标记的课时数和真正存在的课程数量不一致";
    }


}
