package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションから教員情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストから科目コードを取得
        String cd = request.getParameter("cd");

        // 該当する科目情報を取得
        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(cd, teacher.getSchool());

        // リクエストスコープに設定
        request.setAttribute("cd", subject.getCd());
        request.setAttribute("name", subject.getName());

        // JSPへフォワード
        return "scoremanager/main/subject_update.jsp";
    }
}
