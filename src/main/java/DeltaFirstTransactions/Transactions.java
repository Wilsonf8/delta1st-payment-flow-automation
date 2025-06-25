package DeltaFirstTransactions;

import java.sql.*;

public class Transactions {

    public static void main(String[] args) throws SQLException {
        String sql = "select * from purchase_order_details where id=1";
        String url = "jdbc:mysql://127.0.0.1:3306/service";
        String username = "root";
        String password = "Jackson_f8";

        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
//        rs.beforeFirst();
        rs.next();
        String val = rs.getString("notes");
        System.out.println(val);

    }


}
