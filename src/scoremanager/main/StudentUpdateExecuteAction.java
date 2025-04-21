// StudentUpdateExecuteAction.java
package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        String no = request.getParameter("no");
        String ent_year = request.getParameter("ent_year");
        String name = request.getParameter("name");
        String class_num = request.getParameter("class_num");
        String is_attend = request.getParameter("is_attend");

        // 入力チェック
        if (name == null || name.isEmpty()) {
            request.setAttribute("error", "氏名を入力してください。");
            return new StudentUpdateAction().execute(request, response);
        }

        // 更新処理
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(Integer.parseInt(ent_year));
        student.setClassNum(class_num);
        student.setAttend(is_attend != null);
        student.setSchoolCd(teacher.getSchoolCd());

        new StudentDao().save(student);

        return "scoremanager/main/student_update_done.jsp";
    }
}