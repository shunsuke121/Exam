// 修正済 TestListStudentExecuteAction.java - student.setSchool(school) 追加済み
package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String studentNo = request.getParameter("student_key");
        request.setAttribute("studentKey", studentNo);

        // ▼ プルダウンデータ（検索後も保持）
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

        if (studentNo == null || studentNo.isEmpty()) {
            request.setAttribute("message", "学生番号を入力してください。\n");
            return "scoremanager/main/test_list.jsp";
        }

        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(studentNo);

        if (student == null) {
            request.setAttribute("message", "指定された学生は存在しません。\n");
            request.setAttribute("testList", null);
            return "scoremanager/main/test_list.jsp";
        }

        student.setSchool(school); // ← NullPointerException 対策

        request.setAttribute("student", student);

        TestDao testDao = new TestDao();
        List<bean.Test> testList = testDao.filterByStudent(student, subjectList);
        request.setAttribute("testList", testList);

        return "scoremanager/main/test_list.jsp";
    }
}