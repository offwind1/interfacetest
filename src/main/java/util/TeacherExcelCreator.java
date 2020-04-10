package util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import config.ConfigUtil;
import config.Environment;
import inter.mizhu.web.usr.TeacherSearchUser;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Arrays;

@RequiredArgsConstructor(staticName = "of")
public class TeacherExcelCreator extends ExcelCreator {

    @Override
    public String[] getHeaders() {
        return new String[]{"班级名称", "年级", "教师姓名", "任教学科", "教师手机号"};
    }

    public TeacherExcelCreator addTeacher(String className, String gradeName, String teacherName, String subjectName, String teacherPhone) {
        XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(className);
        cell = row.createCell(1);
        cell.setCellValue(gradeName);
        cell = row.createCell(2);
        cell.setCellValue(teacherName);
        cell = row.createCell(3);
        cell.setCellValue(subjectName);
        cell = row.createCell(4);
        cell.setCellValue(teacherPhone);
        return this;
    }

    public static void main(String[] args) throws IOException {
        TeacherExcelCreator teacherExcelCreator = new TeacherExcelCreator()
                .addTeacher("9年1班", "二年级", "小龙", "语文",
                        Environment.getValue("phone.registered"));

        teacherExcelCreator.saveToFile("D:/111.xlsx");
        HttpRequest request = HttpRequest.post("https://develop.kacha.xin/school/class/manage/creat/excel")
//                .header("Content-Type", "application/json;charset=UTF-8")
                .form("excelFile", teacherExcelCreator.build(), "123.xlsx")
                .form("schoolId", "8386")
                .form("year", "2020");

        System.out.println(request.headers());

        HttpResponse response = request.execute();
        System.out.println(response.body());
    }


}
