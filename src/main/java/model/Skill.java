package model;
public class Skill {
    public int studentId;
    public String skillName;
    public String proficiencyLevel;
    public Skill(int studentId, String skillName, String proficiencyLevel) {
        this.studentId = studentId;
        this.skillName = skillName;
        this.proficiencyLevel = proficiencyLevel;
    }
}