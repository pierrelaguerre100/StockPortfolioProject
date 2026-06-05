
import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            if (connection != null) {

                System.out.println(
                        "Database connection successful!");

            } else {

                System.out.println(
                        "Database connection failed!");

            }

        } catch (Exception e) {

            System.out.println(
                    "Error connecting to database.");

            e.printStackTrace();

        }

    }
}