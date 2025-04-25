package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassNumListAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        List<ClassNum> classNums = new ClassNumDao().filter(teacher.getSchool());
        req.setAttribute("classNums", classNums);
        return "scoremanager/main/classnum_list.jsp";
    }
}