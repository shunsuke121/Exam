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
}
