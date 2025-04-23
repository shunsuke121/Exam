package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");

        HttpSession ses = req.getSession();
        Teacher teacher = (Teacher) ses.getAttribute("user");
        if (teacher == null) return "/login.jsp";

        School school = teacher.getSchool();

        /* ---------- パラメータ ---------- */
        int    entYear = Integer.parseInt(req.getParameter("f1"));
        String classNo = req.getParameter("f2");
        String subjCd  = req.getParameter("f3");
        int    count   = Integer.parseInt(req.getParameter("f4"));

        /* ---------- 必要データ ---------- */
        List<Student> students = new StudentDao()
                .filter(school, entYear, classNo, true);
        Subject subject = new SubjectDao().get(school, subjCd);

        /* ---------- 登録処理 ---------- */
        TestDao tDao = new TestDao();
        for (Student s : students) {
            String pStr = req.getParameter("point_" + s.getNo());
            if (pStr == null || pStr.isEmpty()) continue;

            int point;
            try {
                point = Integer.parseInt(pStr);
                if (point < 0 || point > 100) continue;
            } catch (NumberFormatException e) {
                continue;
            }

            Test t = new Test();
            t.setStudent(s);
            t.setSubject(subject);
            t.setEntYear(entYear);
            t.setNo(count);
            t.setPoint(point);
            t.setClassNum(classNo);

            tDao.save(t);
        }
        return "scoremanager/main/test_regist_done.jsp";
    }
}
