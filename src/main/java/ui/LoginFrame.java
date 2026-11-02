package ui;
import dao.StudentDAO;
import javax.swing.*;
public class LoginFrame {
    public void show() {
        JFrame f = new JFrame("Skill Vault - Login");
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        JTextField email = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JButton login = new JButton("Login");
        JButton register = new JButton("Register");
        JLabel msg = new JLabel("Enter login details");
        p.add(new JLabel("Email"));
        p.add(email);
        p.add(Box.createVerticalStrut(8));
        p.add(new JLabel("Password"));
        p.add(password);
        p.add(Box.createVerticalStrut(8));
        p.add(login);
        p.add(register);
        p.add(msg);
        login.addActionListener(e -> {
            try {
                int id = new StudentDAO().login(email.getText().trim(),new String(password.getPassword()));
                if(id > 0) {
                    f.dispose();
                    new DashboardFrame(id).show();
                } else msg.setText("Invalid email or password");
            } catch(Exception ex) {
                msg.setText("Login failed: " + ex.getMessage());
            }
        });
        register.addActionListener(e -> {
            f.dispose();
            new RegisterFrame().show();
        });
        f.setContentPane(p);
        f.setSize(350,300);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}