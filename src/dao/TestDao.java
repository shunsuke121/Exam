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

    private static final String BASE_SQL = "SELECT * FROM TEST";

    /* ========== 保存（INSERT or UPDATE） ========== */
    public boolean save(Test test) throws Exception {

        if (test.getPoint() < 0 || test.getPoint() > 100) return false;

        Connection con = getConnection();

        String chk = "SELECT COUNT(*) FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? "
                   + "AND SCHOOL_CD = ? AND NO = ?";
        PreparedStatement cst = con.prepareStatement(chk);
        cst.setString(1, test.getStudent().getNo());
        cst.setString(2, test.getSubject().getCd());
        cst.setString(3, test.getStudent().getSchoolCd());
        cst.setInt   (4, test.getNo());

        ResultSet rs = cst.executeQuery();
        rs.next();
        boolean exists = rs.getInt(1) > 0;
        rs.close(); cst.close();

        PreparedStatement st;
        if (exists) {
            /* ---------- UPDATE ---------- */
            String upd = "UPDATE TEST SET POINT = ?, CLASS_NUM = ? "
                       + "WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";
            st = con.prepareStatement(upd);
            st.setInt   (1, test.getPoint());
            st.setString(2, test.getClassNum());
            st.setString(3, test.getStudent().getNo());
            st.setString(4, test.getSubject().getCd());
            st.setString(5, test.getStudent().getSchoolCd());
            st.setInt   (6, test.getNo());
        } else {
            /* ---------- INSERT ---------- */
            String ins = "INSERT INTO TEST "
                       + "(STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";
            st = con.prepareStatement(ins);
            st.setString(1, test.getStudent().getNo());
            st.setString(2, test.getSubject().getCd());
            st.setString(3, test.getStudent().getSchoolCd());
            st.setInt   (4, test.getNo());
            st.setInt   (5, test.getPoint());
            st.setString(6, test.getClassNum());
        }
        int result = st.executeUpdate();
        st.close(); con.close();
        return result > 0;
    }

    /* ========== 条件検索 ========== */
    public List<Test> filter(int entYear, String classNum,
                             String subjectCd, int no, School school) throws Exception {

        List<Test> list = new ArrayList<>();
        Connection con = getConnection();

        String sql = BASE_SQL + " WHERE SCHOOL_CD = ? AND SUBJECT_CD = ? "
                   + "AND NO = ? AND CLASS_NUM = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, school.getSchoolCd());
        st.setString(2, subjectCd);
        st.setInt   (3, no);
        st.setString(4, classNum);

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Test    t = new Test();
            Student s = new Student();
            Subject sub = new Subject();

            s.setNo      (rs.getString("STUDENT_NO"));
            s.setEntYear (entYear);
            s.setClassNum(rs.getString("CLASS_NUM"));
            s.setSchool  (school);

            sub.setCd(rs.getString("SUBJECT_CD"));

            t.setStudent (s);
            t.setSubject (sub);
            t.setEntYear (entYear);
            t.setNo      (rs.getInt("NO"));
            t.setPoint   (rs.getInt("POINT"));
            t.setClassNum(rs.getString("CLASS_NUM"));

            list.add(t);
        }
        rs.close(); st.close(); con.close();
        return list;
    }
}
