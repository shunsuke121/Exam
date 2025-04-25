package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class LoginExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("★ LoginExecuteAction 開始");

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        System.out.println("★ 入力ID：" + id);
        System.out.println("★ 入力PW：" + password);

        // 入力チェック
        if (id == null || id.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "このフィールドを入力してください");
            return "scoremanager/login.jsp";
        }

        TeacherDao dao = new TeacherDao();
        Teacher teacher = null;

        try {
            teacher = dao.login(id, password);
            System.out.println("★ ログイン結果：" + (teacher != null ? "成功（ID: " + teacher.getId() + "）" : "失敗"));
        } catch (Exception e) {
            System.out.println("★ データベース処理中にエラーが発生しました");
            e.printStackTrace();
            throw e;
        }

        if (teacher == null) {
            request.setAttribute("error", "ログインに失敗しました。IDまたはパスワードが正しくありません。");
            request.setAttribute("id", id);
            return "scoremanager/login.jsp";
        }

        // セッションにユーザー情報を格納
        HttpSession session = request.getSession();
        session.setAttribute("user", teacher);

        System.out.println("★ ログイン成功 → メニュー画面へリダイレクト");

        // FrontController側でこのプレフィックスを見てsendRedirect()するようにしてください
        return "redirect:scoremanager/Menu.action";
    }
}
