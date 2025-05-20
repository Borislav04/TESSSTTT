import java.io.*;
import java.util.Set;

/**
 * Клас за управление на файловите операции:
 * - запис и зареждане на данните от файл
 * - експорт в текстов файл
 */
public class FileManager {
    private static final String DATA_FILE = "university.dat";

    /**
     * Записва обекта University във файл чрез сериализация.
     *
     * @param university обектът, който ще бъде записан
     */
    public void saveUniversity(University university) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(university);
            System.out.println("Данните са запазени успешно във файл: " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("Грешка при запис на данните: " + e.getMessage());
        }
    }

    /**
     * Зарежда обект University от файл чрез десериализация.
     * В случай на несъвместимост или грешка – връща нова инстанция.
     *
     * @return обект University
     */
    public University loadUniversity() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("Не е намерен файл с данни. Създава се нова база данни.");
            return new University();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            University university = (University) ois.readObject();
            System.out.println("Данните са заредени успешно от файл: " + DATA_FILE);
            return university;
        } catch (InvalidClassException e) {
            System.err.println("Несъвместимост на класовете при зареждане. Създава се нова база.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Грешка при зареждане на данните: " + e.getMessage());
        }
        return new University();
    }

    /**
     * Експортира списък на студентите и техните дисциплини в текстов файл.
     *
     * @param filename   име на файла
     * @param university обектът, който съдържа данните
     */
    public void exportToTextFile(String filename, University university) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("=== Списък на студентите ===");
            for (Student student : university.getStudents()) {
                writer.println(student);
                Set<Discipline> disciplines = student.getEnrolledDisciplines();
                for (Discipline d : disciplines) {
                    writer.println("  - " + d.getName());
                }
            }
            System.out.println("Данните са експортирани в " + filename);
        } catch (IOException e) {
            System.err.println("Грешка при експорт: " + e.getMessage());
        }
    }
}
