package cn.vr168.interfacetest.inter.flow.studentManage;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.mizhu.api.mobile.UserInfoById;
import cn.vr168.interfacetest.inter.mizhu.web.orgInfo.AddStudentToOrg;
import cn.vr168.interfacetest.inter.mizhu.web.orgInfo.OrgStudentList;
import cn.vr168.interfacetest.inter.mizhu.web.usr.OrgDelTeacher;
import cn.vr168.interfacetest.inter.mizhu.web.usr.OrgTeacherInfo;
import cn.vr168.interfacetest.page.StudentPage;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.util.SampleAssert;
import org.testng.TestException;
import org.testng.annotations.Test;

import java.util.stream.Collectors;

public class AddStudentTestFlow {

    private String account = Environment.getValue("student.account.temp");
    private String userId;

    @Test(description = "添加学生")
    public void test() {
        JSONObject studentToOrg = AddStudentToOrg.of().addStudentToOrg(Jigou.getInstance(), account);
        SampleAssert.assertCode200(studentToOrg);

        userId = studentToOrg.getJSONObject("data")
                .getJSONArray("userList").getJSONObject(0).getStr("userId");

        //检查学生是否在学生列表中
        assert userId.equals(StudentPage.findStudent(account)) : "惊了！ userId竟然不一样";

        //检查学生是否有机构
        StudentPage.findStudentOrgId(userId);
    }

    @Test(description = "删除学生", dependsOnMethods = {"test"})
    public void test1() {
        if (ObjectUtil.isNull(userId)) {
            return;
        }

        JSONObject del = OrgDelTeacher.of().orgDelTeacher(OrgDelTeacher.Bean.builder()
                .deleteType("1")
                .orgId(Jigou.getInstance().getUserId())
                .teacherId(userId)
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertCode200(del);

        try {
            //检查学生是否在学生列表中
            StudentPage.findStudent(account);
            throw new RuntimeException("学生列表 依然存在！");
        } catch (TestException ignored) {
        }

        try {
            //检查学生是否有机构
            StudentPage.findStudentOrgId(userId);
            throw new RuntimeException("学生依然保有机构！");
        } catch (TestException ignored) {
        }
    }
}
