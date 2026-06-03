import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class FindStudent {
    private static final String URL = "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "secret";

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java FindStudent <positive integer id>");
            System.exit(1);
        }

        int id;
        try {
            id = Integer.parseInt(args[0]);

            if (id <= 0) {
                System.err.println("ID must be a positive integer");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.err.println("ID must be a positive integer");
            System.exit(1);
            return;
        }

        String sql = "SELECT id, name, program, gpa FROM student WHERE id = ?";
        DriverManager.setLoginTimeout(5);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setQueryTimeout(10);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int sid = rs.getInt("id");
                    String name = rs.getString("name");
                    String program = rs.getString("program");
                    double gpa = rs.getDouble("gpa");

                    System.out.printf("Student #%d · %s · program %s · GPA %.2f%n",
                            sid, name, program, gpa);
                } else {
                    System.out.println("No student with ID " + id);
                }
            }

        } catch (SQLException e) {
            String timestamp = Instant.now().toString();
            System.err.printf("%s | ERROR | FindStudent | message=%s%n",
                    timestamp, e.getMessage());
    }
}

}
