package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

/**
 * 科目マスタ DAO<br>
 * テーブル定義書「SUBJECT」に完全準拠。<br>
 * <ul>
 *   <li>{@link #get(String, School)} ― save() 内部で使用</li>
 *   <li>{@link #get(School, String)} ― 成績登録／参照系 Action で使用</li>
 * </ul>
 */
public class SubjectDao extends Dao {

    /* ========== 一覧取得 ========== */
    public List<Subject> filter(School school) throws Exception {

        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD";

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, school.getSchoolCd());
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject sj = new Subject();
            sj.setCd(rs.getString("CD"));
            sj.setName(rs.getString("NAME"));
            sj.setSchool(school);
            list.add(sj);
        }
        rs.close(); st.close(); con.close();
        return list;
    }

    /* ------------------------------------------------------------
       get(cd, school)  : save() で存在チェックに使用
       get(school, cd)  : 画面系 Action で取得に使用
       シグネチャが逆なので両方残す
       ------------------------------------------------------------ */

    /** cd → school の順 */
    public Subject get(String cd, School school) throws Exception {

        String sql = "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, cd);
        st.setString(2, school.getSchoolCd());

        ResultSet rs = st.executeQuery();
        Subject sj = null;
        if (rs.next()) {
            sj = new Subject();
            sj.setCd(rs.getString("CD"));
            sj.setName(rs.getString("NAME"));
            sj.setSchool(school);
        }
        rs.close(); st.close(); con.close();
        return sj;
    }

    /** school → cd の順（成績登録・参照 Action 用） */
    public Subject get(School school, String cd) throws Exception {

        String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? AND CD = ?";
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, school.getSchoolCd());
        st.setString(2, cd);

        ResultSet rs = st.executeQuery();
        Subject sj = null;
        if (rs.next()) {
            sj = new Subject();
            sj.setCd(rs.getString("CD"));
            sj.setName(rs.getString("NAME"));
            sj.setSchool(school);
        }
        rs.close(); st.close(); con.close();
        return sj;
    }

    /* ========== INSERT / UPDATE ========== */
    public boolean save(Subject subject) throws Exception {

        Connection con = getConnection();
        boolean result;

        // 既存チェック
        Subject existing = get(subject.getCd(), subject.getSchool());

        if (existing == null) {
            String ins = "INSERT INTO SUBJECT (CD, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
            PreparedStatement st = con.prepareStatement(ins);
            st.setString(1, subject.getCd());
            st.setString(2, subject.getName());
            st.setString(3, subject.getSchoolCd());
            result = st.executeUpdate() > 0;
            st.close();
        } else {
            String upd = "UPDATE SUBJECT SET NAME = ? WHERE CD = ? AND SCHOOL_CD = ?";
            PreparedStatement st = con.prepareStatement(upd);
            st.setString(1, subject.getName());
            st.setString(2, subject.getCd());
            st.setString(3, subject.getSchoolCd());
            result = st.executeUpdate() > 0;
            st.close();
        }
        con.close();
        return result;
    }
}
