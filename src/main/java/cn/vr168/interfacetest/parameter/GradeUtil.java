package cn.vr168.interfacetest.parameter;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.util.JsonProperties;

import java.util.ArrayList;
import java.util.List;

public class GradeUtil {
    private static JSONObject json = JsonProperties.getJson("json.grade");

    public static String getGradeNames(String gradeIds) {
        if (gradeIds.startsWith(",")) {
            gradeIds = gradeIds.substring(1);
        }
        List<String> list = new ArrayList<>();
        for (String gradeId : gradeIds.split(",")) {
            list.add(json.getStr(gradeId));
        }
        return String.join(",", list);
    }

    public static void main(String[] args) {
        System.out.println(getGradeNames("7"));
        ;
    }

}
