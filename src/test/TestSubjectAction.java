package test;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;

public class TestSubjectAction {

    public static void main(String[] args) {
        try {
            // 学校情報を仮で用意（本番ではセッションから取得）
            School school = new School();
            school.setSchoolCd("tes");

            Teacher teacher = new Teacher();
            teacher.setSchool(school);

            // 一覧確認
            System.out.println("----- 科目一覧 -----");
            SubjectDao dao = new SubjectDao();
            List<Subject> list = dao.filter(teacher.getSchool());
            for (Subject s : list) {
                System.out.println(s.getCd() + " : " + s.getName());
            }

            // 更新確認（F02→「Java演習」へ変更）
            System.out.println("----- 科目更新 -----");
            Subject subject = new Subject();
            subject.setCd("F02"); // 存在するコード
            subject.setName("Java演習");
            subject.setSchool(school);

            boolean updated = dao.save(subject);
            System.out.println("更新成功？ : " + updated);

            // 再一覧確認
            System.out.println("----- 更新後一覧 -----");
            list = dao.filter(teacher.getSchool());
            for (Subject s : list) {
                System.out.println(s.getCd() + " : " + s.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
