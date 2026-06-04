
import java.sql.*;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/StockPortfolioDB",
                "root",
                "password");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if(instance == null) instance = new DatabaseConnection();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
