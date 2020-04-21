package cn.vr168.interfacetest.kit.excelCreator;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.MD5;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.IOException;

/**
 * 新增课程_包含课节
 */
@RequiredArgsConstructor(staticName = "of")
public class LessonNeedClassroomExcelCreator extends ExcelCreator {
    @Builder
    public static class Bean {
        @Getter
        private String lessonName;
        private String classroomName;
        @Getter
        private DateTime startDate;
        private String subjectName;
        private String teacherName;
        @Getter
        private String teacherPhone;
        private String gradeName;
        private String className;
        private int classTime;
        @Getter
        private int classroomCount;

        public String getTeacherName() {
            if (teacherName == null) {
                return "某某人";
            }
            return teacherName;
        }

        public String getSubjectName() {
            if (subjectName == null) {
                return "科学";
            }
            return subjectName;
        }

        public String getGradeName() {
            if (gradeName == null) {
                return "高三";
            }
            return gradeName;
        }

        public String getClassName() {
            if (className == null) {
                return "全年级";
            }
            return className;
        }
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"课程名称", "课节名称", "开始日期", "开始时间", "结束时间", "学科", "老师", "手机", "年级", "班级"};
    }

    public LessonNeedClassroomExcelCreator addLesson(Bean bean, int count) {

        for (int i = 0; i < count; i++) {
            XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(bean.lessonName);
            cell = row.createCell(1);
            cell.setCellValue("课时" + String.valueOf(i + 1));
            cell = row.createCell(2);
            cell.setCellValue(DateUtil.offsetDay(bean.startDate, i).toString("yyyy-MM-dd"));
            cell = row.createCell(3);
            cell.setCellValue(bean.startDate.toString("HH:mm"));
            cell = row.createCell(4);
            cell.setCellValue(DateUtil.offsetHour(bean.startDate, 1).toString("HH:mm"));
            cell = row.createCell(5);
            cell.setCellValue(bean.getSubjectName());
            cell = row.createCell(6);
            cell.setCellValue(bean.getTeacherName());
            cell = row.createCell(7);
            cell.setCellValue(bean.teacherPhone);
            cell = row.createCell(8);
            cell.setCellValue(bean.getGradeName());
            cell = row.createCell(9);
            cell.setCellValue(bean.getClassName());
        }
        return this;
    }

    public LessonNeedClassroomExcelCreator addLesson(Bean bean) {
        return addLesson(bean, bean.classroomCount);
    }


    public static void main(String[] args) throws IOException {
        LessonNeedClassroomExcelCreator a = LessonNeedClassroomExcelCreator.of().addLesson(Bean
                .builder()
                .lessonName("课程")
                .startDate(DateUtil.date())
                .teacherPhone("18767126032")
                .build(), 5);//.saveToFile("D:/222.xlsx");
        byte[] bb = a.build();

        System.out.println(MD5.create().digestHex16(bb));
        System.out.println(MD5.create().digestHex(bb));

    }


}
