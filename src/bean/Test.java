package bean;

import java.io.Serializable;

public class Test implements Serializable {
    private Student student;
    private Subject subject;
    private int num;          // 回数
    private int point;        // 点数
    private School school;
    private Integer point1;
    private Integer point2;

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }

    public int getPoint() { return point; }
    public void setPoint(int point) { this.point = point; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
    public Integer getPoint1() { return point1; }
    public void setPoint1(Integer point1) { this.point1 = point1; }

    public Integer getPoint2() { return point2; }
    public void setPoint2(Integer point2) { this.point2 = point2; }
}
