import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListStudents {
    private static final String URL  =
            "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "secret";

    public static void main(String[] args) {
        String sql = "SELECT id, name, program, gpa FROM student ORDER BY id";

        DriverManager.setLoginTimeout(5);

        long start = System.nanoTime();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setQueryTimeout(10);

            try (ResultSet rs = ps.executeQuery()) {

                System.out.printf("%3s | %-16s | %-4s | %5s%n", "id", "name", "program", "gpa");
                System.out.println("---+------------------+------+-----");

                int count = 0;

                while (rs.next()) {
                    count++;

                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String program = rs.getString("program");
                    double gpa = rs.getDouble("gpa");

                    System.out.printf("%3d | %-16s | %-4s | %5.2f%n",
                            id, name, program, gpa);
                }

                long end = System.nanoTime();
                long elapsedMs = (end - start) / 1_000_000;
                System.out.printf("%d listed in %d ms%n", count, elapsedMs);;
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
