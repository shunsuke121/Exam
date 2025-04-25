package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassNumCreateExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String classNum = req.getParameter("class_num");
        Teacher teacher = (Teacher) req.getSession().getAttribute("user");

        ClassNum cn = new ClassNum();
        cn.setClassNum(classNum);
        cn.setSchoolCd(teacher.getSchool().getSchoolCd());

        new ClassNumDao().save(cn);
        return "/scoremanager/main/classnum_create_done.jsp";
    }
}