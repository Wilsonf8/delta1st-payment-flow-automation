////import DatabaseManager.DatabaseManager;
////import Tunnel.SSHTunnel;
////
////import javax.swing.*;
////import javax.swing.table.DefaultTableModel;
////import java.awt.*;
////import java.sql.*;
////import java.util.*;
////import java.util.List;
////import java.util.concurrent.*;
////
////public class PaymentsViewer extends JFrame {
////    private final DefaultTableModel tableModel;
////    private final ScheduledExecutorService scheduler;
////    private volatile List<List<String>> currentSnapshot = Collections.emptyList(); // last view
////
////    public PaymentsViewer() {
////        super("Payments Monitor");
////        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        setSize(400, 300);
////        setLayout(new BorderLayout());
////
////        tableModel = new DefaultTableModel(new String[]{"Transaction ID", "Status", "Batch Status"}, 0);
////
////        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
////
////        scheduler = Executors.newSingleThreadScheduledExecutor();
////        scheduler.scheduleAtFixedRate(this::refreshData, 0, 5, TimeUnit.SECONDS);
////
////        setVisible(true);
////    }
////
////    /** Polls DB, compares with cached snapshot, updates UI only if data changed. */
////    private void refreshData() {
////        try {
////            List<List<String>> fresh = loadSnapshot();
////            if (sameSnapshot(fresh, currentSnapshot)) {
////                return;                        // nothing new → skip repaint
////            }
////            currentSnapshot = fresh;           // cache new view
////
////            SwingUtilities.invokeLater(() -> {
////                tableModel.setRowCount(0);
////                fresh.forEach(row -> tableModel.addRow(row.toArray()));
////            });
////        } catch (SQLException ex) {
////            ex.printStackTrace();
////            SwingUtilities.invokeLater(() ->
////                    JOptionPane.showMessageDialog(this, ex.getMessage(),
////                            "DB error", JOptionPane.ERROR_MESSAGE));
////        }
////    }
////
////    /** Reads status + batch_status from DB into a deterministic list of rows. */
////    private List<List<String>> loadSnapshot() throws SQLException {
//////        String sql = "SELECT status, batch_status FROM payments WHERE terminal_id = 'PDEBO5RC' ORDER BY id"; // ensure stable order
////        String sql = "SELECT status, batch_status, transaction_id FROM payments WHERE terminal_id = 'J4VTI8RG' ORDER BY id";
////
////
////        try (Connection c = DatabaseManager.getConnection();
////             Statement st = c.createStatement();
////             ResultSet rs = st.executeQuery(sql)) {
////
////            List<List<String>> rows = new ArrayList<>();
////            while (rs.next()) {
////                rows.add(Arrays.asList(
////                        rs.getString("transaction_id"),
////                        rs.getString("status"),
////                        rs.getString("batch_status")
////                ));
////            }
////
////            return rows;
////        }
////    }
////
////    /** Cheap deep-equality test: same row count and same cell values. */
////    private boolean sameSnapshot(List<List<String>> a, List<List<String>> b) {
////        if (a.size() != b.size()) return false;
////        for (int i = 0; i < a.size(); i++) {
////            if (!a.get(i).equals(b.get(i))) return false;
////        }
////        return true;
////    }
////
////    /** Graceful shutdown */
////    private void stopScheduler() {
////        if (!scheduler.isShutdown()) scheduler.shutdown();
////    }
////
////    public static void main(String[] args) {
////        SSHTunnel.openTunnel();
////        DatabaseManager.initializeDataSource();
////        PaymentsViewer viewer = new PaymentsViewer();
////        Runtime.getRuntime().addShutdownHook(new Thread(viewer::stopScheduler));
////    }
////}
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.io.FileInputStream;
//import java.sql.*;
//import java.util.*;
//import java.util.List;
//import java.util.concurrent.*;
//
//import ApiClient;
//import Api.TransactionDetailsApi;
//import DatabaseManager.DatabaseManager;
//import Model.PtsV2TransactionDetailsGet200Response;
//import com.cybersource.authsdk.core.MerchantConfig;
//
//public class PaymentsViewer extends JFrame {
//    private final DefaultTableModel model;
//    private final ScheduledExecutorService poller = Executors.newSingleThreadScheduledExecutor();
//    private final ExecutorService apiPool = Executors.newFixedThreadPool(6);
//    private final Map<String, String> csCache = new ConcurrentHashMap<>();
//    private volatile List<List<String>> snapshot = Collections.emptyList();
//
//    private final TransactionDetailsApi csApi;
//
//    public PaymentsViewer() throws Exception {
//        super("Payments + CyberSource Viewer");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(650, 300);
//        setLayout(new BorderLayout());
//
//        // Load CyberSource credentials
//        Properties props = new Properties();
//        props.load(new FileInputStream("cybs.properties"));
//
//        MerchantConfig merchantConfig = new MerchantConfig(props);
//        ApiClient client = new ApiClient();
//        client.setMerchantConfig(merchantConfig);
//        csApi = new TransactionDetailsApi(client);
//
//        // Table setup
//        String[] cols = {"Status", "Batch Status", "Transaction ID", "CS Decision"};
//        model = new DefaultTableModel(cols, 0);
//        JTable table = new JTable(model);
//        add(new JScrollPane(table), BorderLayout.CENTER);
//
//        // Start polling DB
//        poller.scheduleAtFixedRate(this::refreshData, 0, 5, TimeUnit.SECONDS);
//        setVisible(true);
//    }
//
//    private void refreshData() {
//        try {
//            List<List<String>> fresh = loadSnapshot();
//            if (fresh.equals(snapshot)) return; // no changes
//            snapshot = fresh;
//
//            // Queue CyberSource lookups if not already cached
//            for (List<String> row : fresh) {
//                String txnId = row.get(2);
//                if (!csCache.containsKey(txnId)) {
//                    apiPool.submit(() -> fetchCyberSourceDecision(txnId));
//                }
//            }
//
//            // Update table with latest data
//            SwingUtilities.invokeLater(() -> {
//                model.setRowCount(0);
//                for (List<String> row : fresh) {
//                    String txnId = row.get(2);
//                    String decision = csCache.getOrDefault(txnId, "...");
//                    model.addRow(new Object[]{row.get(0), row.get(1), txnId, decision});
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private List<List<String>> loadSnapshot() throws SQLException {
//        String query = "SELECT status, batch_status, transaction_id FROM payments ORDER BY id";
//        List<List<String>> data = new ArrayList<>();
//
//        try (Connection conn = DatabaseManager.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//
//            while (rs.next()) {
//                data.add(Arrays.asList(
//                        rs.getString("status"),
//                        rs.getString("batch_status"),
//                        rs.getString("transaction_id")
//                ));
//            }
//        }
//        return data;
//    }
//
//    private void fetchCyberSourceDecision(String txnId) {
//        try {
//            PtsV2TransactionDetailsGet200Response res = csApi.getTransaction(txnId);
//            String decision = res.getStatus(); // e.g., ACCEPT, REJECT, ERROR
//            csCache.put(txnId, decision != null ? decision : "UNKNOWN");
//        } catch (Exception e) {
//            csCache.put(txnId, "ERROR");
//        }
//
//        // Trigger UI refresh for decision column
//        SwingUtilities.invokeLater(this::updateDecisionColumn);
//    }
//
//    private void updateDecisionColumn() {
//        for (int r = 0; r < model.getRowCount(); r++) {
//            String txnId = (String) model.getValueAt(r, 2);
//            String decision = csCache.get(txnId);
//            if (decision != null) {
//                model.setValueAt(decision, r, 3);
//            }
//        }
//    }
//
//    public void stopAll() {
//        poller.shutdown();
//        apiPool.shutdown();
//    }
//
//    public static void main(String[] args) throws Exception {
//        PaymentsViewer viewer = new PaymentsViewer();
//        Runtime.getRuntime().addShutdownHook(new Thread(viewer::stopAll));
//    }
//}
//
//
//
