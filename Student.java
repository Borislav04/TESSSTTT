import java.io.Serializable;
import java.util.*;

/**
 * Клас, представляващ студент с информация за лични данни, записани дисциплини и оценки.
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int facultyNumber;
    private String program;
    private String group;
    private int year;
    private Status status;

    private Set<Discipline> enrolledDisciplines;
    private Map<Discipline, Double> grades;

    public enum Status {
        ACTIVE, INTERRUPTED, GRADUATED
    }

    public Student(String name, int facultyNumber, String program, String group) {
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.program = program;
        this.group = group;
        this.year = 1;
        this.status = Status.ACTIVE;
        this.enrolledDisciplines = new HashSet<>();
        this.grades = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getFacultyNumber() {
        return facultyNumber;
    }

    public String getProgram() {
        return program;
    }

    public String getGroup() {
        return group;
    }

    public int getYear() {
        return year;
    }

    public Status getStatus() {
        return status;
    }

    public Set<Discipline> getEnrolledDisciplines() {
        return enrolledDisciplines;
    }

    public Map<Discipline, Double> getGrades() {
        return grades;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void enrollInDiscipline(Discipline discipline) {
        enrolledDisciplines.add(discipline);
    }

    public void addGrade(Discipline discipline, double grade) {
        grades.put(discipline, grade);
    }

    public boolean isEnrolledIn(Discipline discipline) {
        return enrolledDisciplines.contains(discipline);
    }

    @Override
    public String toString() {
        return facultyNumber + " - " + name + ", програма: " + program + ", група: " + group +
                ", курс: " + year + ", статус: " + status;
    }
}
