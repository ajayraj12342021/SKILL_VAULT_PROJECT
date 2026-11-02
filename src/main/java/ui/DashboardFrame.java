package ui;
import javax.swing.*;

public class DashboardFrame {
    private int studentId;

    public DashboardFrame(int studentId) {
        this.studentId = studentId;
    }

    public void show() {
        JFrame f = new JFrame("Skill Vault Dashboard");
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(30,40,30,40));
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Skill Vault Dashboard");
        title.setFont(UIManager.getFont("Label.font").deriveFont(22f));

        JButton skills = new JButton("Manage Skills");
        JButton projects = new JButton("Manage Projects");
        JButton certificates = new JButton("Manage Certificates");
        JButton portfolio = new JButton("My Portfolio");
        JButton logout = new JButton("Logout");

        JButton[] buttons = {skills, projects, certificates, portfolio, logout};

        for (JButton button : buttons) {
            button.setFont(UIManager.getFont("Button.font").deriveFont(16f));
            button.setPreferredSize(new java.awt.Dimension(250,50));
            button.setMaximumSize(button.getPreferredSize());
            button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        }

        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        p.add(title);
        p.add(Box.createVerticalStrut(25));

        for (JButton button : buttons) {
            p.add(button);
            p.add(Box.createVerticalStrut(12));
        }

        skills.addActionListener(e -> new SkillsFrame(studentId).show());
        projects.addActionListener(e -> new ProjectsFrame(studentId).show());
        certificates.addActionListener(e -> new CertificatesFrame(studentId).show());
        portfolio.addActionListener(e -> new PortfolioFrame(studentId));

        logout.addActionListener(e -> {
            f.dispose();
            new LoginFrame().show();
        });

        f.setContentPane(p);
        f.setSize(400,450);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
