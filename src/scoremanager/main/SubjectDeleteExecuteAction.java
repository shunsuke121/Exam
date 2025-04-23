package scoremanager.main;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Teacher;
import dao.Dao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        String cd = request.getParameter("cd");

        try (Connection conn = new Dao().getConnection()) {
            String sql = "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cd);
                stmt.setString(2, teacher.getSchoolCd());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "削除処理でエラーが発生しました。");
            return "scoremanager/main/subject_list.jsp";
        }

        return "scoremanager/main/subject_delete_done.jsp";
    }
}
