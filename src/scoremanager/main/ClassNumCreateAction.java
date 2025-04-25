package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class ClassNumCreateAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        return "/scoremanager/main/classnum_create.jsp";
    }
}