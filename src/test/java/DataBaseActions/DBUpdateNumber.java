package DataBaseActions;

import java.sql.*;

public class DBUpdateNumber extends DBMain {
    private static DBMain dbMain;

    public static void main() throws SQLException {
        PreparedStatement ps;
        String mobileNumber = null;

        dbMain = new DBMain();

        String query = "SELECT * FROM user_profile WHERE mobile_phone LIKE '%9050258607%'";

        dbMain.connectionTest();

        Connection con = dbMain.getConnection();

        ResultSet rs = dbMain.returnResult(con, query);

        if (!rs.isBeforeFirst()) {
            System.out.println("Nothing to update, there is no such mobile number in base");
        } else {
            do {
                mobileNumber = rs.getString("mobile_phone");
            }
            while (rs.next());

            assert mobileNumber != null;

            if (mobileNumber.equals("9050258607")) {
                ps = con.prepareStatement("UPDATE user_profile SET mobile_phone = '0000000000' WHERE mobile_phone LIKE '%9050258607%'");
                ps.executeUpdate();
                ps.close();
            } else System.out.println(" Mobile number from database query ! = 9050258607 ");

        }
        dbMain.tearDown(con);
    }
}
