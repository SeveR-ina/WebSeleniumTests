package DataBaseActions;

import java.sql.*;

public class DBReturnSmsCode extends DBMain {
    private static DBMain dbMain;

    public String returnSmsCode() throws SQLException {

        String confirmationCode = null;

        dbMain = new DBMain();

        String query = "SELECT * FROM mobile_registration_token WHERE mobile_phone LIKE '%9050258607%'";

        dbMain.connectionTest();

        Connection con = dbMain.getConnection();

        ResultSet rs = dbMain.returnResult(con, query);

        if (!rs.isBeforeFirst()) {
            System.out.println("No user with 9050258607 number in mobile_registration_token");
            dbMain.tearDown(con);
            return null;
        } else {
            while (rs.next()) {
                confirmationCode = rs.getString("confirmation_code");
            }
            assert confirmationCode != null;
            dbMain.tearDown(con);
            return confirmationCode;
        }
    }

}
