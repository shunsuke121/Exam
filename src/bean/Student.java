package bean;

import java.io.Serializable;

public class Student implements Serializable {
    private String no;         // 学生番号
    private String name;       // 氏名
    private int entYear;       // 入学年度
    private String classNum;   // クラス番号
    private boolean isAttend;  // 在学中フラグ
    private String schoolCd;   // 学校コード

    // --- ゲッター・セッター ---

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public boolean isAttend() {
        return isAttend;
    }

    public void setAttend(boolean isAttend) {
        this.isAttend = isAttend;
    }

    public String getSchoolCd() {
        return schoolCd;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }

    public Boolean getIsAttend() {
        return Boolean.valueOf(isAttend);
    }

}
