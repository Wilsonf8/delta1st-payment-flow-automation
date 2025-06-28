package DeltaFirstTransactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PpkTest {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://ds-mysql8.cluster-clb2b7knmca4.us-east-2.rds.amazonaws.com:3306/mydatabase"; // Example URL
        String username = "trainee_readonly";
        String password = "k21jBncdIP7iWIk7Dqti"; // Or use private key authentication

        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password); // If not using key-based auth exclusively
        props.setProperty("useSSL", "true");
        props.setProperty("requireSSL", "true"); // Enforce SSL

        // Configure client certificate and private key
        System.setProperty("javax.net.ssl.keyStore", "C:/Users/was/WilsonWAS/04partners-DS.ppk");
        System.setProperty("javax.net.ssl.keyStorePassword", "keystore_password");

        // Configure truststore (if needed for server certificate validation)
//        System.setProperty("javax.net.ssl.trustStore", "/path/to/truststore.jks");
//        System.setProperty("javax.net.ssl.trustStorePassword", "truststore_password");

        Connection connection = null;
        try {
            System.out.println("1");
            connection = DriverManager.getConnection(jdbcUrl, props);
            System.out.println("Successfully connected to the database!");
            // Perform database operations
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
