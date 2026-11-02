package model;
public class Project {
    public int projectId;
    public int studentId;
    public String title;
    public String description;
    public String technologies;
    public String githubUrl;

    public Project(int studentId, String title, String description, String technologies, String githubUrl) {
        this.studentId = studentId;
        this.title = title;
        this.description = description;
        this.technologies = technologies;
        this.githubUrl = githubUrl;
    }

    public Project(int projectId, int studentId, String title, String description, String technologies, String githubUrl) {
        this.projectId = projectId;
        this.studentId = studentId;
        this.title = title;
        this.description = description;
        this.technologies = technologies;
        this.githubUrl = githubUrl;
    }

    @Override
    public String toString() {
        return title;
    }
}