import java.sql.*;
import java.time.Instant;

public class UpdateGpa {
    private static final String URL =
            "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "secret";

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: java UpdateGpa <id> <gpa>");
            System.exit(1);
        }

        int id;
        try {
            id = Integer.parseInt(args[0]);
            if (id <= 0) {
                fail("id must be a positive integer, got \"" + args[0] + "\"");
            }
        } catch (NumberFormatException e) {
            fail("id must be a positive integer, got \"" + args[0] + "\"");
            return;
        }

        double gpa;
        try {
            gpa = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            fail("gpa must be a number, got \"" + args[1] + "\"");
            return;
        }

        if (gpa < 0.0 || gpa > 4.0) {
            fail("gpa must be between 0.00 and 4.00 inclusive, got " + gpa);
        }

        String sql = "UPDATE student SET gpa = ? WHERE id = ?";

        DriverManager.setLoginTimeout(5);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, gpa);
            ps.setInt(2, id);
            ps.setQueryTimeout(10);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.printf("No update · No student with id %d · %d rows changed%n", id, rows);
            } else if (rows == 1) {
                System.out.printf("Student #%d · GPA set to %.2f · %d row changed%n", id, gpa, rows);
            }

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    private static void fail(String message) {
        logError(message);
        System.exit(1);
    }

    private static void logError(String message) {
        String timestamp = Instant.now().toString();
        System.err.printf("%s | ERROR | UpdateGpa | message=%s%n",
                timestamp, message);
    }
}
