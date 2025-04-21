// StudentUpdateAction.java
package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String no = request.getParameter("no");

        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        StudentDao sDao = new StudentDao();
        ClassNumDao cDao = new ClassNumDao();

        Student student = sDao.get(no);
        request.setAttribute("student", student);

        List<ClassNum> classList = cDao.filter(teacher.getSchoolCd());
        request.setAttribute("classList", classList);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> yearList = new ArrayList<>();
        for (int i = currentYear - 10; i <= currentYear + 1; i++) {
            yearList.add(i);
        }
        request.setAttribute("yearList", yearList);

        return "scoremanager/main/student_update.jsp";
    }
}