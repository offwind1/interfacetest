package inter.flow;


import cn.hutool.db.Entity;
import cn.hutool.json.JSONObject;
import config.ConfigUtil;
import config.Environment;
import inter.kaca.school.clazz.manage.creat.Excel;
import inter.mizhu.api.mobile.Login;
import inter.mizhu.web.orgInfo.AddTeacher;
import inter.mizhu.web.usr.OrgDelTeacher;
import inter.mizhu.web.usr.OrgTeacherInfo;
import inter.mizhumanage.web.usr.UsrManage;
import org.testng.annotations.Test;
import parameter.people.Admin;
import parameter.people.Jigou;
import parameter.people.Teacher;
import util.DBUtil;
import util.SampleAssert;
import util.TeacherExcelCreator;

import java.sql.SQLException;
import java.util.Optional;


public class AddTeacherFlow {

    private final String phone_registered = Environment.getValue("phone.registered");
    private final String phone_unregistered = Environment.getValue("phone.unregistered");

    /**
     * 机构添加教师
     */

    /**
     * 批量添加教师
     * 添加未注册的一个
     * 再添加已注册的一个
     */
    @Test(description = "批量添加教师", groups = {"database"})
    public void testPiLiang() throws SQLException {
        try {
            TeacherExcelCreator excelCreator = TeacherExcelCreator.of();
            excelCreator = excelCreator
                    .addTeacher("9年1班", "二年级", "小龙", "语文", phone_registered)
                    .addTeacher("9年1班", "二年级", "小龙", "语文", phone_unregistered);
            JSONObject object = Excel.of().excel(excelCreator.build(), Jigou.getInstance().getOrgId(), "2020");
            SampleAssert.assertResult0(object);

            //在列表中
            list(phone_unregistered, "小龙");
            list(phone_registered);
            //未注册的用户账号创建
            authen(phone_unregistered);
            //未注册教师，创建的账号 可以登录
            JSONObject login = Login.of().login(phone_unregistered, "888888");
            SampleAssert.assertResult0(login);

        } catch (AssertionError error) {
            throw error;
        } finally {
            Optional<Entity> optional = DBUtil.selectUserWithPhone(phone_unregistered);
            String userId = optional.get().getStr("user_id");
            delTeacher(userId);
            DBUtil.delUserWithPhone(phone_unregistered);

            optional = DBUtil.selectUserWithPhone(phone_registered);
            userId = optional.get().getStr("user_id");
            delTeacher(userId);
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
    @Test(description = "新增已注册教师")
    public void test() {
        Teacher teacher = new Teacher(phone_registered, "111111");

        try {
            JSONObject jsonObject = AddTeacher.of().addTeacher(AddTeacher.Bean.builder()
                    .token(Jigou.getInstance().getToken())
                    .orgId(Jigou.getInstance().getOrgId())
                    .phone(teacher.getUserPhone())
                    .userId(teacher.getUserId())
                    .nickname(teacher.getName())
                    .signIn("1")
                    .build());
            SampleAssert.assertCode200(jsonObject);
            list(phone_registered);
        } catch (AssertionError error) {
            throw error;
        } finally {
            delTeacher(teacher.getUserId());
        }
    }


    /**
     * 添加未注册的教师
     * 断言：
     * *使用手机号注册账号
     * **验证账号是否通过实名认证
     * **验证账号是否有默认密码
     * *机构教师列表
     * **显示该新注册的账号
     */
    @Test(description = "添加未注册的教师", groups = {"database"})
    public void test1() throws SQLException {
        String phone = phone_unregistered;
        String name = "王老师";

        try {
            JSONObject jsonObject = AddTeacher.of().addTeacher(AddTeacher.Bean.builder()
                    .token(Jigou.getInstance().getToken())
                    .signIn("0")
                    .orgId(Jigou.getInstance().getOrgId())
                    .phone(phone)
                    .nickname(name)
                    .build());
            SampleAssert.assertCode200(jsonObject);

            //搜索教师管理列表，查看新增的教师是否在列表中
            list(phone, name);

            //查看新建的教师，是否有进行认证
            authen(phone);

            // TODO 新建账号尝试登录，密码默认为……
            //未注册教师，创建的账号 可以登录
            JSONObject login = Login.of().login(phone, "888888");
            SampleAssert.assertResult0(login);

        } catch (AssertionError error) {
            throw new RuntimeException(error);
        } finally {
            Optional<Entity> optional = DBUtil.selectUserWithPhone(phone);
            String userId = optional.get().getStr("user_id");
            delTeacher(userId);
            DBUtil.delUserWithPhone(phone);
        }
    }

    private void list(String phone) {
        JSONObject teacherInfo = OrgTeacherInfo.of().orgTeacherInfo(
                OrgTeacherInfo.Bean.builder()
                        .token(Jigou.getInstance().getToken())
                        .currentPage("1")
                        .pageSize("100")
                        .phone(phone)
                        .build());
        SampleAssert.assertAnyMatch(teacherInfo.getJSONObject("data").getJSONArray("list"),
                "userPhone", phone);
    }

    private void list(String phone, String name) {
        JSONObject teacherInfo = OrgTeacherInfo.of().orgTeacherInfo(
                OrgTeacherInfo.Bean.builder()
                        .token(Jigou.getInstance().getToken())
                        .currentPage("1")
                        .pageSize("100")
                        .phone(phone)
                        .build());
        SampleAssert.assertAnyMatch(teacherInfo.getJSONObject("data").getJSONArray("list"),
                "userPhone", phone);
        SampleAssert.assertAnyMatch(teacherInfo.getJSONObject("data").getJSONArray("list"),
                "nickname", name);
    }

    private void authen(String phone) {
        JSONObject userManage = UsrManage.of().userManage(UsrManage.Bean.builder()
                .token(Admin.getInstance().getToken())
                .phone(phone)
                .currentPage("1")
                .pageSize("10")
                .queryType("1")
                .build());
        SampleAssert.assertAnyMatch(userManage.getJSONObject("data").getJSONArray("list"),
                "userPhone", phone);
        SampleAssert.assertStr(userManage.getJSONObject("data").getJSONArray("list")
                .getJSONObject(0), "authen", "2");
    }

    private void delTeacher(String userId) {
        OrgDelTeacher.of().orgDelTeacher(OrgDelTeacher.Bean.builder()
                .token(Jigou.getInstance().getToken())
                .orgId(Jigou.getInstance().getOrgId())
                .teacherId(userId)
                .deleteType("2")
                .build());
    }
}