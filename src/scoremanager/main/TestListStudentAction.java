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

public class TestListStudentAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String studentKey = request.getParameter("student_key");
        request.setAttribute("studentKey", studentKey);

        if (studentKey == null || studentKey.trim().isEmpty()) {
            return "scoremanager/main/test_list_student.jsp";
        }

        // 学生検索（部分一致）
        StudentDao stuDao = new StudentDao();
        List<Student> matched = stuDao.filterByKey(school, studentKey);

        if (matched.isEmpty()) {
            return "scoremanager/main/test_list_student.jsp"; // 該当なし
        }

        // 科目リスト取得（表示用の名前などに使用）
        SubjectDao subDao = new SubjectDao();
        List<Subject> subjectList = subDao.filter(school);

        // 成績取得（対象学生1人分）
        Student student = matched.get(0); // 複数マッチした場合は先頭のみ表示
        TestDao testDao = new TestDao();
        List<Test> testList = testDao.filterByStudent(student, subjectList);

        request.setAttribute("testList", testList);

        return "scoremanager/main/test_list_student.jsp";
    }
}
