package cn.vr168.interfacetest.util;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class ExcelCreator {
    @Getter
    protected XSSFWorkbook wb = new XSSFWorkbook();
    @Getter
    protected XSSFSheet sheet = wb.createSheet("sheet1");

    public abstract String[] getHeaders();

    public ExcelCreator() {
        init();
    }

    private void init() {
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        String[] headers = getHeaders();
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            // 第四步，创建单元格，并设置值表头
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    public byte[] build() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wb.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void saveToFile(String filePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(build());
        fileOutputStream.close();
    }

    public static void saveToFile(String filePath, Workbook document) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        fileOutputStream.write(outputStream.toByteArray());
        fileOutputStream.close();
    }
}
