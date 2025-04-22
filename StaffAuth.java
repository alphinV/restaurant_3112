import java.util.Scanner;

public class StaffAuth {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "pass123";

    public static boolean login(Scanner scanner) {
        System.out.print("Enter staff username: ");
        String user = scanner.nextLine();
        System.out.print("Enter password: ");
        String pass = scanner.nextLine();

        return user.equals(USERNAME) && pass.equals(PASSWORD);
    }
}
