import java.util.Scanner;

/**
 * Клас, отговарящ за обработката на потребителски команди в системата.
 */
public class CommandHandler {
    private University university;
    private FileManager fileManager;

    public CommandHandler(University university, FileManager fileManager) {
        this.university = university;
        this.fileManager = fileManager;
    }

    /**
     * Основният метод, който обработва командата, подадена от потребителя.
     *
     * @param input Входен низ с команда и параметри.
     */
    public void handleCommand(String input) {
        String[] parts = input.split("\\s+");
        String command = parts[0].toLowerCase();

        switch (command) {
            case "enroll" -> handleEnroll(parts);
            case "advance" -> handleAdvance(parts);
            case "change" -> handleChange(parts);
            case "graduate" -> handleGraduate(parts);
            case "interrupt" -> handleInterrupt(parts);
            case "resume" -> handleResume(parts);
            case "enrollin" -> handleEnrollIn(parts);
            case "addgrade" -> handleAddGrade(parts);
            case "print" -> handlePrint(parts);
            case "printall" -> handlePrintAll(parts);
            case "protocol" -> handleProtocol(parts);
            case "report" -> handleReport(parts);
            case "export" -> handleExport(parts);
            case "courses" -> handleCourses(parts);
            case "help" -> printHelp();
            default -> System.out.println("Невалидна команда. Използвайте 'help' за помощ.");
        }
    }

    private void handleEnroll(String[] parts) {
        if (parts.length < 5) {
            System.out.println("Използване: enroll <име> <фн> <специалност> <група>");
            return;
        }
        String name = parts[1];
        int fn = Integer.parseInt(parts[2]);
        String program = parts[3];
        String group = parts[4];
        university.enrollStudent(name, fn, program, group);
    }

    private void handleAdvance(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Използване: advance <фн>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        university.advanceStudent(fn);
    }

    private void handleChange(String[] parts) {
        if (parts.length < 4) {
            System.out.println("Използване: change <фн> <program|group|year> <стойност>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        String option = parts[2];
        String value = parts[3];

        switch (option) {
            case "program" -> university.changeStudentProgram(fn, value);
            case "group" -> university.findStudentByFacultyNumber(fn).ifPresent(s -> s.setGroup(value));
            case "year" -> {
                int year = Integer.parseInt(value);
                university.findStudentByFacultyNumber(fn).ifPresent(s -> s.setYear(year));
            }
            default -> System.out.println("Невалидна опция за промяна!");
        }
    }

    private void handleGraduate(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Използване: graduate <фн>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        university.graduateStudent(fn);
    }

    private void handleInterrupt(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Използване: interrupt <фн>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        university.interruptStudent(fn);
    }

    private void handleResume(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Използване: resume <фн>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        university.resumeStudent(fn);
    }

    private void handleEnrollIn(String[] parts) {
        if (parts.length < 3) {
            System.out.println("Използване: enrollin <фн> <дисциплина>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        String courseName = parts[2];
        university.enrollInCourse(fn, courseName);
    }

    private void handleAddGrade(String[] parts) {
        if (parts.length < 4) {
            System.out.println("Използване: addgrade <фн> <дисциплина> <оценка>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        String courseName = parts[2];
        double grade = Double.parseDouble(parts[3]);
        university.addGrade(fn, courseName, grade);
    }

    private void handlePrint(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Използване: print <фн>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        university.printStudentReport(fn);
    }

    private void handlePrintAll(String[] parts) {
        if (parts.length == 1) {
            university.printAllStudents(null, 0);
        } else if (parts.length == 2) {
            university.printAllStudents(parts[1], 0);
        } else {
            university.printAllStudents(parts[1], Integer.parseInt(parts[2]));
        }
    }

    private void handleProtocol(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Използване: protocol <дисциплина>");
            return;
        }
        String courseName = parts[1];
        university.generateProtocol(courseName);
    }

    private void handleReport(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Използване: report <фн>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        university.printStudentReport(fn);
    }

    private void handleExport(String[] parts) {
        String filename = parts.length > 1 ? parts[1] : "students_report.txt";
        fileManager.exportToTextFile(filename, university);
    }

    private void handleCourses(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Използване: courses <специалност>");
            return;
        }
        String program = parts[1];
        university.printAllCourses(program);
    }

    private void printHelp() {
        System.out.println("\nДостъпни команди:");
        System.out.println("enroll <име> <фн> <специалност> <група>");
        System.out.println("advance <фн>");
        System.out.println("change <фн> <program|group|year> <стойност>");
        System.out.println("graduate <фн>");
        System.out.println("interrupt <фн>");
        System.out.println("resume <фн>");
        System.out.println("enrollin <фн> <дисциплина>");
        System.out.println("addgrade <фн> <дисциплина> <оценка>");
        System.out.println("print <фн>");
        System.out.println("printall [специалност] [курс]");
        System.out.println("protocol <дисциплина>");
        System.out.println("report <фн>");
        System.out.println("export [файл]");
        System.out.println("courses <специалност>");
        System.out.println("help");
        System.out.println("exit\n");
    }
}
