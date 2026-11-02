package ui;
import dao.ProjectDAO;
import model.Project;
import javax.swing.*;
import java.util.List;

public class ProjectsFrame {
    private int studentId;
    private DefaultListModel<Project> listModel = new DefaultListModel<>();
    private JList<Project> projectList = new JList<>(listModel);

    public ProjectsFrame(int studentId) {
        this.studentId = studentId;
    }

    public void show() {
        JFrame f = new JFrame("Skill Vault - Projects");
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        JTextField title = new JTextField(20);
        JTextArea description = new JTextArea(4,20);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        JTextField technologies = new JTextField(20);
        JTextField githubUrl = new JTextField(20);

        JButton add = new JButton("Add Project");
        JButton update = new JButton("Update Project");
        JButton delete = new JButton("Delete Project");
        JButton refresh = new JButton("Refresh Projects");
        JButton clear = new JButton("Clear Fields");

        JLabel message = new JLabel("Enter project details.");

        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        p.add(new JLabel("Project Title"));
        p.add(title);
        p.add(new JLabel("Description"));
        p.add(new JScrollPane(description));
        p.add(new JLabel("Technologies Used"));
        p.add(technologies);
        p.add(new JLabel("GitHub URL"));
        p.add(githubUrl);
        p.add(add);
        p.add(new JLabel("Saved Projects"));
        p.add(new JScrollPane(projectList));
        p.add(update);
        p.add(delete);
        p.add(refresh);
        p.add(clear);
        p.add(message);

        Runnable clearFields = () -> {
            title.setText("");
            description.setText("");
            technologies.setText("");
            githubUrl.setText("");
            projectList.clearSelection();
        };

        Runnable loadProjects = () -> {
            try {
                List<Project> projects = new ProjectDAO().getProjects(studentId);
                listModel.clear();
                for (Project project : projects) {
                    listModel.addElement(project);
                }
                message.setText("Projects loaded successfully.");
            } catch (Exception ex) {
                message.setText("Load Error: " + ex.getMessage());
            }
        };

        projectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Project selected = projectList.getSelectedValue();
                if (selected != null) {
                    title.setText(selected.title);
                    description.setText(selected.description);
                    technologies.setText(selected.technologies);
                    githubUrl.setText(selected.githubUrl);
                    message.setText("Edit details and click Update.");
                }
            }
        });

        add.addActionListener(e -> {
            String projectTitle = title.getText().trim();
            if (projectTitle.isEmpty()) {
                message.setText("Project title is required.");
                return;
            }
            try {
                Project project = new Project(
                    studentId,
                    projectTitle,
                    description.getText().trim(),
                    technologies.getText().trim(),
                    githubUrl.getText().trim()
                );
                if (new ProjectDAO().saveProject(project)) {
                    message.setText("Project added successfully.");
                    clearFields.run();
                    loadProjects.run();
                } else {
                    message.setText("Project could not be added.");
                }
            } catch (Exception ex) {
                message.setText("Add Error: " + ex.getMessage());
            }
        });

        update.addActionListener(e -> {
            Project selected = projectList.getSelectedValue();
            if (selected == null) {
                message.setText("Select a project to update.");
                return;
            }
            String projectTitle = title.getText().trim();
            if (projectTitle.isEmpty()) {
                message.setText("Project title is required.");
                return;
            }
            try {
                Project project = new Project(
                    selected.projectId,
                    studentId,
                    projectTitle,
                    description.getText().trim(),
                    technologies.getText().trim(),
                    githubUrl.getText().trim()
                );
                if (new ProjectDAO().updateProject(project)) {
                    message.setText("Project updated successfully.");
                    clearFields.run();
                    loadProjects.run();
                } else {
                    message.setText("Project could not be updated.");
                }
            } catch (Exception ex) {
                message.setText("Update Error: " + ex.getMessage());
            }
        });

        delete.addActionListener(e -> {
            Project selected = projectList.getSelectedValue();
            if (selected == null) {
                message.setText("Select a project to delete.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(
                f, "Are you sure you want to delete this project?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) return;
            try {
                if (new ProjectDAO().deleteProject(selected.projectId, studentId)) {
                    message.setText("Project deleted successfully.");
                    clearFields.run();
                    loadProjects.run();
                } else {
                    message.setText("Project could not be deleted.");
                }
            } catch (Exception ex) {
                message.setText("Delete Error: " + ex.getMessage());
            }
        });

        refresh.addActionListener(e -> {
            clearFields.run();
            loadProjects.run();
        });

        clear.addActionListener(e -> {
            clearFields.run();
            message.setText("Fields cleared.");
        });

        f.setContentPane(p);
        f.setSize(450,650);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
        loadProjects.run();
    }
}