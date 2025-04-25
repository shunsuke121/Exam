package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class LogoutAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションを無効化
        request.getSession().invalidate();

        // ログアウト画面へフォワード
        return "scoremanager/main/logout.jsp";
    }
}
