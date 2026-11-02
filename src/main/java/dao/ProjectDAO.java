package dao;
import database.DBConnection;
import model.Project;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    public boolean saveProject(Project p) throws Exception {
        String sql = "INSERT INTO projects (student_id, title, description, technologies, github_url) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.studentId);
            ps.setString(2, p.title);
            ps.setString(3, p.description);
            ps.setString(4, p.technologies);
            ps.setString(5, p.githubUrl);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Project> getProjects(int studentId) throws Exception {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE student_id = ? ORDER BY project_id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    projects.add(new Project(
                        rs.getInt("project_id"),
                        studentId,
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("technologies"),
                        rs.getString("github_url")
                    ));
                }
            }
        }
        return projects;
    }

    public boolean updateProject(Project p) throws Exception {
        String sql = "UPDATE projects SET title=?, description=?, technologies=?, github_url=? WHERE project_id=? AND student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.title);
            ps.setString(2, p.description);
            ps.setString(3, p.technologies);
            ps.setString(4, p.githubUrl);
            ps.setInt(5, p.projectId);
            ps.setInt(6, p.studentId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteProject(int projectId, int studentId) throws Exception {
        String sql = "DELETE FROM projects WHERE project_id=? AND student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ps.setInt(2, studentId);
            return ps.executeUpdate() > 0;
        }
    }
}