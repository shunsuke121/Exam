package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Teacher;

public class TeacherDao extends Dao {

    /**
     * IDのみで教員情報を取得（主に管理用）
     */
    public Teacher get(String id) throws Exception {
        Teacher teacher = null;

        String sql = "SELECT * FROM TEACHER WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                teacher = new Teacher();
                teacher.setId(rs.getString("ID"));
                teacher.setPassword(rs.getString("PASSWORD"));
                teacher.setName(rs.getString("NAME"));

                // 学校情報のセット
                School school = new School();
                school.setSchoolCd(rs.getString("SCHOOL_CD"));
                teacher.setSchool(school);
            }
        }

        return teacher;
    }

    /**
     * ログイン用：IDとパスワードで照合して教員を取得
     */
    public Teacher login(String id, String password) throws Exception {
        Teacher teacher = null;

        String sql = "SELECT * FROM TEACHER WHERE ID = ? AND PASSWORD = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                teacher = new Teacher();
                teacher.setId(rs.getString("ID"));
                teacher.setPassword(rs.getString("PASSWORD"));
                teacher.setName(rs.getString("NAME"));

                // 学校情報のセット
                School school = new School();
                school.setSchoolCd(rs.getString("SCHOOL_CD"));
                teacher.setSchool(school);
            }
        }

        return teacher;
    }
}
