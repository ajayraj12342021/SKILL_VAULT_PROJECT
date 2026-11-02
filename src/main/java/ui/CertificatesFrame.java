package ui;
import javax.swing.*;
import java.util.List;
import dao.CertificateDAO;
import model.Certificate;

public class CertificatesFrame extends JFrame {

    private int studentId;
    private JTextField nameField, issuerField, dateField, urlField;
    private DefaultListModel<Certificate> listModel;
    private JList<Certificate> certificateList;
    private CertificateDAO certificateDAO;

    public CertificatesFrame(int studentId) {
        this.studentId = studentId;
        certificateDAO = new CertificateDAO();

        setTitle("Skill Vault - Certificates");
        setSize(580, 780);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel nameLabel = new JLabel("Certificate Name");
        nameLabel.setBounds(45, 30, 200, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(20, 60, 520, 65);
        add(nameField);

        JLabel issuerLabel = new JLabel("Issuer");
        issuerLabel.setBounds(45, 130, 200, 25);
        add(issuerLabel);

        issuerField = new JTextField();
        issuerField.setBounds(20, 160, 520, 65);
        add(issuerField);

        JLabel dateLabel = new JLabel("Completion Date (YYYY-MM-DD)");
        dateLabel.setBounds(45, 230, 300, 25);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(20, 260, 520, 65);
        add(dateField);

        JLabel urlLabel = new JLabel("Certificate URL");
        urlLabel.setBounds(45, 330, 200, 25);
        add(urlLabel);

        urlField = new JTextField();
        urlField.setBounds(20, 360, 520, 65);
        add(urlField);

        JButton addButton = new JButton("Add Certificate");
        addButton.setBounds(45, 430, 155, 35);
        add(addButton);

        JLabel listLabel = new JLabel("Your Certificates");
        listLabel.setBounds(45, 470, 200, 25);
        add(listLabel);

        listModel = new DefaultListModel<>();
        certificateList = new JList<>(listModel);
        certificateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        certificateList.setFixedCellHeight(65);

        JScrollPane scrollPane = new JScrollPane(certificateList);
        scrollPane.setBounds(20, 495, 520, 190);
        add(scrollPane);

        JButton deleteButton = new JButton("Delete Selected Certificate");
        deleteButton.setBounds(45, 690, 245, 35);
        add(deleteButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(45, 730, 105, 35);
        add(refreshButton);

        JLabel statusLabel = new JLabel("Enter certificate details.");
        statusLabel.setBounds(45, 765, 400, 25);
        add(statusLabel);

        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String issuer = issuerField.getText().trim();
            String date = dateField.getText().trim();
            String url = urlField.getText().trim();

            if (name.isEmpty() || issuer.isEmpty() || date.isEmpty() || url.isEmpty()) {
                statusLabel.setText("Please fill all fields.");
                return;
            }

            Certificate c = new Certificate(studentId, name, issuer, date, url);

            if (certificateDAO.saveCertificate(c)) {
                statusLabel.setText("Certificate added successfully.");
                clearFields();
                loadCertificates();
            } else {
                statusLabel.setText("Unable to add certificate.");
            }
        });

        deleteButton.addActionListener(e -> {
            Certificate selected = certificateList.getSelectedValue();

            if (selected == null) {
                statusLabel.setText("Select a certificate first.");
                return;
            }

            if (certificateDAO.deleteCertificate(selected.certificateId, studentId)) {
                statusLabel.setText("Certificate deleted successfully.");
                loadCertificates();
            } else {
                statusLabel.setText("Unable to delete certificate.");
            }
        });

        refreshButton.addActionListener(e -> {
            loadCertificates();
            statusLabel.setText("Certificates refreshed.");
        });

        loadCertificates();
        setVisible(true);
    }

    private void loadCertificates() {
        listModel.clear();

        List<Certificate> certificates = certificateDAO.getCertificates(studentId);

        for (Certificate c : certificates) {
            listModel.addElement(c);
        }
    }

    private void clearFields() {
        nameField.setText("");
        issuerField.setText("");
        dateField.setText("");
        urlField.setText("");
    }
}

