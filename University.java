import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Клас University – управлява студенти, записвания и оценки чрез обекти.
 */
public class University implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Student> students;
    private Curriculum curriculum;

    public University() {
        this.students = new ArrayList<>();
        this.curriculum = new Curriculum();
    }

    public void enrollStudent(String name, int fn, String program, String group) {
        if (findStudentByFacultyNumber(fn).isPresent()) {
            System.out.println("Студент с този факултетен номер вече съществува.");
            return;
        }
        students.add(new Student(name, fn, program, group));
        System.out.println("Студентът е записан успешно.");
    }

    public void advanceStudent(int fn) {
        findStudentByFacultyNumber(fn).ifPresentOrElse(student -> {
            if (student.getStatus() != Student.Status.ACTIVE) {
                System.out.println("Само активни студенти могат да преминават в следващ курс.");
                return;
            }
            student.setYear(student.getYear() + 1);
            System.out.println("Студентът е преминал в курс " + student.getYear() + ".");
        }, () -> System.out.println("Няма такъв студент."));
    }

    public void changeStudentProgram(int fn, String newProgram) {
        findStudentByFacultyNumber(fn).ifPresentOrElse(student -> {
            student.setStatus(Student.Status.ACTIVE);
            student.setYear(1);
            student.getEnrolledDisciplines().clear();
            student.getGrades().clear();
            student.setGroup("new");
            student.setStatus(Student.Status.ACTIVE);
            student.setYear(1);
            student.enrollInDiscipline(new Discipline(newProgram, "задължителна", 1, 0)); // временно
            System.out.println("Програмата е сменена успешно.");
        }, () -> System.out.println("Няма такъв студент."));
    }

    public void graduateStudent(int fn) {
        findStudentByFacultyNumber(fn).ifPresentOrElse(student -> {
            if (student.getStatus() != Student.Status.ACTIVE) {
                System.out.println("Само активни студенти могат да се дипломират.");
                return;
            }

            boolean allPassed = true;
            for (Discipline d : student.getEnrolledDisciplines()) {
                Double grade = student.getGrades().get(d);
                if (grade == null || grade < 3.0) {
                    allPassed = false;
                    System.out.println("Невзет или неположен изпит по: " + d.getName());
                }
            }

            if (allPassed) {
                student.setStatus(Student.Status.GRADUATED);
                System.out.println("Студентът е успешно дипломиран.");
            } else {
                System.out.println("Студентът НЕ може да се дипломира.");
            }
        }, () -> System.out.println("Няма такъв студент."));
    }

    public void interruptStudent(int fn) {
        findStudentByFacultyNumber(fn).ifPresentOrElse(student -> {
            student.setStatus(Student.Status.INTERRUPTED);
            System.out.println("Обучението е прекъснато.");
        }, () -> System.out.println("Няма такъв студент."));
    }

    public void resumeStudent(int fn) {
        findStudentByFacultyNumber(fn).ifPresentOrElse(student -> {
            if (student.getStatus() != Student.Status.INTERRUPTED) {
                System.out.println("Студентът не е прекъснал.");
                return;
            }
            student.setStatus(Student.Status.ACTIVE);
            System.out.println("Студентът е възстановен.");
        }, () -> System.out.println("Няма такъв студент."));
    }

    public void enrollInDiscipline(int fn, String courseName) {
        findStudentByFacultyNumber(fn).ifPresentOrElse(student -> {
            if (student.getStatus() != Student.Status.ACTIVE) {
                System.out.println("Студентът не е активен.");
                return;
            }

            Discipline discipline = curriculum.findDiscipline(student.getProgram(), student.getYear(), courseName);
            if (discipline == null) {
                System.out.println("Тази дисциплина не съществува за тази специалност и курс.");
                return;
            }

            student.enrollInDiscipline(discipline);
            System.out.println("Записан е по дисциплината.");
        }, () -> System.out.println("Няма такъв студент."));
    }

    public void addGrade(int fn, String courseName, double grade) {
        findStudentByFacultyNumber(fn).ifPresentOrElse(student -> {
            Discipline discipline = curriculum.findDiscipline(student.getProgram(), student.getYear(), courseName);
            if (discipline == null || !student.isEnrolledIn(discipline)) {
                System.out.println("Студентът не е записан по тази дисциплина.");
                return;
            }

            student.addGrade(discipline, grade);
            System.out.println("Оценката е добавена.");
        }, () -> System.out.println("Няма такъв студент."));
    }

    public void printStudentReport(int fn) {
        findStudentByFacultyNumber(fn).ifPresentOrElse(student -> {
            System.out.println("\n" + student);

            List<String> passed = new ArrayList<>();
            List<String> failed = new ArrayList<>();
            double total = 0;
            int count = 0;

            for (Discipline d : student.getEnrolledDisciplines()) {
                Double grade = student.getGrades().get(d);
                if (grade != null && grade >= 3.0) {
                    passed.add(d.getName() + " - " + grade);
                    total += grade;
                } else {
                    failed.add(d.getName() + " - " + (grade == null ? "Няма оценка" : grade));
                    total += 2;
                }
                count++;
            }

            System.out.println("\n Взети изпити:");
            passed.forEach(c -> System.out.println("  - " + c));

            System.out.println("\n Невзети/неположени изпити:");
            failed.forEach(c -> System.out.println("  - " + c));

            double average = count > 0 ? total / count : 0;
            System.out.printf("\n Среден успех: %.2f%n\n", average);
        }, () -> System.out.println("Няма такъв студент."));
    }

    public void printAllStudents(String program, int year) {
        List<Student> filtered = students.stream()
                .filter(s -> (program == null || s.getProgram().equalsIgnoreCase(program)) &&
                        (year == 0 || s.getYear() == year))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("Няма намерени студенти.");
            return;
        }

        filtered.forEach(System.out::println);
    }

    public void generateProtocol(String courseName, int year, String program) {
        Discipline discipline = curriculum.findDiscipline(program, year, courseName);
        if (discipline == null) {
            System.out.println("Дисциплината не съществува.");
            return;
        }

        List<Student> list = students.stream()
                .filter(s -> s.isEnrolledIn(discipline))
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            System.out.println("Няма записани студенти по тази дисциплина.");
            return;
        }

        System.out.println("Протокол за дисциплина: " + discipline.getName());
        list.forEach(s -> System.out.println(s.getFacultyNumber() + " - " + s.getName()));
    }

    public void printAllCourses(String program, int year) {
        List<Discipline> courses = curriculum.getCoursesFor(program, year);
        if (courses.isEmpty()) {
            System.out.println("Няма дисциплини.");
            return;
        }

        System.out.println("Курс " + year + ":");
        courses.forEach(d -> System.out.println(" - " + d.getName() + " (" + d.getType() + ")"));
    }

    public Optional<Student> findStudentByFacultyNumber(int fn) {
        return students.stream().filter(s -> s.getFacultyNumber() == fn).findFirst();
    }

    public List<Student> getStudents() {
        return students;
    }
}
