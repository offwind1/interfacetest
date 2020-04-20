package cn.vr168.interfacetest.inter.flow.studentManage;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.web.orgInfo.OrgStudentList;
import cn.vr168.interfacetest.inter.mizhu.web.usr.OrgTeacherInfo;
import cn.vr168.interfacetest.parameter.people.Jigou;
import groovy.lang.Tuple2;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchTestFlow {

    private String token = Jigou.getInstance().getToken();
    private Set<Tuple2<String, String>> students = getAllStudent("", "");

    private Set<Tuple2<String, String>> getAllStudent(String phone, String nickname) {
        JSONObject object = OrgStudentList.of().orgStudentList(OrgStudentList.Bean.builder()
                .currentPage("1")
                .pageSize("100")
                .token(token)
                .name(nickname)
                .phone(phone)
                .build());
        return object.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return new Tuple2<>(o.getStr("userPhone", ""), o.getStr("nickname"));
        }).collect(Collectors.toSet());
    }

    @Test(description = "使用姓名和手机号共同搜索")
    public void test2() {
        String name = "";
        String phone = "";

        for (Tuple2<String, String> tuple2 : students) {
            phone = tuple2.getFirst();
            name = tuple2.getSecond();
            if (ObjectUtil.isNotEmpty(phone)) {
                name = name.substring(0, 1);
                phone = phone.substring(0, 3);
                break;
            }
        }
        Set<Tuple2<String, String>> phone_set = getAllStudent(phone, name);
        final String f_name = name;
        final String f_phone = phone;

        assert students.stream().filter(i -> {
            return i.getSecond().startsWith(f_name) && i.getFirst().startsWith(f_phone);
        }).collect(Collectors.toSet()).equals(phone_set) : "使用姓名和手机号共同搜索 结果不一致";
    }
}
