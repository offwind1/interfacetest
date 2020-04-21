package cn.vr168.interfacetest.inter.flow.lessonManage.answer;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.kit.factory.AnswerCardFactory;
import cn.vr168.interfacetest.kit.factory.ClassFactory;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.inter.mizhu.api.answer.GetStudentAnswerCard;
import cn.vr168.interfacetest.inter.mizhu.api.answer.SaveAnswerCard;
import cn.vr168.interfacetest.inter.mizhu.api.answer.SubmitAnswerCard;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.OptionList;
import cn.vr168.interfacetest.inter.mizhu.web.answer.*;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.*;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.parameter.people.Student;
import cn.vr168.interfacetest.parameter.people.User;
import cn.vr168.interfacetest.kit.util.SampleAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicAnswerFlow {
    private Lesson lesson = LessonFactory.creat();

    /**
     * 创建答题卡
     * 修改答题卡
     * 查看答题卡
     * 删除答题卡
     */
//    @Test
    public void test() {
        //创建答题卡
        AddAnswerCard.Bean bean = AddAnswerCard.Bean.builder()
                .objectiveItemCount(5)
                .subjectiveItemCount(3)
                .lessonId(lesson.getLessonId())
                .classroomId(lesson.getClassRoom(0).getClassroomId())
                .cardName("答题卡" + RandomUtil.randomString(6))
                .token(Jigou.getInstance().getToken())
                .build();

        JSONObject addAnswerCard = AddAnswerCard.of().addAnswerCard(bean);
        SampleAssert.assertCode200(addAnswerCard);
        String answerCardId = addAnswerCard.getJSONObject("data").getJSONObject("la").getStr("answerCardId");

        checkGetAnswerCard(bean, answerCardId);

        //修改答题卡
        bean = AddAnswerCard.Bean.builder()
                .objectiveItemCount(10)
                .subjectiveItemCount(3)
                .lessonId(lesson.getLessonId())
                .classroomId(lesson.getClassRoom(0).getClassroomId())
                .cardName("答题卡" + RandomUtil.randomString(6))
                .token(Jigou.getInstance().getToken())
                .build();

        JSONObject updateAnswerCard = UpdateAnswerCard.of().updateAnswerCard(bean, answerCardId);
        SampleAssert.assertCode200(updateAnswerCard);
        answerCardId = updateAnswerCard.getJSONObject("data").getJSONObject("la").getStr("answerCardId");

        checkGetAnswerCard(bean, answerCardId);

        //删除答题卡
        JSONObject deleteAnswerCard = DeleteAnswerCard.of().deleteAnswerCard(DeleteAnswerCard.Bean.builder()
                .answerCardId(answerCardId)
                .classroomId(lesson.getClassRoom(0).getClassroomId())
                .lessonId(lesson.getLessonId())
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertResult0(deleteAnswerCard);

        //查看答题卡
        JSONObject answerCard = GetAnswerCard.of().getAnswerCard(GetAnswerCard.Bean.builder()
                .answerCardId(answerCardId)
                .classroomId(lesson.getClassRoom(0).getClassroomId())
                .lessonId(lesson.getLessonId())
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertResult0(answerCard);

    }

    private void checkGetAnswerCard(AddAnswerCard.Bean bean, String answerCardId) {
        JSONObject answerCard = GetAnswerCard.of().getAnswerCard(GetAnswerCard.Bean.builder()
                .answerCardId(answerCardId)
                .classroomId(lesson.getClassRoom(0).getClassroomId())
                .lessonId(lesson.getLessonId())
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertResult0(answerCard);

        answerCard = answerCard.getJSONObject("data").getJSONObject("la");

        assert answerCard.getStr("cardName").equals(bean.getCardName()) : answerCard.getStr("cardName") + "!=" + bean.getCardName();
        assert answerCard.getStr("lessonId").equals(lesson.getLessonId()) : answerCard.getStr("lessonId") + "!=" + lesson.getLessonId();
        assert answerCard.getStr("classroomId").equals(lesson.getClassRoom(0).getClassroomId()) :
                answerCard.getStr("classroomId") + "!=" + lesson.getClassRoom(0).getClassroomId();
        assert answerCard.getInt("objectiveItemCount") == bean.getObjectiveItemCount() :
                answerCard.getStr("objectiveItemCount") + "!=" + bean.getObjectiveItemCount();
        assert answerCard.getInt("subjectiveItemCount") == bean.getSubjectiveItemCount() :
                answerCard.getStr("subjectiveItemCount") + "!=" + bean.getSubjectiveItemCount();
        assert answerCard.getInt("answerCount") == bean.getSubjectiveItemCount() + bean.getObjectiveItemCount() :
                answerCard.getStr("answerCount") + "!=" + (bean.getSubjectiveItemCount() + bean.getObjectiveItemCount());

        JSONArray array = answerCard.getJSONArray("objectiveItemList");
        String[] list = bean.get_answerList();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            assert object.getStr("realAnswer").equals(list[i]) : object.getStr("realAnswer") + "!=" + list[i];
        }

    }

    private Clazz clazz;
    int count = 5;

    /**
     * 创建课程
     * 创建班级
     * 班级添加学生
     * 课程添加班级
     * 课程通过审核
     * <p>
     * 创建答题卡
     * 学生课程详情课堂附件查看答题卡
     * 学生答题
     * <p>
     * 作业模块列表
     * 作业学校列表
     * 作业班级列表
     * <p>
     * 作业统计结果
     * 答题进度
     * 试题得分分析
     * <p>
     * 学生主观题列表
     * 学生主观题批改
     * <p>
     * 学生查看答题卡（批改后）
     */
    @Test
    public void test2() {
        Lesson lesson = LessonFactory.creat();                            //创建课程
        clazz = ClassFactory.findClass("答题卡专用");                        //创建班级
        String accountFormat = "baby%04d";
        String nameFormat = "新生娃%04d";
//        clazz.addStudentByExcel(nameFormat, accountFormat, count);   //班级添加学生
        lesson.addClass(clazz.getStuId());                              //课程添加班级
        lesson.applied();   //课程通过审核

        AnswerCard answerCard = AnswerCardFactory.creatCard(lesson);
        List<Student> students = new ArrayList<>();
        List<List<String>> answers = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String account = String.format(accountFormat, i);
            Student student = new Student(account, "111111");
            students.add(student);
            optionListCheck(student, answerCard);   // 学生课程详情课堂附件查看答题卡
            List<String> answer = studentAnswerCard(student, answerCard); // 学生答题
            answers.add(answer);
        }

        getWorkListCheck(answerCard);   //作业模块列表
        getWorkSchoolListCheck(answerCard); //提交作业的学校列表
        getWorkClassListCheck(answerCard);  //提交作业的班级列表
        getWorkStatisticsListCheck(answerCard); //作业统计结果
        getQuestionsScheduleCheck(answerCard, students); //答题进度
        answerAnalyzeCheck(answerCard); //试题得分分析
        getStudentSubjectiveItemListCheck(answerCard, answers);// 学生主观题列表

        for (Student student : students) {
            updateStudentSubjectiveItemCheck(answerCard, student);
            lastCheck(answerCard, student);
        }
    }

    @Test(description = "答题卡发布后，若有学生提交数据，不能修改")
    public void test3() {
        Lesson lesson = LessonFactory.takeOut();
        lesson.addClass(ClassFactory.findClass("答题卡专用").getStuId());
        AnswerCard answerCard = AnswerCardFactory.creatCard(lesson);
        Student student = new Student("baby0001", "111111");
        AnswerCard studentCard = getStudentAnswerCardCheck(student, answerCard, null);
//        submitAnswerCardCheck(student, studentCard); // 提交答题卡
        saveAnswerCardCheck(student, studentCard); // 保存答题卡

        //修改答题卡
        AddAnswerCard.Bean bean = AddAnswerCard.Bean.builder()
                .objectiveItemCount(10)
                .subjectiveItemCount(3)
                .lessonId(lesson.getLessonId())
                .classroomId(lesson.getClassRoom(0).getClassroomId())
                .cardName("答题卡" + RandomUtil.randomString(6))
                .token(Jigou.getInstance().getToken())
                .build();

        JSONObject updateAnswerCard = UpdateAnswerCard.of().updateAnswerCard(bean, answerCard.getAnswerCardId());
        SampleAssert.assertMsg(updateAnswerCard, "已经有学生提交答案，本答题卡不允许修改");
    }


    private void lastCheck(AnswerCard card, Student student) {
        JSONObject object = GetStudentAnswerCard.of().getStudentAnswerCard(GetStudentAnswerCard.Bean.builder()
                .answerCardId(card.getAnswerCardId())
                .classroomId(card.getClassroomId())
                .lessonIs(card.getLessonId())
                .token(student.getToken())
                .build());
        SampleAssert.assertResult0(object);
        JSONObject la = object.getJSONObject("data").getJSONObject("la");
        JSONObject o = la.getJSONArray("subjectiveItemList").getJSONObject(0);
        assert o.getStr("answerResult").equals("2");
    }


    private void updateStudentSubjectiveItemCheck(AnswerCard card, Student student) {
        JSONObject object = UpdateStudentSubjectiveItem.of().updateStudentSubjectiveItem(UpdateStudentSubjectiveItem.Bean.builder()
                .answerCardId(card.getAnswerCardId())
                .answerResult("2")
                .classroomId(card.getClassroomId())
                .seq(String.valueOf(card.getObjectiveItemCount() + 1))
                .token(Jigou.getInstance().getToken())
                .userId(student.getUserId())
                .build());

        SampleAssert.assertResult0(object);
    }

    /**
     * 学生主观题列表s
     *
     * @param card
     * @param answers
     */
    private void getStudentSubjectiveItemListCheck(AnswerCard card, List<List<String>> answers) {
        JSONObject studentSubjectiveItemList = GetStudentSubjectiveItemList.of().getStudentSubjectiveItemList(GetStudentSubjectiveItemList.Bean.builder()
                .answerCardId(card.getAnswerCardId())
                .classId(clazz.getStuId())
                .classroomId(card.getClassroomId())
                .orgId(Jigou.getInstance().getOrgId())
                .token(Jigou.getInstance().getToken())
                .seq(String.valueOf(card.getObjectiveItemCount() + 1))
                .build());
        SampleAssert.assertResult0(studentSubjectiveItemList);

        Set<String> set = studentSubjectiveItemList.getJSONObject("data").getJSONArray("caauList").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("userAnswer");
        }).collect(Collectors.toSet());

        Set<String> answers_set = answers.stream().map(i -> {
            return i.get(5);
        }).collect(Collectors.toSet());

        assert set.equals(answers_set);
    }

    /**
     * 试题得分分析
     *
     * @param card
     */
    private void answerAnalyzeCheck(AnswerCard card) {
        JSONObject answerAnalyze = AnswerAnalyze.of().answerAnalyze(AnswerAnalyze.Bean.builder()
                .answerCardId(card.getAnswerCardId())
                .classId(clazz.getStuId())
                .classroomId(card.getClassroomId())
                .orgId(Jigou.getInstance().getOrgId())
                .token(Jigou.getInstance().getToken())
                .build());

        SampleAssert.assertResult0(answerAnalyze);
    }

    /**
     * 答题进度
     *
     * @param card
     * @param students
     */
    private void getQuestionsScheduleCheck(AnswerCard card, List<Student> students) {
        JSONObject questionsSchedule = GetQuestionsSchedule.of().getQuestionsSchedule(GetQuestionsSchedule.Bean.builder()
                .answerCardId(card.getAnswerCardId())
                .classId(clazz.getStuId())
                .classroomId(card.getClassroomId())
                .orgId(Jigou.getInstance().getOrgId())
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertResult0(questionsSchedule);

        SampleAssert.assertStr(questionsSchedule.getJSONObject("data"), "answerCount", String.valueOf(card.getAnswerCount()));

        Set<String> set = questionsSchedule.getJSONObject("data").getJSONArray("answerUserList").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("userId");
        }).collect(Collectors.toSet());

        Set<String> set_stu = students.stream().map(User::getUserId).collect(Collectors.toSet());
        assert set.equals(set_stu);
    }

    /**
     * 作业统计结果
     *
     * @param card
     */
    private void getWorkStatisticsListCheck(AnswerCard card) {
        JSONObject workStatisticsList = GetWorkStatisticsList.of().getWorkStatisticsList(GetWorkStatisticsList.Bean.builder()
                .answerCardId(card.getAnswerCardId())
                .classId(clazz.getStuId())
                .classroomId(card.getClassroomId())
                .orgId(Jigou.getInstance().getOrgId())
                .token(Jigou.getInstance().getToken())
                .build());
        JSONObject object = workStatisticsList.getJSONObject("data");
        SampleAssert.assertStr(object, "studentsCount", String.valueOf(count));
        SampleAssert.assertStr(object, "notFiled", "0");

        JSONArray array = object.getJSONArray("caList");
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = array.getJSONObject(i);
            SampleAssert.assertStr(o, "allCount", "5");
            if (i < 4) {
                SampleAssert.assertStr(o, "rightCount", "5");
            } else {
                SampleAssert.assertStr(o, "rightCount", "0");
            }

            if (i == 4) {
                SampleAssert.assertStr(o, "wrongCount", "5");
            } else {
                SampleAssert.assertStr(o, "wrongCount", "0");
            }

            if (i > 4) {
                SampleAssert.assertStr(o, "nullCount", "5");
                SampleAssert.assertStr(o, "auto", "2");
            } else {
                SampleAssert.assertStr(o, "nullCount", "0");
                SampleAssert.assertStr(o, "auto", "1");
            }
        }
    }

    /**
     * 提交作业的班级列表
     *
     * @param card
     */
    private void getWorkClassListCheck(AnswerCard card) {
        JSONObject workClassList = GetWorkClassList.of().getWorkClassList(GetWorkClassList.Bean.builder()
                .answerCardId(card.getAnswerCardId())
                .classroomId(card.getClassroomId())
                .orgId(Jigou.getInstance().getOrgId())
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertResult0(workClassList);
        JSONObject object = workClassList.getJSONObject("data").getJSONArray("crList").getJSONObject(0);
        SampleAssert.assertStr(object, "className", clazz.getClassName());
        SampleAssert.assertStr(object, "classId", clazz.getStuId());
    }

    /**
     * 提交作业的学校列表
     *
     * @param card
     */
    private void getWorkSchoolListCheck(AnswerCard card) {
        JSONObject workSchoolList = GetWorkSchoolList.of().getWorkSchoolList(GetWorkSchoolList.Bean.builder()
                .answerCardId(card.getAnswerCardId())
                .classroomId(card.getClassroomId())
                .orgId(Jigou.getInstance().getOrgId())
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertResult0(workSchoolList);
        JSONObject object = workSchoolList.getJSONObject("data").getJSONArray("oiList").getJSONObject(0);
        assert object.getStr("orgId").equals(Jigou.getInstance().getOrgId()) : "orgId:" +
                object.getStr("orgId") + "!=" + Jigou.getInstance().getOrgId();
        assert object.getStr("orgName").equals(Jigou.getInstance().getOrgName()) : "orgName" +
                object.getStr("orgName") + "!=" + Jigou.getInstance().getOrgName();
    }

    /**
     * 作业模块列表
     */
    private void getWorkListCheck(AnswerCard card) {
        JSONObject workList = GetWorkList.of().getWorkList(Jigou.getInstance().getToken(), card.getLessonId());
        JSONObject object = workList.getJSONObject("data").getJSONArray("laList").getJSONObject(0);

        assert object.getStr("answerCardId").equals(card.getAnswerCardId()) : "answerCardId: " +
                object.getStr("answerCardId") + "!=" + card.getAnswerCardId();
        assert object.getStr("cardName").equals(card.getCardName()) : "cardName: " +
                object.getStr("cardName") + "!=" + card.getCardName();
        assert object.getInt("studentCount") == count : "studentCount: " +
                object.getInt("studentCount") + "!=" + count;
    }

    /**
     * 学生课程详情课堂附件查看答题卡
     */
    private void optionListCheck(Student student, AnswerCard card) {
        JSONObject optionList = OptionList.of().optionList(student.getToken(), card.getClassroomId());
        String answerCardId = optionList.getJSONObject("data").getJSONArray("laList")
                .getJSONObject(0).getStr("answerCardId");
        assert answerCardId.equals(card.getAnswerCardId());
    }

    /**
     * 学生答题操作
     *
     * @param student
     * @param card
     */
    private List<String> studentAnswerCard(Student student, AnswerCard card) {
        AnswerCard studentCard = getStudentAnswerCardCheck(student, card, null);

        List<String> saveAnswerCardCheck = saveAnswerCardCheck(student, studentCard);
        getStudentAnswerCardCheck(student, card, saveAnswerCardCheck);

        List<String> submitAnswerCardCheck = submitAnswerCardCheck(student, studentCard);
        getStudentAnswerCardCheck(student, card, submitAnswerCardCheck);

        return submitAnswerCardCheck;
    }

    /**
     * 获取学生答题卡
     *
     * @param student
     * @param card
     * @param list
     * @return
     */
    private AnswerCard getStudentAnswerCardCheck(Student student, AnswerCard card, List<String> list) {
        JSONObject object = GetStudentAnswerCard.of().getStudentAnswerCard(GetStudentAnswerCard.Bean.builder()
                .answerCardId(card.getAnswerCardId())
                .classroomId(card.getClassroomId())
                .lessonIs(card.getLessonId())
                .token(student.getToken())
                .build());
        SampleAssert.assertResult0(object);
        JSONObject la = object.getJSONObject("data").getJSONObject("la");

        if (list != null) {
            JSONArray array = la.getJSONArray("objectiveItemList");
            array.addAll(la.getJSONArray("subjectiveItemList"));
            assert array.size() == list.size();

            for (int i = 0; i < list.size(); i++) {
                assert list.get(i).equals(array.getJSONObject(i).getStr("userAnswer"));
            }
        }
        return new AnswerCard(object);
    }

    /**
     * 保存答题卡
     *
     * @param student
     * @param card
     * @return
     */
    private List<String> saveAnswerCardCheck(Student student, AnswerCard card) {
        JSONArray array = new JSONArray();
        List<String> list = new ArrayList<>();

        for (JSONObject object : card.getObjectiveItemList().jsonIter()) {
            JSONObject o = new JSONObject();
            o.put("answerId", object.getStr("answerId"));
            o.put("userAnswer", "A");
            o.put("answerType", object.getStr("answerType"));
            array.add(o);
            list.add("A");
        }

        for (JSONObject object : card.getSubjectiveItemList().jsonIter()) {
            JSONObject o = new JSONObject();
            o.put("answerId", object.getStr("answerId"));
            o.put("userAnswer", "temp");
            o.put("answerType", object.getStr("answerType"));
            array.add(o);
            list.add("temp");
        }

        JSONObject saveAnswerCard = SaveAnswerCard.of().saveAnswerCard(SaveAnswerCard.Bean.builder()
                ._array(array)
                .answerCardId(card.getAnswerCardId())
                .classroomId(card.getClassroomId())
                .lessonId(card.getLessonId())
                .token(student.getToken())
                .build());
        SampleAssert.assertResult0(saveAnswerCard);
        return list;
    }

    /**
     * 提交答题卡
     *
     * @param student
     * @param card
     * @return
     */
    private List<String> submitAnswerCardCheck(Student student, AnswerCard card) {
        JSONArray array = new JSONArray();
        List<String> list = new ArrayList<>();
        int i = 0;
        for (JSONObject object : card.getObjectiveItemList().jsonIter()) {
            JSONObject o = new JSONObject();
            String str = String.valueOf((char) (65 + i++));
            o.put("answerId", object.getStr("answerId"));
            o.put("userAnswer", str);
            o.put("answerType", object.getStr("answerType"));
            array.add(o);
            list.add(str);
        }

        for (JSONObject object : card.getSubjectiveItemList().jsonIter()) {
            JSONObject o = new JSONObject();
            String userAnswer = RandomUtil.randomString(6);
            o.put("answerId", object.getStr("answerId"));
            o.put("userAnswer", userAnswer);
            o.put("answerType", object.getStr("answerType"));
            array.add(o);
            list.add(userAnswer);
        }

        JSONObject submitAnswerCard = SubmitAnswerCard.of().submitAnswerCard(SubmitAnswerCard.Bean.builder()
                .answerCardId(card.getAnswerCardId())
                .classroomId(card.getClassroomId())
                .lessonId(card.getLessonId())
                .token(student.getToken())
                ._array(array)
                .build());
        SampleAssert.assertResult0(submitAnswerCard);
        return list;
    }


}
