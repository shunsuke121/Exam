package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String cd = request.getParameter("cd");

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.filter(teacher.getSchool()).stream()
                .filter(s -> s.getCd().equals(cd))
                .findFirst()
                .orElse(null);

        if (subject == null) {
            request.setAttribute("error", "該当する科目が見つかりませんでした。");
            return "scoremanager/main/subject_list.jsp";
        }

        request.setAttribute("subject", subject); // ← ここがポイント
        return "scoremanager/main/subject_update.jsp";
    }
}
