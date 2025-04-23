package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

/**
 * クラス番号テーブル DAO<br>
 * ER図／テーブル定義書「CLASS_NUM」に準拠。
 */
public class ClassNumDao extends Dao {

    /* ======================== 取得系 ======================== */

    /**
     * 1件取得（クラス変更画面などで使用予定）
     * @param classNum  クラス番号
     * @param school    学校オブジェクト
     * @return  該当データがあれば ClassNum、なければ null
     */
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

    /** 学校コード（文字列）指定のラッパー */
    public List<ClassNum> filter(String schoolCd) throws Exception {
        School s = new School();
        s.setSchoolCd(schoolCd);
        return filter(s);
    }

    /**
     * 学校に紐づくクラス一覧を取得
     * @param school School
     * @return List&lt;ClassNum&gt;
     */
    public List<ClassNum> filter(School school) throws Exception {

        List<ClassNum> list = new ArrayList<>();
        String sql = "SELECT * FROM CLASS_NUM WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, school.getSchoolCd());      // ← getSchoolCd() を正としてコメント修正
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

    /* ======================== 登録／更新系（未使用） ======================== */
    public boolean save(ClassNum cn) throws Exception { return false; }
    public boolean save(ClassNum cn, String newClassNum) throws Exception { return false; }
}
