package cn.vr168.interfacetest.inter.flow.teacherManage;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.web.usr.OrgTeacherInfo;
import cn.vr168.interfacetest.parameter.people.Jigou;
import groovy.lang.Tuple2;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchTestFlow {

    private String token = Jigou.getInstance().getToken();
    private Set<Tuple2<String, String>> teachers = getAllTeacher("", "");
    private Set<String> phones = teachers.stream().map(Tuple2::getFirst).collect(Collectors.toSet());
    private Set<String> nicknames = teachers.stream().map(Tuple2::getSecond).collect(Collectors.toSet());

    private Set<Tuple2<String, String>> getAllTeacher(String phone, String nickname) {
        JSONObject object = OrgTeacherInfo.of().orgTeacherInfo(OrgTeacherInfo.Bean.builder()
                .currentPage("1")
                .pageSize("100")
                .token(token)
                .name(nickname)
                .phone(phone)
                .build());
        return object.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return new Tuple2<>(o.getStr("userPhone"), o.getStr("nickname"));
        }).collect(Collectors.toSet());
    }


    @Test(description = "使用手机号模糊搜索")
    public void test() {
        String phone = phones.iterator().next().substring(0, 3);
        Set<Tuple2<String, String>> phone_set = getAllTeacher(phone, "");

        assert teachers.stream().filter(i -> {
            return i.getFirst().startsWith(phone);
        }).collect(Collectors.toSet()).equals(phone_set) : "使用手机号模糊搜索 结果不一致";
    }

    @Test(description = "使用姓名模糊搜索")
    public void test1() {
        String nickname = nicknames.iterator().next().substring(0, 1);
        Set<Tuple2<String, String>> phone_set = getAllTeacher("", nickname);

        assert teachers.stream().filter(i -> {
            return i.getSecond().startsWith(nickname);
        }).collect(Collectors.toSet()).equals(phone_set) : "使用姓名模糊搜索 结果不一致";
    }

    @Test(description = "使用姓名和手机号共同搜索")
    public void test2() {
        Tuple2<String, String> tuple2 = teachers.iterator().next();
        String name = tuple2.getSecond().substring(0, 1);
        String phone = tuple2.getFirst().substring(0, 3);

        Set<Tuple2<String, String>> phone_set = getAllTeacher(phone, name);

        assert teachers.stream().filter(i -> {
            return i.getSecond().startsWith(name) && i.getFirst().startsWith(phone);
        }).collect(Collectors.toSet()).equals(phone_set) : "使用姓名和手机号共同搜索 结果不一致";
    }


}
