package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;

public class TestRegistAction extends Action {

    // 画面項目名（設計書 GRMU001 一覧）
    private static final String P_ENT_YEAR = "f1";
    private static final String P_CLASS    = "f2";
    private static final String P_SUBJECT  = "f3";
    private static final String P_COUNT    = "f4";
    private static final String P_ATTEND   = "f5";

    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse res) throws Exception {

        HttpSession ses = req.getSession();
        Teacher teacher = (Teacher) ses.getAttribute("user");
        School  school  = teacher.getSchool();

        /* ---------- プルダウン共通値 ---------- */
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> years = new ArrayList<>();
        for (int y = thisYear - 10; y <= thisYear + 1; y++) years.add(y);
        req.setAttribute("years", years);

        req.setAttribute("classNums", new ClassNumDao().filter(school));
        req.setAttribute("subjects",  new SubjectDao().filter(school));

        /* ---------- 検索条件 ---------- */
        String f1 = req.getParameter(P_ENT_YEAR);
        String f2 = req.getParameter(P_CLASS);
        String f3 = req.getParameter(P_SUBJECT);
        String f4 = req.getParameter(P_COUNT);
        String f5 = req.getParameter(P_ATTEND);

        // 再描画用
        req.setAttribute(P_ENT_YEAR, f1);
        req.setAttribute(P_CLASS,    f2);
        req.setAttribute(P_SUBJECT,  f3);
        req.setAttribute(P_COUNT,    f4);
        req.setAttribute(P_ATTEND,   f5);

        boolean hasYear  = f1 != null && !f1.isEmpty();
        boolean hasClass = f2 != null && !f2.isEmpty();
        boolean hasSubj  = f3 != null && !f3.isEmpty();
        boolean hasCnt   = f4 != null && !f4.isEmpty();

        if (hasYear && hasClass && hasSubj && hasCnt) {

            int     entYear    = Integer.parseInt(f1);
            int     count      = Integer.parseInt(f4);
            boolean attendOnly = (f5 != null);   // チェックボックス

            /* --- 学生一覧取得 --- */
            List<Student> students = attendOnly
                    ? new StudentDao().filter(school, entYear, f2, true)
                    : new StudentDao().filter(school, entYear, f2);

            /* --- Test ビーンを JSP へ --- */
            List<Test> testList = new ArrayList<>();
            for (Student s : students) {
                Test t = new Test();
                t.setStudent(s);
                t.setEntYear(entYear);
                t.setNo(count);
                t.setClassNum(f2);
                testList.add(t);
            }
            req.setAttribute("testList", testList);
        }
        return "scoremanager/main/test_regist.jsp";
    }
}
