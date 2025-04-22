package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        // セッションから教員情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストパラメータを取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // 入力チェック（必須）
        if (name == null || name.isEmpty()) {
            request.setAttribute("error", "科目名を入力してください。");
            request.setAttribute("cd", cd);
            return "scoremanager/main/subject_update.jsp";
        }

        // 更新用Subjectインスタンスを作成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        // 更新処理
        SubjectDao dao = new SubjectDao();
        dao.save(subject); // insert or update

        // 完了画面へ遷移
        return "scoremanager/main/subject_update_done.jsp";
    }
}
