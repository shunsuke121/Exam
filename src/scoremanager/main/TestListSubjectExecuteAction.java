// 修正済 TestListSubjectExecuteAction.java - 回数は非必須＆デフォルト1に設定
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
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String entYear = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");
        String numStr = request.getParameter("num"); // 画面上で非表示でも取得OK

        request.setAttribute("entYear", entYear);
        request.setAttribute("classNum", classNum);
        request.setAttribute("subjectCd", subjectCd);
        request.setAttribute("num", numStr);

        // ▼ プルダウン保持用
        List<Integer> yearList = new ArrayList<>();
        int thisYear = LocalDate.now().getYear();
        for (int i = thisYear - 10; i <= thisYear + 1; i++) {
            yearList.add(i);
        }
        request.setAttribute("yearList", yearList);

        ClassNumDao classDao = new ClassNumDao();
        List<ClassNum> classList = classDao.filter(school);
        request.setAttribute("classList", classList);

        SubjectDao subDao = new SubjectDao();
        List<Subject> subjectList = subDao.filter(school);
        request.setAttribute("subjectList", subjectList);

        // ▼ 入力チェック（回数は除外）
        if (entYear == null || entYear.isEmpty() ||
            classNum == null || classNum.isEmpty() ||
            subjectCd == null || subjectCd.isEmpty()) {
            request.setAttribute("message", "入学年度・クラス・科目を選択してください。");
            return "scoremanager/main/test_list.jsp";
        }

        // ▼ 回数が未指定ならデフォルトで1回目
        int num = 1;
        if (numStr != null && !numStr.isEmpty()) {
            try {
                num = Integer.parseInt(numStr);
            } catch (NumberFormatException e) {
                num = 1;
            }
        }

        TestDao testDao = new TestDao();
        request.setAttribute("subjectName", testDao.getSubjectName(subjectCd));
        request.setAttribute("testList", testDao.filterBySubject(school, entYear, classNum, subjectCd, num));

        return "scoremanager/main/test_list.jsp";
    }
}
