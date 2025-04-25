package scoremanager.main;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Teacher;
import dao.Dao;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        // 入力パラメータ取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // 入力保持
        request.setAttribute("cd", cd);
        request.setAttribute("name", name);

        // 入力チェック
        if (cd == null || cd.isEmpty() || name == null || name.isEmpty()) {
            request.setAttribute("error", "科目コードと科目名は必須です。");
            return "scoremanager/main/subject_create.jsp";
        }

        // 科目コードの重複チェック（同じ学校内で）
        SubjectDao subjectDao = new SubjectDao();
        boolean isDuplicate = subjectDao.filter(teacher.getSchool()).stream()
                .anyMatch(s -> s.getCd().equals(cd));

        if (isDuplicate) {
            request.setAttribute("error", "この科目コードは既に使用されています。");
            return "scoremanager/main/subject_create.jsp";
        }

        // 登録処理
        try (Connection conn = new Dao().getConnection()) {
            String sql = "INSERT INTO SUBJECT(CD, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cd);
                stmt.setString(2, name);
                stmt.setString(3, teacher.getSchoolCd());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "登録処理でエラーが発生しました。");
            return "scoremanager/main/subject_create.jsp";
        }

        return "scoremanager/main/subject_create_done.jsp";
    }
}
