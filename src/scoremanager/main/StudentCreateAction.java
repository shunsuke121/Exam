package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class StudentCreateAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        List<ClassNum> classList = new ClassNumDao().filter(teacher.getSchoolCd());
        request.setAttribute("classList", classList);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> yearList = new ArrayList<>();
        for (int i = currentYear - 10; i <= currentYear + 1; i++) {
            yearList.add(i);
        }
        request.setAttribute("yearList", yearList);

        return "scoremanager/main/student_create.jsp";

    }
}
