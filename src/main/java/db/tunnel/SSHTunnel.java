package db.tunnel;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHTunnel {
    public static void openTunnel() {
        String sshHost = System.getenv("SSH_HOST");
        String sshUser = System.getenv("SSH_USER");
        int sshPort = Integer.parseInt(System.getenv("SSH_PORT"));

        String privateKeyPath = System.getenv("PRIVATE_KEY_PATH");
        int localPort = Integer.parseInt(System.getenv("LOCAL_PORT"));
//        String remoteHost = "ds-mysql8.cluster-clb2b7knmca4.us-east-2.rds.amazonaws.com";
        String remoteHost = System.getenv("REMOTE_HOST");;
        int remotePort = Integer.parseInt(System.getenv("REMOTE_PORT"));

        try {
            JSch jsch = new JSch();
            jsch.addIdentity(privateKeyPath);
            Session session = jsch.getSession(sshUser, sshHost, sshPort);

            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(localPort, remoteHost, remotePort);
            System.out.println("SSH Tunnel established");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

