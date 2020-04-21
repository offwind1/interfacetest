package cn.vr168.interfacetest.kit.excelCreator;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.IOException;

@RequiredArgsConstructor(staticName = "of")
public class StudentExcelCreator extends ExcelCreator {

    @Data
    @Builder
    public static class Bean {
        private String nameFormat = "";
        private String accountFormat = "";
        private String password = "";
        private String phoneFormat = "";
        private String schoolName = "";
        private String gradeName = "";
        private String className = "";
        private String year = "";
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"姓名", "账号", "密码", "手机号", "学校", "年级", "班级", "级数"};
    }

    public StudentExcelCreator addStudent(String nameFormat, String accountFormat, String password, int count) {
        for (int i = 1; i <= count; i++) {
            XSSFRow row = sheet.createRow(i);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(String.format(nameFormat, i));

            cell = row.createCell(1);
            cell.setCellValue(String.format(accountFormat, i));
            cell = row.createCell(2);
            cell.setCellValue(password);
        }
        return this;
    }

    public StudentExcelCreator addStudent(String nameFormat, String accountFormat, String password, int start, int count) {
        for (int i = 0; i < count; i++) {
            XSSFRow row = sheet.createRow(i + 1);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(String.format(nameFormat, start + i));
            cell = row.createCell(1);
            cell.setCellValue(String.format(accountFormat, start + i));
            cell = row.createCell(2);
            cell.setCellValue(password);
        }
        return this;
    }

    public StudentExcelCreator addStudent(Bean bean, int start, int count) {
        for (int i = 0; i < count; i++) {
            XSSFRow row = sheet.createRow(i + 1);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(String.format(bean.nameFormat, start + i));
            cell = row.createCell(1);
            cell.setCellValue(String.format(bean.accountFormat, start + i));
            cell = row.createCell(2);
            cell.setCellValue(bean.password);
            cell = row.createCell(3);
            cell.setCellValue(bean.phoneFormat);
            cell = row.createCell(4);
            cell.setCellValue(bean.schoolName);
            cell = row.createCell(5);
            cell.setCellValue(bean.gradeName);
            cell = row.createCell(6);
            cell.setCellValue(bean.className);
            cell = row.createCell(7);
            cell.setCellValue(bean.year);
        }
        return this;
    }

    public static void main(String[] args) throws IOException {
        StudentExcelCreator teacherExcelCreator = new StudentExcelCreator()
                .addStudent(Bean.builder()
                        .nameFormat("人工智能%04d")
                        .accountFormat("ai%04d")
                        .password("111111")
                        .build(), 201, 5);

        //"人工智能%04d", "ai%04d", "111111"
        teacherExcelCreator.saveToFile("D:/111.xlsx");
    }


}
