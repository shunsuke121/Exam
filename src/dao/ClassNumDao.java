package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

    // 1件取得
    public ClassNum get(String classNum, School school) throws Exception {
        String sql = "SELECT * FROM CLASS_NUM WHERE SCHOOL_CD = ? AND CLASS_NUM = ?";
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, school.getSchoolCd());
        st.setString(2, classNum);

        ResultSet rs = st.executeQuery();
        ClassNum cn = null;
        if (rs.next()) {
            cn = new ClassNum();
            cn.setSchoolCd(rs.getString("SCHOOL_CD"));
            cn.setClassNum(rs.getString("CLASS_NUM"));
        }
        rs.close(); st.close(); con.close();
        return cn;
    }

    // 一覧取得（学校コード指定）
    public List<ClassNum> filter(String schoolCd) throws Exception {
        School s = new School();
        s.setSchoolCd(schoolCd);
        return filter(s);
    }

    // 一覧取得（Schoolオブジェクト指定）
    public List<ClassNum> filter(School school) throws Exception {
        List<ClassNum> list = new ArrayList<>();
        String sql = "SELECT * FROM CLASS_NUM WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, school.getSchoolCd());
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            ClassNum cn = new ClassNum();
            cn.setSchoolCd(rs.getString("SCHOOL_CD"));
            cn.setClassNum(rs.getString("CLASS_NUM"));
            list.add(cn);
        }
        rs.close(); st.close(); con.close();
        return list;
    }

    // 新規登録
    public boolean save(ClassNum cn) throws Exception {
        String sql = "INSERT INTO CLASS_NUM (SCHOOL_CD, CLASS_NUM) VALUES (?, ?)";
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, cn.getSchoolCd());
        st.setString(2, cn.getClassNum());
        int result = st.executeUpdate();
        st.close(); con.close();
        return result == 1;
    }

    // クラス番号の更新＋紐づく学生のCLASS_NUMも更新
    public boolean save(ClassNum cn, String newClassNum) throws Exception {
        Connection con = getConnection();
        try {
            con.setAutoCommit(false);

            // STUDENT テーブルの CLASS_NUM を更新
            String sql1 = "UPDATE STUDENT SET CLASS_NUM = ? WHERE SCHOOL_CD = ? AND CLASS_NUM = ?";
            PreparedStatement st1 = con.prepareStatement(sql1);
            st1.setString(1, newClassNum);
            st1.setString(2, cn.getSchoolCd());
            st1.setString(3, cn.getClassNum());
            st1.executeUpdate();

            // CLASS_NUM テーブルの CLASS_NUM を更新
            String sql2 = "UPDATE CLASS_NUM SET CLASS_NUM = ? WHERE SCHOOL_CD = ? AND CLASS_NUM = ?";
            PreparedStatement st2 = con.prepareStatement(sql2);
            st2.setString(1, newClassNum);
            st2.setString(2, cn.getSchoolCd());
            st2.setString(3, cn.getClassNum());
            int result = st2.executeUpdate();

            con.commit();
            st1.close();
            st2.close();
            con.close();
            return result == 1;
        } catch (Exception e) {
            con.rollback();
            con.close();
            throw e;
        }
    }
}