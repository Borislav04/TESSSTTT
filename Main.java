import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        University university = fileManager.loadUniversity();
        CommandHandler handler = new CommandHandler(university, fileManager);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Добре дошли в системата! Напишете 'help' за списък с команди.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            if (input.equalsIgnoreCase("exit")) {
                fileManager.saveUniversity(university);
                System.out.println("Довиждане!");
                break;
            }

            handler.handleCommand(input);
        }
    }
}
