package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {

    // データベース接続メソッド
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // JDBCドライバのロード
        Class.forName("org.h2.Driver");

        // データベースへ接続
        return DriverManager.getConnection(
            "jdbc:h2:tcp://localhost/~/project", // DB URL
            "sa",                                // ユーザー名
            ""                                   // パスワード（未設定）
        );
    }
}
