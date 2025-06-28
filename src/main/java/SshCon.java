import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SshCon {

    private static final String HOST = "3.21.43.168";
    private static final String USER = "ec2-user";
    private static final String PRIVATE_KEY = "C:/Users/was/WilsonWAS/newformat";
    private static final int PORT = 22;

    private static final String DATABASE_HOST = "ds-mysql8.cluster-clb2b7knmca4.us-east-2.rds.amazonaws.com";
    private static final int DATABASE_PORT = 3307;
    private static final String DATABASE_USERNAME = "trainee_readonly";
    private static final String DATABASE_PASSWORD = "k21jBncdIP7iWIk7Dqti";

    public static void main(String[] args) throws JSchException {

        JSch jsch = new JSch();
        jsch.addIdentity(PRIVATE_KEY);

        Session session = jsch.getSession(USER, HOST, PORT);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        System.out.println("Connected to Session");

//        int port = session.setPortForwardingL(0, DATABASE_HOST, DATABASE_PORT);
        int port = 3306;

//        String databaseUrl = "jdbc:mariadb://" + DATABASE_HOST + ":" + port + "/addons";
        String databaseUrl = "jdbc:aws-wrapper:mysql://" + DATABASE_HOST + ":" + port + "/addons";
        try {
            Connection connection = DriverManager.getConnection(databaseUrl, DATABASE_USERNAME, DATABASE_PASSWORD);
            System.out.println("Connected to Database successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
