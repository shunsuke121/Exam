package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassNumUpdateExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String oldClassNum = req.getParameter("old_class_num");
        String newClassNum = req.getParameter("class_num");

        Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        ClassNum cn = new ClassNum();
        cn.setClassNum(oldClassNum);
        cn.setSchoolCd(teacher.getSchool().getSchoolCd());

        new ClassNumDao().save(cn, newClassNum);
        return "/scoremanager/main/classnum_update_done.jsp";
    }
}
