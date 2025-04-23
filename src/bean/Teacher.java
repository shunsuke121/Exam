package bean;

import java.io.Serializable;

public class Teacher extends User implements Serializable {
    private String id;         // 教員ID
    private String password;
    private String name;
    private School school;
    // 所属学校(オブジェクト)

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    // 所属学校コード
    public String getSchoolCd() {
        return (school != null) ? school.getSchoolCd() : null;
    }

    // ✅ これを追加することで base.jsp の表示条件に一致するようになる
    public boolean isAuthenticated() {
        return true;
    }

}
