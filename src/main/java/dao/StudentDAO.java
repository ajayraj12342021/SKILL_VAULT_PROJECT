package dao;
import database.DBConnection;
import model.Student;
import java.sql.*;

public class StudentDAO {

    public boolean register(Student s) throws Exception {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO students " +
                "(name,email,password,college,branch,semester) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,s.name);
        ps.setString(2,s.email);
        ps.setString(3,s.password);
        ps.setString(4,s.college);
        ps.setString(5,s.branch);
        ps.setInt(6,s.semester);

        int result = ps.executeUpdate();
        ps.close();
        con.close();
        return result > 0;
    }
    public int login(String email, String password) throws Exception {
    Connection con = DBConnection.getConnection();

    PreparedStatement ps = con.prepareStatement(
        "SELECT student_id FROM students WHERE email=? AND password=?"
    );

    ps.setString(1, email);
    ps.setString(2, password);

    ResultSet rs = ps.executeQuery();
    int id = rs.next() ? rs.getInt("student_id") : -1;

    rs.close();
    ps.close();
    con.close();

    return id;
   }
}