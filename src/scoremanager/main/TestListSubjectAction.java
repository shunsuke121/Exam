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
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestListSubjectAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        List<Integer> yearList = new ArrayList<>();
        int thisYear = LocalDate.now().getYear();
        for (int i = thisYear - 10; i <= thisYear + 1; i++) {
            yearList.add(i);
        }

        ClassNumDao classDao = new ClassNumDao();
        List<ClassNum> classList = classDao.filter(school);

        SubjectDao subDao = new SubjectDao();
        List<Subject> subjectList = subDao.filter(school);

        request.setAttribute("yearList", yearList);
        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);

        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");
        String numStr = request.getParameter("num");
        String studentKey = request.getParameter("student_key");

        request.setAttribute("entYear", entYearStr);
        request.setAttribute("classNum", classNum);
        request.setAttribute("subjectCd", subjectCd);
        request.setAttribute("num", numStr);
        request.setAttribute("studentKey", studentKey);

        TestDao testDao = new TestDao();

        if (studentKey != null && !studentKey.trim().isEmpty()) {
            // 学生情報で検索
            StudentDao stuDao = new StudentDao();
            List<Student> students = stuDao.filterByKey(school, studentKey);

            if (!students.isEmpty()) {
                Student student = students.get(0); // 最初の1件のみ表示
                List<Subject> subjects = subDao.filter(school);
                List<Test> testList = testDao.filterByStudent(student, subjects);
                request.setAttribute("testList", testList);

                // 科目名（先頭テストの科目）を表示用にセット
                if (!testList.isEmpty()) {
                    request.setAttribute("subjectName", testList.get(0).getSubject().getName());
                }
            }
        } else if (entYearStr != null && classNum != null && subjectCd != null && numStr != null) {
            // 科目情報で検索
            int entYear = Integer.parseInt(entYearStr);
            int num = Integer.parseInt(numStr);

            Subject subject = subjectList.stream()
                .filter(s -> s.getCd().equals(subjectCd))
                .findFirst()
                .orElse(null);

            if (subject != null) {
                List<Test> testList = testDao.filter(entYear, classNum, subject, num, school);
                request.setAttribute("testList", testList);
                request.setAttribute("subjectName", subject.getName()); // ← 科目名をセット
            }
        }

        return "scoremanager/main/test_list_subject.jsp";
    }
}
