package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

    public Student get(String no) throws Exception {
        Student st = null;
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM STUDENT WHERE NO=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, no);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    st = new Student();
                    st.setNo(rs.getString("NO"));
                    st.setName(rs.getString("NAME"));
                    st.setEntYear(rs.getInt("ENT_YEAR"));
                    st.setClassNum(rs.getString("CLASS_NUM"));
                    st.setAttend(rs.getBoolean("IS_ATTEND"));
                    st.setSchoolCd(rs.getString("SCHOOL_CD"));
                }
            }
        }
        return st;
    }

    public boolean save(Student student) throws Exception {
        boolean result = false;
        Student existing = get(student.getNo());

        try (Connection con = getConnection()) {
            if (existing == null) {
                String sql = "INSERT INTO STUDENT (NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD) VALUES (?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, student.getNo());
                ps.setString(2, student.getName());
                ps.setInt(3, student.getEntYear());
                ps.setString(4, student.getClassNum());
                ps.setBoolean(5, student.isAttend());
                ps.setString(6, student.getSchoolCd());
                int r = ps.executeUpdate();
                result = (r > 0);
            } else {
                String sql = "UPDATE STUDENT SET NAME=?, ENT_YEAR=?, CLASS_NUM=?, IS_ATTEND=?, SCHOOL_CD=? WHERE NO=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, student.getName());
                ps.setInt(2, student.getEntYear());
                ps.setString(3, student.getClassNum());
                ps.setBoolean(4, student.isAttend());
                ps.setString(5, student.getSchoolCd());
                ps.setString(6, student.getNo());
                int r = ps.executeUpdate();
                result = (r > 0);
            }
        }
        return result;
    }

    public boolean delete(String no) throws Exception {
        boolean result = false;
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM STUDENT WHERE NO = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, no);
            int r = ps.executeUpdate();
            result = (r > 0);
        }
        return result;
    }

    public List<Student> filter(School school, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND IS_ATTEND=? ORDER BY NO";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, school.getSchoolCd());
            ps.setBoolean(2, isAttend);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student st = new Student();
                    st.setNo(rs.getString("NO"));
                    st.setName(rs.getString("NAME"));
                    st.setEntYear(rs.getInt("ENT_YEAR"));
                    st.setClassNum(rs.getString("CLASS_NUM"));
                    st.setAttend(rs.getBoolean("IS_ATTEND"));
                    st.setSchoolCd(rs.getString("SCHOOL_CD"));
                    list.add(st);
                }
            }
        }
        return list;
    }

    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND ENT_YEAR=? AND CLASS_NUM=? AND IS_ATTEND=? ORDER BY NO";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, school.getSchoolCd());
            ps.setInt(2, entYear);
            ps.setString(3, classNum);
            ps.setBoolean(4, isAttend);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student st = new Student();
                    st.setNo(rs.getString("NO"));
                    st.setName(rs.getString("NAME"));
                    st.setEntYear(rs.getInt("ENT_YEAR"));
                    st.setClassNum(rs.getString("CLASS_NUM"));
                    st.setAttend(rs.getBoolean("IS_ATTEND"));
                    st.setSchoolCd(rs.getString("SCHOOL_CD"));
                    list.add(st);
                }
            }
        }
        return list;
    }

    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND ENT_YEAR=? AND IS_ATTEND=? ORDER BY NO";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, school.getSchoolCd());
            ps.setInt(2, entYear);
            ps.setBoolean(3, isAttend);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student st = new Student();
                    st.setNo(rs.getString("NO"));
                    st.setName(rs.getString("NAME"));
                    st.setEntYear(rs.getInt("ENT_YEAR"));
                    st.setClassNum(rs.getString("CLASS_NUM"));
                    st.setAttend(rs.getBoolean("IS_ATTEND"));
                    st.setSchoolCd(rs.getString("SCHOOL_CD"));
                    list.add(st);
                }
            }
        }
        return list;
    }

    public List<Student> filter(School school, int entYear, String classNum) throws Exception {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND ENT_YEAR=? AND CLASS_NUM=? ORDER BY NO";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, school.getSchoolCd());
            ps.setInt(2, entYear);
            ps.setString(3, classNum);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student st = new Student();
                    st.setNo(rs.getString("NO"));
                    st.setName(rs.getString("NAME"));
                    st.setEntYear(rs.getInt("ENT_YEAR"));
                    st.setClassNum(rs.getString("CLASS_NUM"));
                    st.setAttend(rs.getBoolean("IS_ATTEND"));
                    st.setSchoolCd(rs.getString("SCHOOL_CD"));
                    list.add(st);
                }
            }
        }
        return list;
    }

    public List<Student> filter(School school, int entYear) throws Exception {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND ENT_YEAR=? ORDER BY NO";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, school.getSchoolCd());
            ps.setInt(2, entYear);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student st = new Student();
                    st.setNo(rs.getString("NO"));
                    st.setName(rs.getString("NAME"));
                    st.setEntYear(rs.getInt("ENT_YEAR"));
                    st.setClassNum(rs.getString("CLASS_NUM"));
                    st.setAttend(rs.getBoolean("IS_ATTEND"));
                    st.setSchoolCd(rs.getString("SCHOOL_CD"));
                    list.add(st);
                }
            }
        }
        return list;
    }

    public List<Student> filter(School school) throws Exception {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? ORDER BY NO";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, school.getSchoolCd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student st = new Student();
                    st.setNo(rs.getString("NO"));
                    st.setName(rs.getString("NAME"));
                    st.setEntYear(rs.getInt("ENT_YEAR"));
                    st.setClassNum(rs.getString("CLASS_NUM"));
                    st.setAttend(rs.getBoolean("IS_ATTEND"));
                    st.setSchoolCd(rs.getString("SCHOOL_CD"));
                    list.add(st);
                }
            }
        }
        return list;
    }

    public List<Student> filterAll(School school) throws Exception {
        return filter(school);
    }

    public List<Student> filterAll(String schoolCd) throws Exception {
        School school = new School();
        school.setSchoolCd(schoolCd);
        return filter(school);
    }

    public List<Student> filterExcludeAttend(School school) throws Exception {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND IS_ATTEND=FALSE ORDER BY NO";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, school.getSchoolCd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student st = new Student();
                    st.setNo(rs.getString("NO"));
                    st.setName(rs.getString("NAME"));
                    st.setEntYear(rs.getInt("ENT_YEAR"));
                    st.setClassNum(rs.getString("CLASS_NUM"));
                    st.setAttend(rs.getBoolean("IS_ATTEND"));
                    st.setSchoolCd(rs.getString("SCHOOL_CD"));
                    list.add(st);
                }
            }
        }
        return list;
    }

    public List<Student> filterExcludeAttend(String schoolCd) throws Exception {
        School school = new School();
        school.setSchoolCd(schoolCd);
        return filterExcludeAttend(school);
    }

    public List<Student> filter(String schoolCd, int entYear, String classNum) throws Exception {
        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD = ? AND ENT_YEAR = ? AND CLASS_NUM = ? AND IS_ATTEND = TRUE ORDER BY NO";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, schoolCd);
            stmt.setInt(2, entYear);
            stmt.setString(3, classNum);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setNo(rs.getString("NO"));
                student.setName(rs.getString("NAME"));
                student.setEntYear(rs.getInt("ENT_YEAR"));
                student.setClassNum(rs.getString("CLASS_NUM"));
                student.setAttend(rs.getBoolean("IS_ATTEND"));
                student.setSchoolCd(rs.getString("SCHOOL_CD"));
                list.add(student);
            }
        }
        return list;
    }
    public List<Student> filterByKey(School school, String key) throws Exception {

        List<Student> list = new ArrayList<>();

        String sql =
            "SELECT * FROM STUDENT " +
            " WHERE SCHOOL_CD = ?" +
            "   AND (NO LIKE ? OR NAME LIKE ?) " +
            " ORDER BY NO";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, school.getSchoolCd());
            ps.setString(2, "%" + key + "%");
            ps.setString(3, "%" + key + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student stu = new Student();
                stu.setNo(rs.getString("NO"));
                stu.setName(rs.getString("NAME"));
                stu.setEntYear(rs.getInt("ENT_YEAR"));
                stu.setClassNum(rs.getString("CLASS_NUM"));
                stu.setSchool(school);
                list.add(stu);
            }
        }
        return list;
    }

}