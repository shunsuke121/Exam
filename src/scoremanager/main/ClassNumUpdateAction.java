package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassNumUpdateAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String classNum = req.getParameter("class_num");
        Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        ClassNum cn = new ClassNumDao().get(classNum, teacher.getSchool());
        req.setAttribute("classNum", cn);
        return "/scoremanager/main/classnum_update.jsp";
    }
}