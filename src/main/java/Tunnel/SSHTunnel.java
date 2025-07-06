package Tunnel;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;

public class SSHTunnel {
    public static void openTunnel() {
//        String sshHost = "3.21.43.168";
        String sshHost = "18.224.66.91";
        String sshUser = "ec2-user";
        int sshPort = 22;
//        String privateKeyPath = "C:/Users/was/WilsonWAS/newformat"; // Use .pem instead if needed
        String privateKeyPath = "C:/Wilson/SSCreds/04partners-SS-new.ppk"; // Use .pem instead if needed
        int localPort = 5000;
//        String remoteHost = "ds-mysql8.cluster-clb2b7knmca4.us-east-2.rds.amazonaws.com";
        String remoteHost = "ss-mysql8.cluster-clb2b7knmca4.us-east-2.rds.amazonaws.com";
        int remotePort = 3306;

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

