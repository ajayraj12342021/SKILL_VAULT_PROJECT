package dao;
import database.DBConnection;
import model.Certificate;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertificateDAO {

    public boolean saveCertificate(Certificate c) {
        String sql = "INSERT INTO certificates (student_id, title, issuer, completion_date, certificate_url) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.studentId);
            ps.setString(2, c.certificateName);
            ps.setString(3, c.issuer);
            ps.setString(4, c.issueDate);
            ps.setString(5, c.certificateUrl);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Certificate> getCertificates(int studentId) {
        List<Certificate> list = new ArrayList<>();

        String sql = "SELECT * FROM certificates WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Certificate c = new Certificate(
                    rs.getInt("certificate_id"),
                    rs.getInt("student_id"),
                    rs.getString("title"),
                    rs.getString("issuer"),
                    rs.getString("completion_date"),
                    rs.getString("certificate_url")
                );

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean deleteCertificate(int certificateId, int studentId) {
        String sql = "DELETE FROM certificates WHERE certificate_id = ? AND student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, certificateId);
            ps.setInt(2, studentId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
