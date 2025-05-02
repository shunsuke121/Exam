package scoremanager.main;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestListExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String mode = request.getParameter("mode");
        String entYear = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");
        String studentNo = request.getParameter("f4");
        String numStr = request.getParameter("num");

        TestDao testDao = new TestDao();
        StudentDao studentDao = new StudentDao();

        // 初期表示処理（modeがnullまたは空）
        if (mode == null || mode.isEmpty()) {
            List<Integer> years = new ArrayList<>();
            int currentYear = Year.now().getValue();
            for (int i = currentYear - 10; i <= currentYear + 1; i++) {
                years.add(i);
            }

            ClassNumDao classNumDao = new ClassNumDao();
            SubjectDao subjectDao = new SubjectDao();

            request.setAttribute("years", years);
            request.setAttribute("classNums", classNumDao.filter(teacher.getSchool()));
            request.setAttribute("subjects", subjectDao.filter(teacher.getSchool()));

            return "test_list.jsp";
        }

        // 科目別検索
        if ("sj".equals(mode)) {
            int num = (numStr != null && !numStr.isEmpty()) ? Integer.parseInt(numStr) : 1;
            List<Test> testList = testDao.filterBySubject(teacher.getSchool(), entYear, classNum, subjectCd, num);
            request.setAttribute("subjectName", testDao.getSubjectName(subjectCd));
            request.setAttribute("testList", testList);
            return "test_list_subject.jsp";
        }

        // 学生別検索（安全対策付き）
        if ("st".equals(mode)) {
            Student student = studentDao.get(studentNo);
            if (student == null) {
                request.setAttribute("message", "指定された学生は存在しません。\n学生番号を確認してください。\n");
                request.setAttribute("testList", null);
                return "test_list_student.jsp";
            }

            student.setSchool(teacher.getSchool());
            SubjectDao subjectDao = new SubjectDao();
            List<Test> testList = testDao.filterByStudent(student, subjectDao.filter(teacher.getSchool()));

            request.setAttribute("student", student);
            request.setAttribute("testList", testList);
            return "test_list_student.jsp";
        }

        return "test_list.jsp";
    }
}