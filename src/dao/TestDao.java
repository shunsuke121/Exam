package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    public List<Test> filter(int entYear, String classNum, Subject subject, int count, School school) throws Exception {
        return filter(entYear, classNum, subject, count, school, null);
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, int count, School school, String studentKey) throws Exception {
        List<Test> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.NO, s.NAME, s.ENT_YEAR, s.CLASS_NUM, t.POINT ");
        sql.append("FROM STUDENT s ");
        sql.append("LEFT JOIN TEST t ");
        sql.append("ON s.NO = t.STUDENT_NO ");
        sql.append("AND t.SUBJECT_CD = ? ");
        sql.append("AND t.NO = ? ");
        sql.append("AND t.SCHOOL_CD = ? ");
        sql.append("WHERE s.ENT_YEAR = ? ");
        sql.append("AND s.CLASS_NUM = ? ");
        sql.append("AND s.SCHOOL_CD = ? ");

        boolean useStudentKey = studentKey != null && !studentKey.trim().isEmpty();
        if (useStudentKey) {
            sql.append("AND (s.NO LIKE ? OR s.NAME LIKE ?) ");
        }

        sql.append("ORDER BY s.NO");

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int idx = 1;
            ps.setString(idx++, subject.getCd());
            ps.setInt(idx++, count);
            ps.setString(idx++, school.getSchoolCd());
            ps.setInt(idx++, entYear);
            ps.setString(idx++, classNum);
            ps.setString(idx++, school.getSchoolCd());

            if (useStudentKey) {
                ps.setString(idx++, "%" + studentKey + "%");
                ps.setString(idx++, "%" + studentKey + "%");
            }

            ResultSet rs = ps.executeQuery();

            // 科目名取得
            SubjectDao subDao = new SubjectDao();
            Subject fullSubject = subDao.get(school, subject.getCd());

            while (rs.next()) {
                Student stu = new Student();
                stu.setNo(rs.getString("NO"));
                stu.setName(rs.getString("NAME"));
                stu.setEntYear(rs.getInt("ENT_YEAR"));
                stu.setClassNum(rs.getString("CLASS_NUM"));

                Test test = new Test();
                test.setStudent(stu);
                test.setSubject(fullSubject);
                test.setNum(count);
                test.setSchool(school);
                test.setPoint(rs.getInt("POINT"));

                list.add(test);
            }
        }

        return list;
    }

    public boolean save(Test test) throws Exception {
        String sql =
            "MERGE INTO TEST " +
            "(STUDENT_NO, SUBJECT_CD, NO, SCHOOL_CD, POINT, CLASS_NUM) " +
            "KEY(STUDENT_NO, SUBJECT_CD, NO, SCHOOL_CD) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, test.getStudent().getNo());
            ps.setString(2, test.getSubject().getCd());
            ps.setInt(3, test.getNum());
            ps.setString(4, test.getSchool().getSchoolCd());
            ps.setInt(5, test.getPoint());
            ps.setString(6, test.getStudent().getClassNum());

            ps.executeUpdate();
        }

        return true;
    }

    public List<Test> filterByStudent(Student student, List<Subject> subjectList) throws Exception {
        List<Test> list = new ArrayList<>();

        String sql = "SELECT * FROM TEST WHERE STUDENT_NO = ? AND SCHOOL_CD = ? ORDER BY SUBJECT_CD, NO";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getNo());
            ps.setString(2, student.getSchool().getSchoolCd());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Test test = new Test();
                test.setStudent(student);
                test.setNum(rs.getInt("NO"));
                test.setPoint(rs.getInt("POINT"));

                String subCd = rs.getString("SUBJECT_CD");
                Subject subject = subjectList.stream()
                    .filter(s -> s.getCd().equals(subCd))
                    .findFirst()
                    .orElse(null);

                test.setSubject(subject);
                test.setSchool(student.getSchool());

                list.add(test);
            }
        }

        return list;
    }

    public List<Test> filterByStudent(School school, String studentNo) throws Exception {
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();

        Student student = studentDao.get(studentNo);
        student.setSchool(school);

        List<Subject> subjects = subjectDao.filter(school);

        return filterByStudent(student, subjects);
    }

    public List<Test> filterBySubject(School school, String entYear, String classNum, String subjectCd, int num) throws Exception {
        List<Test> list = new ArrayList<>();

        String sql = "SELECT t.*, s.NAME " +
                     "FROM TEST t JOIN STUDENT s ON t.STUDENT_NO = s.NO " +
                     "WHERE t.SCHOOL_CD = ? AND t.SUBJECT_CD = ? AND s.ENT_YEAR = ? " +
                     "AND s.CLASS_NUM = ? AND t.NO = ? ORDER BY t.STUDENT_NO";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, school.getSchoolCd());
            ps.setString(2, subjectCd);
            ps.setString(3, entYear);
            ps.setString(4, classNum);
            ps.setInt(5, num);

            ResultSet rs = ps.executeQuery();

            // 科目情報の取得
            SubjectDao subDao = new SubjectDao();
            Subject fullSubject = subDao.get(school, subjectCd);

            while (rs.next()) {
                Test test = new Test();
                Student student = new Student();

                student.setNo(rs.getString("STUDENT_NO"));
                student.setName(rs.getString("NAME"));

                test.setStudent(student);
                test.setSubject(fullSubject);
                test.setNum(num);
                test.setPoint(rs.getInt("POINT"));
                test.setSchool(school);

                list.add(test);
            }
        }

        return list;
    }

    public String getSubjectName(String subjectCd) throws Exception {
        String subjectName = "";

        String sql = "SELECT NAME FROM SUBJECT WHERE CD = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, subjectCd);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                subjectName = rs.getString("NAME");
            }
        }

        return subjectName;
    }
}
