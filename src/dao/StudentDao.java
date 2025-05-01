package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

/**
 * 学生情報を操作するDAOクラス
 * 対象テーブル: STUDENT（得点管理システム）
 * カラム構成はテーブル定義書に準拠
 */
public class StudentDao extends Dao {

    /**
     * 学生番号を指定して学生情報を1件取得する
     */
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

    /**
     * 学生情報を登録または更新する（既存の学生番号が存在するかどうかで分岐）
     */
    public boolean save(Student student) throws Exception {
        boolean result = false;
        Student existing = get(student.getNo());

        try (Connection con = getConnection()) {
            if (existing == null) {
                // INSERT処理
                String sql = "INSERT INTO STUDENT (NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD) VALUES (?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, student.getNo());
                ps.setString(2, student.getName());
                ps.setInt(3, student.getEntYear());
                ps.setString(4, student.getClassNum());
                ps.setBoolean(5, student.isAttend());
                ps.setString(6, student.getSchoolCd());
                result = ps.executeUpdate() > 0;
            } else {
                // UPDATE処理
                String sql = "UPDATE STUDENT SET NAME=?, ENT_YEAR=?, CLASS_NUM=?, IS_ATTEND=?, SCHOOL_CD=? WHERE NO=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, student.getName());
                ps.setInt(2, student.getEntYear());
                ps.setString(3, student.getClassNum());
                ps.setBoolean(4, student.isAttend());
                ps.setString(5, student.getSchoolCd());
                ps.setString(6, student.getNo());
                result = ps.executeUpdate() > 0;
            }
        }
        return result;
    }

    /**
     * 学生情報を1件削除する（学生番号指定）
     */
    public boolean delete(String no) throws Exception {
        boolean result = false;
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM STUDENT WHERE NO = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, no);
            result = ps.executeUpdate() > 0;
        }
        return result;
    }

    // 以下、様々な条件による学生一覧の取得メソッド群（filter）

    /**
     * 学校＋在学中フラグで絞り込み
     */
    public List<Student> filter(School school, boolean isAttend) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND IS_ATTEND=? ORDER BY NO";
        return executeFilter(sql, school.getSchoolCd(), isAttend);
    }

    /**
     * 学校＋入学年度＋クラス番号＋在学中フラグで絞り込み
     */
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND ENT_YEAR=? AND CLASS_NUM=? AND IS_ATTEND=? ORDER BY NO";
        return executeFilter(sql, school.getSchoolCd(), entYear, classNum, isAttend);
    }

    /**
     * 学校＋入学年度＋在学中フラグで絞り込み
     */
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND ENT_YEAR=? AND IS_ATTEND=? ORDER BY NO";
        return executeFilter(sql, school.getSchoolCd(), entYear, isAttend);
    }

    /**
     * 学校＋入学年度＋クラス番号で絞り込み（在学中条件なし）
     */
    public List<Student> filter(School school, int entYear, String classNum) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND ENT_YEAR=? AND CLASS_NUM=? ORDER BY NO";
        return executeFilter(sql, school.getSchoolCd(), entYear, classNum);
    }

    /**
     * 学校＋入学年度で絞り込み
     */
    public List<Student> filter(School school, int entYear) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND ENT_YEAR=? ORDER BY NO";
        return executeFilter(sql, school.getSchoolCd(), entYear);
    }

    /**
     * 学校単位の全件取得（在学中条件なし）
     */
    public List<Student> filter(School school) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? ORDER BY NO";
        return executeFilter(sql, school.getSchoolCd());
    }

    /**
     * 全件取得（学校オブジェクト）→ filter(school)を呼び出し
     */
    public List<Student> filterAll(School school) throws Exception {
        return filter(school);
    }

    /**
     * 全件取得（学校コード文字列）
     */
    public List<Student> filterAll(String schoolCd) throws Exception {
        School school = new School();
        school.setSchoolCd(schoolCd);
        return filter(school);
    }

    /**
     * 学校＋在学中=FALSE で退学済みを抽出
     */
    public List<Student> filterExcludeAttend(School school) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD=? AND IS_ATTEND=FALSE ORDER BY NO";
        return executeFilter(sql, school.getSchoolCd());
    }

    /**
     * filterExcludeAttend の学校コード文字列版
     */
    public List<Student> filterExcludeAttend(String schoolCd) throws Exception {
        School school = new School();
        school.setSchoolCd(schoolCd);
        return filterExcludeAttend(school);
    }

    /**
     * 指定条件（学校・入学年度・クラス）＋在学中=TRUE で取得
     */
    public List<Student> filter(String schoolCd, int entYear, String classNum) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD = ? AND ENT_YEAR = ? AND CLASS_NUM = ? AND IS_ATTEND = TRUE ORDER BY NO";
        return executeFilter(sql, schoolCd, entYear, classNum);
    }

    /**
     * 学校＋学生番号or氏名にLIKE検索
     */
    public List<Student> filterByKey(School school, String key) throws Exception {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD = ? AND (NO LIKE ? OR NAME LIKE ?) ORDER BY NO";

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
                stu.setSchool(school);  // ここはsetSchool（オブジェクト）を使用
                list.add(stu);
            }
        }
        return list;
    }

    /**
     * フィルターメソッド共通実行部（オーバーロード）
     */
    private List<Student> executeFilter(String sql, Object... params) throws Exception {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

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
}
