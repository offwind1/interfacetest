package cn.vr168.interfacetest.kit.page;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.api.mobile.UserInfoById;
import cn.vr168.interfacetest.inter.mizhu.web.orgInfo.OrgStudentList;
import cn.vr168.interfacetest.inter.mizhu.web.usr.OrgDelTeacher;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import org.testng.TestException;

public class StudentPage {

    /**
     * 查询学生是否在机构下
     *
     * @param userId
     * @return
     */
    public static String findStudentOrgId(String userId) {
        JSONObject userInfo = UserInfoById.of().userInfoById(userId);
        String orgId = Jigou.getInstance().getOrgId();

        for (JSONObject o : userInfo.getJSONObject("data").getJSONArray("orgRelList").jsonIter()) {
            if (o.getStr("orgId").equals(orgId)) {
                return orgId;
            }
        }
        throw new TestException("orgRelList 未搜索到" + orgId);
    }

    /**
     * 查询是否存在学生
     *
     * @param account
     * @return
     */
    public static String findStudent(String account) {
        JSONObject studentList = OrgStudentList.of().orgStudentList(OrgStudentList.Bean.builder()
                .token(Jigou.getInstance().getToken())
                .pageSize("100")
                .currentPage("1")
                .build());

        for (JSONObject object : studentList.getJSONObject("data").getJSONArray("list").jsonIter()) {
            if (object.getStr("account").equals(account)) {
                return object.getStr("userId");
            }
        }
        throw new TestException("学生列表中，未搜索到 " + account);
    }


    public static void delStudent(String userId) {
        JSONObject del = OrgDelTeacher.of().orgDelTeacher(OrgDelTeacher.Bean.builder()
                .deleteType("1")
                .orgId(Jigou.getInstance().getUserId())
                .teacherId(userId)
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertCode200(del);
    }


}
