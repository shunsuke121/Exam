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

public class StudentCreateExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        // パラメータ取得
        String ent_year = request.getParameter("ent_year");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String class_num = request.getParameter("class_num");
        String is_attend = request.getParameter("is_attend");

        // エラー時の再表示用：共通でリストを作る
        List<ClassNum> classList = new ClassNumDao().filter(teacher.getSchoolCd());
        request.setAttribute("classList", classList);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> yearList = new ArrayList<>();
        for (int i = currentYear - 10; i <= currentYear + 1; i++) {
            yearList.add(i);
        }
        request.setAttribute("yearList", yearList);

        // 入力値の保持
        request.setAttribute("ent_year", ent_year);
        request.setAttribute("no", no);
        request.setAttribute("name", name);
        request.setAttribute("class_num", class_num);
        request.setAttribute("is_attend", is_attend);

        // 入力チェック
        if (no == null || no.isEmpty() || name == null || name.isEmpty()) {
            request.setAttribute("error", "学生番号と氏名は必須です。");
            return "scoremanager/main/student_create.jsp";
        }

        if (ent_year == null || ent_year.isEmpty()) {
            request.setAttribute("error", "入学年度を選択してください。");
            return "scoremanager/main/student_create.jsp";
        }

        // 学生番号の重複チェック
        StudentDao studentDao = new StudentDao();
        if (studentDao.get(no) != null) {
            request.setAttribute("error", "学生番号が重複しています。");
            return "scoremanager/main/student_create.jsp";
        }

        // 学生データ登録
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(Integer.parseInt(ent_year));
        student.setClassNum(class_num);
        student.setAttend(is_attend != null);
        student.setSchoolCd(teacher.getSchoolCd());

        studentDao.save(student);

        return "scoremanager/main/student_create_done.jsp";
    }
}
