package cn.vr168.interfacetest.inter.flow.teacherManage;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.kaca.school.clazz.manage.creat.Excel;
import cn.vr168.interfacetest.inter.mizhu.web.usr.OrgDelTeacher;
import cn.vr168.interfacetest.inter.mizhu.web.usr.OrgTeacherInfo;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.page.ManagePage;
import cn.vr168.interfacetest.util.SampleAssert;
import cn.vr168.interfacetest.util.TeacherExcelCreator;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AddTeacherByExcelTestFlow {

    private String phoneFormat = Environment.getValue("phone.teacherFormat");
    private String token = Jigou.getInstance().getToken();


    /**
     * 批量添加教师
     * 文件缺少教师姓名
     */
    @Test(description = "批量添加教师，文件缺失教师姓名")
    public void test() {
        TeacherExcelCreator excelCreator = TeacherExcelCreator.of().addTeachers(
                null,
                null,
                null,
                new TeacherExcelCreator.Format("语文", false),
                new TeacherExcelCreator.Format(phoneFormat, true),
                2
        );

        try {
            JSONObject excel = Excel.of().excel(excelCreator.build(), Jigou.getInstance().getOrgId(), "2020");
            throw new RuntimeException("缺少教师姓名没有报错");
        } catch (JSONException ignored) {

        }
    }

    @Test(description = "批量添加教师，缺少任教学科")
    public void test1() {
        TeacherExcelCreator excelCreator = TeacherExcelCreator.of().addTeachers(
                null,
                null,
                new TeacherExcelCreator.Format("老王", false),
                null,
                new TeacherExcelCreator.Format(phoneFormat, true),
                2
        );

        try {
            JSONObject excel = Excel.of().excel(excelCreator.build(), Jigou.getInstance().getOrgId(), "2020");
            throw new RuntimeException("缺少任教学科没有报错");
        } catch (JSONException ignored) {

        }
    }

    @Test(description = "批量添加教师，缺少手机号")
    public void test2() {
        TeacherExcelCreator excelCreator = TeacherExcelCreator.of().addTeachers(
                null,
                null,
                new TeacherExcelCreator.Format("老王", false),
                new TeacherExcelCreator.Format("语文", false),
                null,
                2
        );

        try {
            JSONObject excel = Excel.of().excel(excelCreator.build(), Jigou.getInstance().getOrgId(), "2020");
            throw new RuntimeException("缺少手机号没有报错");
        } catch (JSONException ignored) {

        }
    }

    @Test(description = "批量添加教师，手机号码不规范")
    public void test3() {
        for (String phone : new String[]{"187", "187777777777777"}) {
            TeacherExcelCreator excelCreator = TeacherExcelCreator.of().addTeachers(
                    null,
                    null,
                    new TeacherExcelCreator.Format("老王", false),
                    new TeacherExcelCreator.Format("语文", false),
                    new TeacherExcelCreator.Format(phone, false),
                    2
            );

            try {
                JSONObject excel = Excel.of().excel(excelCreator.build(), Jigou.getInstance().getOrgId(), "2020");
                throw new RuntimeException("缺少手机号没有报错");
            } catch (JSONException ignored) {

            }
        }
    }

    /**
     * 正常添加教师
     * 检查教师是否添加成功
     */
    @Test(description = "正常添加教师")
    public void test4() {
        int count = 4;
        TeacherExcelCreator excelCreator = TeacherExcelCreator.of().addTeachers(
                null,
                null,
                new TeacherExcelCreator.Format("新增教师%d", true),
                new TeacherExcelCreator.Format("语文", false),
                new TeacherExcelCreator.Format(phoneFormat, true),
                count
        );
        JSONObject excel = Excel.of().excel(excelCreator.build(), Jigou.getInstance().getOrgId(), "2020");

        //检查教师是否正常添加进入
        Set<String> phone_set = new HashSet<>();
        for (int i = 0; i < count; i++) {
            phone_set.add(String.format(phoneFormat, i));
        }

        Set<String> set = getAllTeacher(phone_set.iterator().next().substring(0, 9));

        //删除教师
        for (int i = 0; i < count; i++) {
            delTeacher(String.format(phoneFormat, i));
        }

        assert phone_set.equals(set) : "添加的教师不同";
    }

    private void delTeacher(String phone) {
        String userId = ManagePage.getUserIdByPhone(phone).orElseThrow(() -> {
            return new RuntimeException("未找到该手机号");
        });

        SampleAssert.assertCode200(OrgDelTeacher.of().orgDelTeacher(OrgDelTeacher.Bean.builder()
                .token(Jigou.getInstance().getToken())
                .orgId(Jigou.getInstance().getOrgId())
                .teacherId(userId)
                .deleteType("2")
                .build()));
    }

    private Set<String> getAllTeacher(String phone) {
        JSONObject object = OrgTeacherInfo.of().orgTeacherInfo(OrgTeacherInfo.Bean.builder()
                .currentPage("1")
                .pageSize("100")
                .token(token)
                .phone(phone)
                .build());

        return object.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("userPhone");
        }).collect(Collectors.toSet());
    }
}
