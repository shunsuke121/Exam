package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class MenuAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	// デバッグ用ログ（ログイン成功後に呼ばれることを確認）
        System.out.println("★★ MenuAction 実行された！");
        // メニュー画面へフォワード
        return "scoremanager/main/menu.jsp";
    }
}
