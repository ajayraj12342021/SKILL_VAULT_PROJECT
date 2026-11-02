package ui;
import dao.StudentDAO;
import model.Student;
import javax.swing.*;
public class RegisterFrame {
    public void show() {
        JFrame f = new JFrame("Skill Vault - Registration");
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        JTextField name = new JTextField(20);
        JTextField email = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JTextField college = new JTextField(20);
        JTextField branch = new JTextField(20);
        JTextField semester = new JTextField(20);
        JButton register = new JButton("Register");
        JButton login = new JButton("Back to Login");
        JLabel message = new JLabel("Enter your details");
        p.add(new JLabel("Full Name"));
        p.add(name);
        p.add(new JLabel("Email"));
        p.add(email);
        p.add(new JLabel("Password"));
        p.add(password);
        p.add(new JLabel("College"));
        p.add(college);
        p.add(new JLabel("Branch"));
        p.add(branch);
        p.add(new JLabel("Semester"));
        p.add(semester);
        p.add(register);
        p.add(login);
        p.add(message);
        register.addActionListener(e -> {
            String n = name.getText().trim();
            String em = email.getText().trim();
            String pass = new String(password.getPassword());
            String clg = college.getText().trim();
            String br = branch.getText().trim();
            String sem = semester.getText().trim();
            if(n.isEmpty() || em.isEmpty() || pass.isEmpty() || clg.isEmpty() || br.isEmpty() || sem.isEmpty()) {
                message.setText("Please fill all fields.");
                return;
            }
            int semesterNo;
            try {
                semesterNo = Integer.parseInt(sem);
                if(semesterNo < 1 || semesterNo > 12) {
                    message.setText("Enter a valid semester.");
                    return;
                }
            } catch(NumberFormatException ex) {
                message.setText("Semester must be a number.");
                return;
            }
            try {
                Student s = new Student(n,em,pass,clg,br,semesterNo);
                boolean result = new StudentDAO().register(s);
                if(result) {
                    JOptionPane.showMessageDialog(f,"Registration successful!");
                    f.dispose();
                    new LoginFrame().show();
                } else {
                    message.setText("Registration failed.");
                }
            } catch(Exception ex) {
                message.setText("Error: " + ex.getMessage());
            }
        });
        login.addActionListener(e -> {
            f.dispose();
            new LoginFrame().show();
        });
        f.setContentPane(p);
        f.setSize(350,550);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}