import java.sql.*;

public class SearchUnsafe {
    private static final String URL =
            "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "secret";

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java SearchUnsafe <name>");
            System.exit(1);
        }

        String sql = "SELECT id, name, program, gpa FROM student WHERE name LIKE '%"
                + args[0] + "%'";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            stmt.setQueryTimeout(10);

            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    System.out.printf(
                            "Student #%d · %s · program %s · GPA %.2f%n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("program"),
                            rs.getDouble("gpa")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            System.exit(1);
        }
    }
}