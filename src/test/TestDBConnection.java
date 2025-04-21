package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import dao.Dao;

public class TestDBConnection extends Dao {

    public static void main(String[] args) {
        try {
            TestDBConnection test = new TestDBConnection();
            Connection conn = test.getConnection();
            System.out.println("✅ DB接続成功！");

            // 教員テーブルから1件表示
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM TEACHER");

            while (rs.next()) {
                System.out.println("ID: " + rs.getString("ID"));
                System.out.println("名前: " + rs.getString("NAME"));
                System.out.println("----");
            }

            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ DB接続に失敗しました");
            e.printStackTrace();
        }
    }
}
