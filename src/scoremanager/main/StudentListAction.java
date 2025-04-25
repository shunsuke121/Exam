package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションから教員情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストパラメータ取得
        String entYearStr = request.getParameter("f1"); // 入学年度
        String classNum = request.getParameter("f2");   // クラス
        String isAttendStr = request.getParameter("f3"); // 在学フラグ（チェックされていれば値あり）

        // 初期値セット
        int entYear = 0;
        Boolean isAttend = null; // チェックされていない場合は null（条件なし扱い）

        if (isAttendStr != null) {
            isAttend = true;
        }

        List<Student> students = null;
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        StudentDao sDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao();
        Map<String, String> errors = new HashMap<>();

        // 入学年度をintに変換（0＝未指定）
        if (entYearStr != null && !entYearStr.equals("0")) {
            entYear = Integer.parseInt(entYearStr);
        }

        // 入学年度選択肢の作成
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }

        // クラス一覧の取得
        List<ClassNum> list = cNumDao.filter(teacher.getSchool());

        // 条件による絞り込み
        if (entYear != 0 && !classNum.equals("0")) {
            // 入学年度＋クラス
            if (isAttend != null) {
                students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
            } else {
                students = sDao.filter(teacher.getSchool(), entYear, classNum);
            }
        } else if (entYear != 0 && classNum.equals("0")) {
            // 入学年度のみ
            if (isAttend != null) {
                students = sDao.filter(teacher.getSchool(), entYear, isAttend);
            } else {
                students = sDao.filter(teacher.getSchool(), entYear);
            }
        } else if (entYear == 0 && (classNum == null || classNum.equals("0"))) {
            // 条件なし or 在学中のみ
            if (isAttend != null) {
                students = sDao.filter(teacher.getSchool(), isAttend);
            } else {
                students = sDao.filter(teacher.getSchool());
            }
        } else {
            // クラスだけ指定はエラー
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            request.setAttribute("errors", errors);
            if (isAttend != null) {
                students = sDao.filter(teacher.getSchool(), isAttend);
            } else {
                students = sDao.filter(teacher.getSchool());
            }
        }

        // フォーム再表示用にリクエストスコープへセット
        request.setAttribute("f1", entYearStr);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", isAttendStr);
        request.setAttribute("isAttend", isAttendStr);
        request.setAttribute("students", students);
        request.setAttribute("class_num_set", list);
        request.setAttribute("ent_year_set", entYearSet);

        return "scoremanager/main/student_list.jsp";
    }
}
