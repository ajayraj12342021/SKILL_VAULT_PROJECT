package model;
public class Student {
    public int id;
    public String name,email,password,college,branch;
    public int semester;

    public Student(String name,String email,String password,
                   String college,String branch,int semester) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.college = college;
        this.branch = branch;
        this.semester = semester;
    }
}
