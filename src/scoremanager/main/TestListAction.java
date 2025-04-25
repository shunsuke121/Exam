// 成績参照（検索条件入力画面）初期化用 Action - 設計書準拠
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
import tool.Action;

public class TestListAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // ▼ 入学年度リスト（現在-10年〜翌年）
        List<Integer> yearList = new ArrayList<>();
        int thisYear = LocalDate.now().getYear();
        for (int i = thisYear - 10; i <= thisYear + 1; i++) {
            yearList.add(i);
        }

        // ▼ クラス一覧・科目一覧
        ClassNumDao classDao = new ClassNumDao();
        SubjectDao subDao = new SubjectDao();

        List<ClassNum> classList = classDao.filter(school);
        List<Subject> subjectList = subDao.filter(school);

        request.setAttribute("yearList", yearList);
        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);

        return "scoremanager/main/test_list.jsp";
    }
}