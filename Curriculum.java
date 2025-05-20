import java.io.Serializable;
import java.util.*;

/**
 * Клас, съдържащ учебни планове за различни специалности и курсове.
 * Използва се за валидация и извличане на дисциплини за дадена програма.
 */
public class Curriculum implements Serializable {
    private static final long serialVersionUID = 1L;

    // Пример: "SI" -> {1 -> [Discipline1, Discipline2], 2 -> [Discipline3, ...]}
    private Map<String, Map<Integer, List<Discipline>>> programPlans;

    public Curriculum() {
        programPlans = new HashMap<>();

        // Специалност "SI"
        Map<Integer, List<Discipline>> siCourses = new HashMap<>();
        siCourses.put(1, List.of(
                new Discipline("Програмиране", "задължителна", 1),
                new Discipline("Математика1", "задължителна", 1)
        ));
        siCourses.put(2, List.of(
                new Discipline("ООП", "задължителна", 2),
                new Discipline("Алгоритми", "задължителна", 2),
                new Discipline("Бази данни", "избираема", 2, 3)
        ));
        programPlans.put("SI", siCourses);

        // Специалност "Cyber"
        Map<Integer, List<Discipline>> cyberCourses = new HashMap<>();
        cyberCourses.put(1, List.of(
                new Discipline("Математика", "задължителна", 1),
                new Discipline("Въведение в киберсигурността", "задължителна", 1)
        ));
        programPlans.put("Cyber", cyberCourses);
    }

    /**
     * Връща всички дисциплини за дадена специалност и курс.
     */
    public List<Discipline> getDisciplinesFor(String program, int year) {
        return programPlans.getOrDefault(program, new HashMap<>())
                           .getOrDefault(year, Collections.emptyList());
    }

    /**
     * Проверява дали съществува дисциплина за дадената специалност, курс и име.
     */
    public Optional<Discipline> findDiscipline(String program, int year, String disciplineName) {
        return getDisciplinesFor(program, year).stream()
                .filter(d -> d.getName().equalsIgnoreCase(disciplineName))
                .findFirst();
    }

    /**
     * Връща всички дисциплини по години за дадена специалност.
     */
    public Map<Integer, List<Discipline>> getAllDisciplinesForProgram(String program) {
        return programPlans.getOrDefault(program, new HashMap<>());
    }
}
