package ui;
import dao.SkillDAO;
import model.Skill;
import javax.swing.*;
import java.util.List;

public class SkillsFrame {
    private int studentId;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> skillList = new JList<>(listModel);
    private List<Skill> savedSkills;

    public SkillsFrame(int studentId) {
        this.studentId = studentId;
    }

    public void show() {
        JFrame f = new JFrame("Skill Vault - My Skills");
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JTextField skillName = new JTextField(20);
        String[] levels = {"Beginner", "Intermediate", "Advanced", "Expert"};
        JComboBox<String> proficiency = new JComboBox<>(levels);

        JButton add = new JButton("Add Skill");
        JButton update = new JButton("Update Selected Skill");
        JButton delete = new JButton("Delete Selected Skill");
        JButton refresh = new JButton("Refresh");

        JLabel message = new JLabel("Add or select a skill.");

        skillList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        p.add(new JLabel("Skill Name"));
        p.add(skillName);
        p.add(Box.createVerticalStrut(8));
        p.add(new JLabel("Proficiency Level"));
        p.add(proficiency);
        p.add(Box.createVerticalStrut(10));
        p.add(add);
        p.add(new JLabel("Your Saved Skills"));
        p.add(new JScrollPane(skillList));
        p.add(update);
        p.add(delete);
        p.add(refresh);
        p.add(message);

        Runnable loadSkills = () -> {
            try {
                savedSkills = new SkillDAO().getSkills(studentId);
                listModel.clear();
                for (Skill s : savedSkills) {
                    listModel.addElement(s.skillName + " - " + s.proficiencyLevel);
                }
            } catch (Exception ex) {
                message.setText("Load Error: " + ex.getMessage());
            }
        };

        skillList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = skillList.getSelectedIndex();
                if (index >= 0 && savedSkills != null && index < savedSkills.size()) {
                    Skill selected = savedSkills.get(index);
                    skillName.setText(selected.skillName);
                    proficiency.setSelectedItem(selected.proficiencyLevel);
                    message.setText("Edit the selected skill and click Update.");
                }
            }
        });

        add.addActionListener(e -> {
            String name = skillName.getText().trim();
            String level = (String) proficiency.getSelectedItem();

            if (name.isEmpty()) {
                message.setText("Please enter a skill name.");
                return;
            }

            try {
                boolean result = new SkillDAO().saveSkill(new Skill(studentId, name, level));
                if (result) {
                    message.setText("Skill added successfully.");
                    skillName.setText("");
                    skillList.clearSelection();
                    loadSkills.run();
                } else {
                    message.setText("Skill could not be added.");
                }
            } catch (Exception ex) {
                message.setText("Add Error: " + ex.getMessage());
            }
        });

        update.addActionListener(e -> {
            int index = skillList.getSelectedIndex();
            String newName = skillName.getText().trim();
            String level = (String) proficiency.getSelectedItem();

            if (index == -1) {
                message.setText("Please select a skill to update.");
                return;
            }
            if (newName.isEmpty()) {
                message.setText("Skill name cannot be empty.");
                return;
            }

            try {
                String oldName = savedSkills.get(index).skillName;
                boolean result = new SkillDAO().updateSkill(studentId, oldName, newName, level);

                if (result) {
                    message.setText("Skill updated successfully.");
                    skillName.setText("");
                    skillList.clearSelection();
                    loadSkills.run();
                } else {
                    message.setText("Skill could not be updated.");
                }
            } catch (Exception ex) {
                message.setText("Update Error: " + ex.getMessage());
            }
        });

        delete.addActionListener(e -> {
            int index = skillList.getSelectedIndex();

            if (index == -1) {
                message.setText("Please select a skill to delete.");
                return;
            }

            try {
                String name = savedSkills.get(index).skillName;
                boolean result = new SkillDAO().deleteSkill(studentId, name);

                if (result) {
                    message.setText("Skill deleted successfully.");
                    skillName.setText("");
                    skillList.clearSelection();
                    loadSkills.run();
                } else {
                    message.setText("Skill could not be deleted.");
                }
            } catch (Exception ex) {
                message.setText("Delete Error: " + ex.getMessage());
            }
        });

        refresh.addActionListener(e -> {
            skillName.setText("");
            skillList.clearSelection();
            loadSkills.run();
        });

        f.setContentPane(p);
        f.setSize(420, 550);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);

        loadSkills.run();
    }
}