package cn.vr168.interfacetest.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.vr168.interfacetest.config.Environment;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.*;

@RequiredArgsConstructor(staticName = "of")
public class TeacherExcelCreator extends ExcelCreator {

    @Data
    public static class Format {
        private boolean isFormat;
        private String format;

        public Format(String format, boolean isFormat) {
            this.format = format;
            this.isFormat = isFormat;
        }
    }

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

    public TeacherExcelCreator addTeachers(Format classNameFormat, Format gradeNameFormat, Format teacherNameFormat, Format subjectName, Format teacherPhoneFormat, Integer count) {
        for (int i = 0; i < count; i++) {
            XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            setCellValue(row.createCell(0), classNameFormat, i);
            setCellValue(row.createCell(1), gradeNameFormat, i);
            setCellValue(row.createCell(2), teacherNameFormat, i);
            setCellValue(row.createCell(3), subjectName, i);
            setCellValue(row.createCell(4), teacherPhoneFormat, i);
        }
        return this;
    }

    private void setCellValue(XSSFCell cell, Format format, int index) {
        if (ObjectUtil.isNotNull(format)) {
            if (format.isFormat) {
                cell.setCellValue(String.format(format.format, index));
            } else {
                cell.setCellValue(format.format);
            }
        }
    }

}
