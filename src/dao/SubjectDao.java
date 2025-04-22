package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    // 学校コードをもとに科目一覧を取得する
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();

        String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD";

        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, school.getSchoolCd());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Subject subject = new Subject();
            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));
            subject.setSchool(school); // Schoolオブジェクトそのままセット
            list.add(subject);
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;

        String sql = "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";

        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cd);
        stmt.setString(2, school.getSchoolCd());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            subject = new Subject();
            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));
            subject.setSchool(school); // 取得元schoolをそのままセット
        }

        rs.close();
        stmt.close();
        conn.close();

        return subject;
    }
    public boolean save(Subject subject) throws Exception {
        boolean result = false;

        // 既存データの確認（INSERTかUPDATEかの判定用）
        Subject existing = get(subject.getCd(), subject.getSchool());

        Connection conn = getConnection();

        if (existing == null) {
            // INSERT処理
            String sql = "INSERT INTO SUBJECT (CD, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, subject.getCd());
            stmt.setString(2, subject.getName());
            stmt.setString(3, subject.getSchool().getSchoolCd());
            int r = stmt.executeUpdate();
            result = (r > 0);
            stmt.close();
        } else {
            // UPDATE処理
            String sql = "UPDATE SUBJECT SET NAME = ? WHERE CD = ? AND SCHOOL_CD = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, subject.getName());
            stmt.setString(2, subject.getCd());
            stmt.setString(3, subject.getSchool().getSchoolCd());
            int r = stmt.executeUpdate();
            result = (r > 0);
            stmt.close();
        }

        conn.close();
        return result;
    }

}

