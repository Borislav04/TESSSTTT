import java.io.Serializable;
import java.util.Objects;

/**
 * Клас, представляващ учебна дисциплина.
 * Включва име, тип (задължителна или избираема), курс и кредити.
 */
public class Discipline implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String type; // "задължителна" или "избираема"
    private int year;
    private int credits;

    public Discipline(String name, String type, int year) {
        this(name, type, year, type.equals("избираема") ? 3 : 0);
    }

    public Discipline(String name, String type, int year, int credits) {
        this.name = name;
        this.type = type;
        this.year = year;
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, курс %d, кредити: %d)", name, type, year, credits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discipline)) return false;
        Discipline that = (Discipline) o;
        return year == that.year &&
               Objects.equals(name, that.name) &&
               Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), type.toLowerCase(), year);
    }
}
