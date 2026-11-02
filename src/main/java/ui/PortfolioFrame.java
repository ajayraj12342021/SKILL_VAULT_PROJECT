package ui;
import javax.swing.*;
import java.sql.*;
import database.DBConnection;

public class PortfolioFrame extends JFrame {
    private int studentId;
    private JTextPane portfolioArea;

    public PortfolioFrame(int studentId) {
        this.studentId = studentId;
        setTitle("Skill Vault - My Portfolio");
        setSize(750, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel heading = new JLabel("MY DIGITAL PORTFOLIO");
        heading.setBounds(220, 15, 350, 35);
        heading.setFont(new javax.swing.plaf.FontUIResource("Arial", 22, 1));
        add(heading);

        portfolioArea = new JTextPane();
        portfolioArea.setContentType("text/html");
        portfolioArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(portfolioArea);
        scrollPane.setBounds(30, 65, 675, 530);
        add(scrollPane);

        JButton refreshButton = new JButton("Refresh Portfolio");
        refreshButton.setBounds(280, 610, 170, 35);
        add(refreshButton);
        refreshButton.addActionListener(e -> loadPortfolio());

        loadPortfolio();
        setVisible(true);
    }

    private void loadPortfolio() {
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family:Arial;padding:15px;'>");
        html.append("<h1 align='center'>STUDENT PORTFOLIO</h1><hr>");

        try (Connection con = DBConnection.getConnection()) {
            String studentSql = "SELECT * FROM students WHERE student_id = ?";
            try (PreparedStatement ps = con.prepareStatement(studentSql)) {
                ps.setInt(1, studentId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    html.append("<h2>Personal Information</h2>");
                    html.append("<b>Name:</b> ").append(rs.getString("name")).append("<br>");
                    html.append("<b>Email:</b> ").append(rs.getString("email")).append("<br>");
                    html.append("<b>College:</b> ").append(rs.getString("college")).append("<br>");
                    html.append("<b>Branch:</b> ").append(rs.getString("branch")).append("<br>");
                    html.append("<b>Semester:</b> ").append(rs.getInt("semester")).append("<br>");
                }
            }

            html.append("<hr><h2>My Skills</h2>");
            String skillSql = "SELECT skill_name, proficiency FROM skills WHERE student_id = ?";
            try (PreparedStatement ps = con.prepareStatement(skillSql)) {
                ps.setInt(1, studentId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    html.append("<b>").append(rs.getString("skill_name")).append("</b> - ");
                    html.append(rs.getString("proficiency")).append("<br>");
                }
            }

            html.append("<hr><h2>My Certificates</h2>");
            String certificateSql = "SELECT title, issuer, completion_date, certificate_url FROM certificates WHERE student_id = ?";
            try (PreparedStatement ps = con.prepareStatement(certificateSql)) {
                ps.setInt(1, studentId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String url = rs.getString("certificate_url");
                    html.append("<b>").append(rs.getString("title")).append("</b><br>");
                    html.append("Issuer: ").append(rs.getString("issuer")).append("<br>");
                    html.append("Completion Date: ").append(rs.getString("completion_date")).append("<br>");
                    if (url != null && !url.isEmpty()) {
                        html.append("URL: ").append(url).append("<br>");
                    }
                    html.append("<br>");
                }
            }

            html.append("<hr><h2>My Projects</h2>");
            String projectSql = "SELECT title, description, technologies, github_url FROM projects WHERE student_id = ?";
            try (PreparedStatement ps = con.prepareStatement(projectSql)) {
                ps.setInt(1, studentId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String githubUrl = rs.getString("github_url");
                    html.append("<b>").append(rs.getString("title")).append("</b><br>");
                    html.append("Description: ").append(rs.getString("description")).append("<br>");
                    html.append("Technologies: ").append(rs.getString("technologies")).append("<br>");
                    if (githubUrl != null && !githubUrl.isEmpty()) {
                        html.append("GitHub: ").append(githubUrl).append("<br>");
                    }
                    html.append("<br>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            html.append("<br>Error loading portfolio.");
        }

        html.append("</body></html>");
        portfolioArea.setText(html.toString());
        portfolioArea.setCaretPosition(0);
    }
}
 
