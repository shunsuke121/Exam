package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        String cd = request.getParameter("cd");

        // 所属学校に限定して1件取得（filterから抽出）
        SubjectDao dao = new SubjectDao();
        Subject target = dao.filter(teacher.getSchool()).stream()
                            .filter(s -> s.getCd().equals(cd))
                            .findFirst()
                            .orElse(null);

        if (target == null) {
            request.setAttribute("error", "該当する科目が見つかりませんでした。");
            return "scoremanager/main/subject_list.jsp";
        }

        request.setAttribute("subject", target);
        return "scoremanager/main/subject_delete.jsp";
    }
}
