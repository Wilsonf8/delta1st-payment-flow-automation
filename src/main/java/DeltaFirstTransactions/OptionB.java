package DeltaFirstTransactions;

import com.jcraft.jsch.*;

import java.sql.*;

public class OptionB {
    public static void main(String[] args) throws Exception {

        /* ---------- 1. open SSH tunnel ---------- */
        JSch jsch = new JSch();
        // JSch understands PuTTY keys since 0.1.55; if it fails, convert to OpenSSH with PuTTYgen
        jsch.addIdentity("C:/Users/was/WilsonWAS/04partners-DS.ppk");

        System.out.println("1");

        Session ssh = jsch.getSession("ec2-user", "3.21.43.168", 22);
        ssh.setConfig("StrictHostKeyChecking", "no");  // for quick demos only
        ssh.connect(5_000);                            // 5 s timeout

        // Forward local 3307 → RDS-endpoint:3306
        int localPort = 3307;
        ssh.setPortForwardingL(localPort,
                "ds-mysql8.cluster-dlb2b7knmc4.us-east-2.rds.amazonaws.com", 3306);

        /* ---------- 2. JDBC through the tunnel ---------- */
        String url  = "jdbc:mysql://localhost:" + localPort + "/addons"
                + "?useSSL=true&requireSSL=true&serverTimezone=UTC";
        try (Connection conn = DriverManager.getConnection(
                url, "trainee_readonly", "k21jBncdIP7iWIk7Dqti")) {

            // … work with the DB …

        } finally {
            ssh.disconnect();   // close tunnel
        }
    }
}
