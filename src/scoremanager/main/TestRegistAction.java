package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 入学年度（プルダウン用）
        List<Integer> years = new ArrayList<>();
        int thisYear = LocalDate.now().getYear();
        for (int i = thisYear - 10; i <= thisYear + 1; i++) {
            years.add(i);
        }

        // クラス一覧
        ClassNumDao cDao = new ClassNumDao();
        List<ClassNum> classList = cDao.filter(school);

        // 科目一覧（セッション保持）
        SubjectDao subDao = new SubjectDao();
        List<Subject> subjectList = subDao.filter(school);
        session.setAttribute("subjectList", subjectList);

        // プルダウン用属性
        request.setAttribute("yearList", years);
        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);

        // 検索条件
        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");
        String numStr = request.getParameter("num");

        if (entYearStr == null || classNum == null || subjectCd == null || numStr == null) {
            return "scoremanager/main/test_regist.jsp";
        }

        int entYear = Integer.parseInt(entYearStr);
        int num = Integer.parseInt(numStr);

        Subject subject = subjectList.stream()
            .filter(s -> s.getCd().equals(subjectCd))
            .findFirst()
            .orElse(null);

        if (subject == null) {
            throw new Exception("指定された科目が見つかりません");
        }

        // 成績取得
        TestDao tDao = new TestDao();
        List<Test> testList = tDao.filter(entYear, classNum, subject, num, school);

        // 検索条件 + 学生一覧をセット（JSPで使う変数に合わせる）
        request.setAttribute("entYear", entYear);
        request.setAttribute("classNum", classNum);
        request.setAttribute("subject", subject);
        request.setAttribute("num", num);
        request.setAttribute("testList", testList);

        return "scoremanager/main/test_regist.jsp";
    }
}
