package DeltaFirstTransactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ChatGPT {

    public static void main(String[] args) throws SQLException {

        String url  = "jdbc:mysql://localhost:3307/addons"
                + "?useSSL=true&requireSSL=true&serverTimezone=UTC";
        String user = "trainee_readonly";
        String pass = "k21jBncdIP7iWIk7Dqti";                    // same as HeidiSQL

        Connection connection = null;
        Connection conn;

        {
            try {
                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
