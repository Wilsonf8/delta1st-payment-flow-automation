import Data.Configuration;
import DatabaseManager.DatabaseManager;
import Frame.GridLabelFrame;
import Frame.MainFrame;
import Frame.PaymentFlowFrame;
import Tunnel.SSHTunnel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import samples.TransactionDetails.TransactionDetailsFromRequestId;
import samples.TransactionSearch.latestRequestIdForMerchant;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static GetRequests.GetRequests.*;

public class runner {

    public static Connection conn;

    public static PaymentFlowFrame frame = new PaymentFlowFrame();

    public static String authTerminalID;

    public static String captureTerminalID;

    public static void main(String[] args) throws Exception{

        // Open Panel
//        MainFrame frame = new MainFrame();
        frame.setVisible(true);

        // Connect to Database
        try {
            SSHTunnel.openTunnel();
            DatabaseManager.initializeDataSource();
            conn = DatabaseManager.getConnection();
            System.out.println("Connected to Tunnel and DB");
        }catch (Exception e){
            e.printStackTrace();
        }

//        System.out.println(getBatchStatus("7513824934856358803813"));
//        conn.close();
//
//        System.out.println(getStatus("7513824934856358803813"));
//        frame.updateLabel(0, 0, getStatus("7513824934856358803813"));

//        System.out.println(getLatestAuthID());

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService exec2 = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService exec3 = Executors.newSingleThreadScheduledExecutor();

        exec.scheduleAtFixedRate(() -> {
            try {
                authTerminalID = getLatestAuthID();
                conn.close();
                frame.setCellText(0, 0, authTerminalID);
                conn.close();
                frame.setCellText(1, 0, getBatchStatus(authTerminalID));
                conn.close();
                frame.setCellText(2, 0, getStatus(authTerminalID));
                conn.close();

                captureTerminalID = getLatestCaptureID();
                conn.close();
                frame.setCellText(0, 1, captureTerminalID);
                conn.close();
                frame.setCellText(1, 1, getBatchStatus(captureTerminalID));
                conn.close();
                frame.setCellText(2, 1, getStatus(captureTerminalID));
                conn.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, 0, 100, TimeUnit.MILLISECONDS);

//        exec2.scheduleAtFixedRate(() -> {
//            try {
//                frame.setCellText(1, 0, getBatchStatus(authTerminalID));
//                System.out.println(getBatchStatus(authTerminalID));
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }, 0, 100, TimeUnit.MILLISECONDS);
//
//        exec3.scheduleAtFixedRate(() -> {
//            try {
//                frame.setCellText(2, 0, getStatus(authTerminalID));
//                System.out.println(getStatus(authTerminalID));
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }, 0, 100, TimeUnit.MILLISECONDS);


    }

}
