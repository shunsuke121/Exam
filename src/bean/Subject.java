package bean;

import java.io.Serializable;

public class Subject implements Serializable {
    private String cd;           // 科目コード
    private String name;         // 科目名
    private School school;       // 所属学校（School型）

    public String getCd() {
        return cd;
    }
    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }

    // 学校コード取得用（省略可）
    public String getSchoolCd() {
        return (school != null) ? school.getSchoolCd() : null;
    }

}
