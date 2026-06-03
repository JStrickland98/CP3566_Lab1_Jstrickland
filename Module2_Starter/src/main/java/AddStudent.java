import java.sql.*;
import java.time.Instant;

public class AddStudent {
    private static final String URL = "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "secret";

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: AddStudent <name> <program> <gpa>");
            System.exit(1);
        }

        // Validate inputs before opening a database connection
        String name = args[0].trim();
        if (name.length() < 1 || name.length() > 80) {
            logError("name must be 1-80 characters after trimming");
            System.exit(1);
            return;
        }

        String program = args[1].trim().toUpperCase();
        if (!program.matches("^[A-Z0-9]{2,12}$")) {
            logError("program must match regex ^[A-Z0-9]{2,12}$ (2-12 chars, A-Z or 0-9)");
            System.exit(1);
            return;
        }

        double gpa;
        try {
            gpa = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            logError("gpa must be a number, got \"" + args[2] + "\"");
            System.exit(1);
            return;
        }

        // Accept gpa with two decimals between 0.00 and 4.00 inclusive
        if (gpa < 0.0 || gpa > 4.0) {
            logError("gpa must be between 0.00 and 4.00 inclusive, got " + gpa);
            System.exit(1);
            return;
        }

        String sql = "INSERT INTO student (name, program, gpa) VALUES (?, ?, ?)";

        DriverManager.setLoginTimeout(5);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, program);
            ps.setDouble(3, gpa);
            ps.setQueryTimeout(10);
            int rows = ps.executeUpdate();

            if (rows == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int newId = keys.getInt(1);

                        System.out.printf("Student #%d \u00B7 %s \u00B7 program %s \u00B7 GPA %.2f added%n",
                                newId, name, program, gpa);
                    }
                }
            }

        } catch (SQLException e) {
            logError(e.getMessage());
            System.exit(1);
        }
}

private static void logError(String message) {
    String timestamp = Instant.now().toString();
    System.err.printf("%s | ERROR | AddStudent | message=%s%n",
            timestamp, message);
}

}