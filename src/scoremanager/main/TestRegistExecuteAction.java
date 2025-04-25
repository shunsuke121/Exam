package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // セッションから教員情報を取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        // 検索条件を取得（hiddenで渡ってきたもの）
        int entYear = Integer.parseInt(request.getParameter("ent_year"));
        String classNum = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");
        int num = Integer.parseInt(request.getParameter("num"));

        // 配列で渡ってきた学生番号と点数
        String[] nos = request.getParameterValues("nos");
        String[] points = request.getParameterValues("points");

        // セッションから科目リストを取得して、選択されたSubjectを特定
        @SuppressWarnings("unchecked")
        List<Subject> subjectList = (List<Subject>) request.getSession().getAttribute("subjectList");
        Subject subject = subjectList.stream()
            .filter(s -> s.getCd().equals(subjectCd))
            .findFirst()
            .orElse(null);

        if (subject == null) {
            throw new Exception("指定された科目が見つかりません");
        }

        TestDao testDao = new TestDao();

        // 登録処理（1件ずつ）
        for (int i = 0; i < nos.length; i++) {
            if (points[i] == null || points[i].trim().isEmpty()) continue;

            int p;
            try {
                p = Integer.parseInt(points[i]);
            } catch (NumberFormatException e) {
                continue;
            }

            if (p < 0 || p > 100) continue;

            Student stu = new Student();
            stu.setNo(nos[i]);
            stu.setClassNum(classNum);

            Test test = new Test();
            test.setStudent(stu);
            test.setSubject(subject);
            test.setNum(num);
            test.setPoint(p);
            test.setSchool(teacher.getSchool());

            testDao.save(test);
        }

        // 完了画面へ遷移
        return "scoremanager/main/test_regist_done.jsp";
    }
}
