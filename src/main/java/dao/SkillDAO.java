package dao;
import database.DBConnection;
import model.Skill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {
    public boolean saveSkill(Skill s) throws Exception {
        String sql = "INSERT INTO skills (student_id, skill_name, proficiency) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.studentId);
            ps.setString(2, s.skillName);
            ps.setString(3, s.proficiencyLevel);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Skill> getSkills(int studentId) throws Exception {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT skill_name, proficiency FROM skills WHERE student_id = ? ORDER BY skill_name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    skills.add(new Skill(studentId, rs.getString("skill_name"), rs.getString("proficiency")));
                }
            }
        }
        return skills;
    }

    public boolean updateSkill(int studentId, String oldName, String newName, String level) throws Exception {
        String sql = "UPDATE skills SET skill_name = ?, proficiency = ? WHERE student_id = ? AND skill_name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setString(3, level);
            ps.setInt(2, studentId);
            ps.setString(4, oldName);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteSkill(int studentId, String skillName) throws Exception {
        String sql = "DELETE FROM skills WHERE student_id = ? AND skill_name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setString(2, skillName);
            return ps.executeUpdate() > 0;
        }
    }
}
