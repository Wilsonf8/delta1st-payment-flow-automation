package DeltaFirstTransactions;

import java.sql.*;

public class Transactions {

    public static void main(String[] args) throws SQLException {
        String sql = "select * from purchase_order_details where id=1";
//        String url = "jdbc:mysql://127.0.0.1:3306/ds-service-sadvanced";
        String url = "jdbc:mysql://ds-mysql8.cluster-clb2b7knmca4.us-east-2.rds.amazonaws.com/ds-service-sadvanced";
        String username = "trainee_readonly";
        String password = "k21jBncdIP7iWIk7Dqti";

        Connection con = DriverManager.getConnection(url, username, password);
//        Statement st = con.createStatement();
//        ResultSet rs = st.executeQuery(sql);
//        rs.beforeFirst();
//        rs.next();
//        String val = rs.getString("notes");
//        System.out.println(val);

    }


}
