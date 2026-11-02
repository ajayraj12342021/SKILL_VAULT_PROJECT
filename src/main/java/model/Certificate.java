package model;

public class Certificate {
    public int certificateId;
    public int studentId;
    public String certificateName;
    public String issuer;
    public String issueDate;
    public String certificateUrl;

    public Certificate(int studentId, String certificateName, String issuer, String issueDate, String certificateUrl) {
        this.studentId = studentId;
        this.certificateName = certificateName;
        this.issuer = issuer;
        this.issueDate = issueDate;
        this.certificateUrl = certificateUrl;
    }

    public Certificate(int certificateId, int studentId, String certificateName, String issuer, String issueDate, String certificateUrl) {
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.certificateName = certificateName;
        this.issuer = issuer;
        this.issueDate = issueDate;
        this.certificateUrl = certificateUrl;
    }

    @Override
    public String toString() {
        return "<html><b>" + certificateName + "</b><br><font color='blue'>" + certificateUrl + "</font></html>";
    }
}
