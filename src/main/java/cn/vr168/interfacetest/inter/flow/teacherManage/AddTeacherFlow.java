package cn.vr168.interfacetest.inter.flow.teacherManage;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.mizhu.api.mobile.GetUserInfoByPhone;
import cn.vr168.interfacetest.inter.mizhu.web.orgInfo.AddTeacher;
import cn.vr168.interfacetest.inter.mizhu.web.usr.CheckTeacher;
import cn.vr168.interfacetest.inter.mizhu.web.usr.OrgDelTeacher;
import cn.vr168.interfacetest.inter.mizhu.web.usr.OrgTeacherInfo;
import cn.vr168.interfacetest.page.ManagePage;
import cn.vr168.interfacetest.parameter.people.Student;
import cn.vr168.interfacetest.parameter.people.Teacher;
import org.testng.TestException;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.util.SampleAssert;


public class AddTeacherFlow {

    private String token = Jigou.getInstance().getToken();
    private String userId = Jigou.getInstance().getUserId();
    private String orgId = Jigou.getInstance().getOrgId();

    private final String phone_registered = Environment.getValue("phone.registered");
    private final String phone_unregistered = Environment.getValue("phone.unregistered");


    public String findTeacher(String phone) {
        JSONObject teacherInfo = OrgTeacherInfo.of().orgTeacherInfo(OrgTeacherInfo.Bean.builder()
                .currentPage("1")
                .pageSize("100")
                .token(token)
                .phone(phone)
                .build());
        for (JSONObject object : teacherInfo.getJSONObject("data").getJSONArray("list").jsonIter()) {
            if (object.getStr("userPhone").equals(phone)) {
                return object.getStr("userId");
            }
        }

        throw new TestException("教师列表中，未搜索到 " + phone);
    }

    /**
     * 查询已存在教师
     * 若存在则删除它
     */
    @Test(description = "查询已存在的教师，若存在就删除")
    public void test() {
        JSONObject checkTeacher = CheckTeacher.of().checkTeacher(token, phone_registered);
        if (checkTeacher.getStr("code").equals("300")) {
            SampleAssert.assertStr(checkTeacher, "code", "300");
            SampleAssert.assertMsg(checkTeacher, "已存在该教师");

            String teacherId = findTeacher(phone_registered);

            JSONObject delTeacher = OrgDelTeacher.of().orgDelTeacher(OrgDelTeacher.Bean.builder()
                    .deleteType("2")
                    .orgId(userId)
                    .teacherId(teacherId)
                    .token(token)
                    .build());
            SampleAssert.assertCode200(delTeacher);

            try {
                findTeacher(phone_registered);
                throw new RuntimeException("删除教师后，依旧可以搜索到该教师");
            } catch (TestException ignored) {

            }
        }
    }

    /**
     * 添加已注册的教师
     * checkTeacher 接口，返回已注册
     * addTeacher   添加机构教师
     * 断言：
     * *机构教师列表
     * **显示该教师
     */
    @Test(description = "新增未存在的已注册教师", dependsOnMethods = {"test"})
    public void test1() {
        JSONObject checkTeacher = CheckTeacher.of().checkTeacher(token, phone_registered);
        SampleAssert.assertCode200(checkTeacher);
        String teacherId = checkTeacher.getJSONObject("data").getJSONObject("userInfo").getStr("userId");
        String nickname = checkTeacher.getJSONObject("data").getJSONObject("userInfo").getStr("nickname");

        JSONObject jsonObject = AddTeacher.of().addTeacher(AddTeacher.Bean.builder()
                .token(Jigou.getInstance().getToken())
                .orgId(Jigou.getInstance().getOrgId())
                .phone(phone_registered)
                .userId(teacherId)
                .nickname(nickname)
                .signIn("1")
                .build());
        SampleAssert.assertCode200(jsonObject);
        findTeacher(phone_registered);
    }

    /**
     * 新增未注册的教师
     * <p>
     * 检查教师的认证状态
     * 检查教师的账号状态
     * <p>
     * 清理
     * 通过合并删除账号
     * 机构中删除教师
     */
    @Test(description = "新增未注册的教师，检查认证状态")
    public void test3() {
        //检查教师是否注册
        JSONObject checkTeacher = CheckTeacher.of().checkTeacher(token, phone_unregistered);
        SampleAssert.assertMsg(checkTeacher, "未注册");

        //添加/新增教师
        JSONObject addTeacher = AddTeacher.of().addTeacher(AddTeacher.Bean.builder()
                .nickname("新增教师" + RandomUtil.randomString(6))
                .orgId(orgId)
                .phone(phone_unregistered)
                .signIn("0")
                .token(token)
                .build());
        SampleAssert.assertCode200(addTeacher);

        // 新增教师是否在教师列表中
        String userId = findTeacher(phone_unregistered);

        //获取教师的认证状态
        JSONObject userInfo = GetUserInfoByPhone.of().getUserInfoByPhone(phone_unregistered);

        //TODO 账号默认的密码
        new Teacher(phone_unregistered, "8cheng");

        //删除教师
        delTeacher(userId);

        //通过合并删除教师
        ManagePage.DeleteAccountByMerge(phone_unregistered);

        SampleAssert.assertStr(userInfo.getJSONObject("data"), "authen", "2");
    }

    private void delTeacher(String userId) {
        SampleAssert.assertCode200(OrgDelTeacher.of().orgDelTeacher(OrgDelTeacher.Bean.builder()
                .token(Jigou.getInstance().getToken())
                .orgId(Jigou.getInstance().getOrgId())
                .teacherId(userId)
                .deleteType("2")
                .build()));
    }
}