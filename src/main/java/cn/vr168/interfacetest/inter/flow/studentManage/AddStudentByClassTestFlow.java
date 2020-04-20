package cn.vr168.interfacetest.inter.flow.studentManage;

import cn.vr168.interfacetest.page.StudentPage;
import cn.vr168.interfacetest.parameter.Clazz;
import cn.vr168.interfacetest.parameter.people.Student;
import org.testng.annotations.Test;

/**
 * 通过 班级添加学生管理
 */
public class AddStudentByClassTestFlow {

    /**
     * 班级添加的学生，会同步到学生管理中
     */
    @Test(description = "班级添加的学生，会同步到学生管理中")
    public void test() {
        String account = "studentbyclass";
        Clazz clazz = Clazz.creat();
        clazz.addStudentByExcel("通过班级添加的学生", account, 1);
        String userId = StudentPage.findStudent(account);
        StudentPage.delStudent(userId);
        clazz.delClass();
    }
}
