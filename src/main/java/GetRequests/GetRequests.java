package GetRequests;

import Api.TransactionDetailsApi;
import Data.Configuration;
import DatabaseManager.DatabaseManager;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TssV2TransactionsGet200Response;
import Tunnel.SSHTunnel;
import com.cybersource.authsdk.core.MerchantConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GetRequests {

    public static String getBatchStatus(String transaction_id) throws SQLException {

        String sql = "SELECT batch_status FROM payments WHERE transaction_id = " + transaction_id; // For SQL Server

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("batch_status"); // returns the value as a String
            } else {
                return ""; // no rows found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getStatus(String transaction_id) throws SQLException {

        String sql = "SELECT status FROM payments WHERE transaction_id = " + transaction_id; // For SQL Server

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("status"); // returns the value as a String
            } else {
                return ""; // no rows found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getLatestAuthID() throws SQLException{

        String sql = "SELECT transaction_id FROM payments WHERE status = 'AuthInProgress' or status = 'AuthCompleted' or status = 'AuthFailed' and transaction_id IS NOT NULL AND transaction_id != '' AND terminal_id = 'PDEBO5RC' ORDER BY id DESC LIMIT 1";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("transaction_id"); // returns the value as a String
            } else {
                return ""; // no rows found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    public static String getLatestCaptureID() throws SQLException{

        String sql = "SELECT transaction_id FROM payments WHERE status = 'CaptureInProgress' or status = 'CaptureCompleted' or status = 'CaptureFailed' and transaction_id IS NOT NULL AND transaction_id != '' AND terminal_id = 'PDEBO5RC' ORDER BY id DESC LIMIT 1";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("transaction_id"); // returns the value as a String
            } else {
                return ""; // no rows found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

}

