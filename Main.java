import java.util.Scanner;

/**
 * Главен клас, стартиращ конзолното приложение за управление на университетска система.
 */
public class Main {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        University university = fileManager.loadUniversity();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printHelp();
            System.out.print("> ");
            try {
                String input = scanner.nextLine().trim();
                String[] tokens = input.split("\\s+");

                if (tokens.length == 0 || tokens[0].isEmpty()) continue;

                String command = tokens[0].toLowerCase();

                switch (command) {
                    case "enroll" -> handleEnroll(tokens, university);
                    case "advance" -> handleAdvance(tokens, university);
                    case "change" -> handleChange(tokens, university);
                    case "graduate" -> handleGraduate(tokens, university);
                    case "interrupt" -> handleInterrupt(tokens, university);
                    case "resume" -> handleResume(tokens, university);
                    case "enrollin" -> handleEnrollIn(tokens, university);
                    case "addgrade" -> handleAddGrade(tokens, university);
                    case "print" -> handlePrint(tokens, university);
                    case "printall" -> handlePrintAll(tokens, university);
                    case "protocol" -> handleProtocol(tokens, university);
                    case "report" -> handleReport(tokens, university);
                    case "export" -> handleExport(tokens, university, fileManager);
                    case "courses" -> handleCourses(tokens, university);
                    case "help" -> printHelp();
                    case "exit" -> {
                        fileManager.saveUniversity(university);
                        System.out.println("Довиждане!");
                        return;
                    }
                    default -> System.out.println("Невалидна команда. Напишете 'help' за помощ.");
                }
            } catch (Exception e) {
                System.out.println("Грешка: " + e.getMessage());
            }
        }
    }

    private static void handleEnroll(String[] parts, University university) {
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

    private static void handleAdvance(String[] parts, University university) {
        if (parts.length < 2) {
            System.out.println("Използване: advance <фн>");
            return;
        }
        university.advanceStudent(Integer.parseInt(parts[1]));
    }

    private static void handleChange(String[] parts, University university) {
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
            default -> System.out.println("Невалидна опция.");
        }
    }

    private static void handleGraduate(String[] parts, University university) {
        if (parts.length < 2) {
            System.out.println("Използване: graduate <фн>");
            return;
        }
        university.graduateStudent(Integer.parseInt(parts[1]));
    }

    private static void handleInterrupt(String[] parts, University university) {
        if (parts.length < 2) {
            System.out.println("Използване: interrupt <фн>");
            return;
        }
        university.interruptStudent(Integer.parseInt(parts[1]));
    }

    private static void handleResume(String[] parts, University university) {
        if (parts.length < 2) {
            System.out.println("Използване: resume <фн>");
            return;
        }
        university.resumeStudent(Integer.parseInt(parts[1]));
    }

    private static void handleEnrollIn(String[] parts, University university) {
        if (parts.length < 3) {
            System.out.println("Използване: enrollin <фн> <дисциплина>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        String courseName = parts[2];
        university.enrollInDiscipline(fn, courseName);
    }

    private static void handleAddGrade(String[] parts, University university) {
        if (parts.length < 4) {
            System.out.println("Използване: addgrade <фн> <дисциплина> <оценка>");
            return;
        }
        int fn = Integer.parseInt(parts[1]);
        String courseName = parts[2];
        double grade = Double.parseDouble(parts[3]);
        university.addGrade(fn, courseName, grade);
    }

    private static void handlePrint(String[] parts, University university) {
        if (parts.length < 2) {
            System.out.println("Използване: print <фн>");
            return;
        }
        university.printStudentReport(Integer.parseInt(parts[1]));
    }

    private static void handlePrintAll(String[] parts, University university) {
        if (parts.length == 1) {
            university.printAllStudents(null, 0);
        } else if (parts.length == 2) {
            university.printAllStudents(parts[1], 0);
        } else {
            university.printAllStudents(parts[1], Integer.parseInt(parts[2]));
        }
    }

    private static void handleProtocol(String[] parts, University university) {
        if (parts.length < 2) {
            System.out.println("Използване: protocol <дисциплина>");
            return;
        }
        university.generateProtocol(parts[1]);
    }

    private static void handleReport(String[] parts, University university) {
        if (parts.length < 2) {
            System.out.println("Използване: report <фн>");
            return;
        }
        university.printStudentReport(Integer.parseInt(parts[1]));
    }

    private static void handleExport(String[] parts, University university, FileManager fileManager) {
        String filename = parts.length > 1 ? parts[1] : "students_report.txt";
        fileManager.exportToTextFile(filename, university);
    }

    private static void handleCourses(String[] parts, University university) {
        if (parts.length < 2) {
            System.out.println("Използване: courses <специалност>");
            return;
        }
        university.printAllCourses(parts[1]);
    }

    private static void printHelp() {
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
