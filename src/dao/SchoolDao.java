package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;

public class SchoolDao extends Dao {

    /**
     * 学校コードを指定して学校インスタンスを1件取得する
     *
     * @param cd 学校コード
     * @return 学校クラスのインスタンス 存在しない場合はnull
     * @throws Exception
     */
    public School get(String cd) throws Exception {
        // 学校インスタンスを初期化
        School school = new School();
        // データベースへのコネクションを確立
        Connection connection = getConnection();
        // プリペアドステートメント
        PreparedStatement statement = null;

        try {
            // プリペアドステートメントにSQL文をセット
            statement = connection.prepareStatement("select * from school where cd = ?");
            // プリペアドステートメントに学校コードをバインド
            statement.setString(1, cd);
            // プリペアドステートメントを実行
            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                // リザルトセットが存在する場合 → 学校インスタンスに結果をセット
                school.setSchoolCd(rSet.getString("cd"));
                school.setName(rSet.getString("name"));
            } else {
                // 存在しない場合 → nullをセット
                school = null;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            // プリペアドステートメントを閉じる
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }

            // コネクションを閉じる
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return school;
    }
}

