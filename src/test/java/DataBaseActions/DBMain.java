package DataBaseActions;

import java.sql.*;

class DBMain {

    void connectionTest() {
        System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (
                ClassNotFoundException e)

        {
            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");
    }

    Connection getConnection() {
        Connection con;
        try {
            String password = "medgreat";
            String username = "medgreat";
            String dbUrl = "jdbc:postgresql://192.168.1.123:3306/medgreat?characterEncoding=utf8";
            con = DriverManager.getConnection(dbUrl, username, password);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
        return con;
    }

    ResultSet returnResult(Connection con, String query) throws SQLException{
        Statement stmt = con.createStatement();
        return stmt.executeQuery(query);
    }

    public void tearDown(Connection con) {
        if (con != null) {
            try {
                System.out.println("Closing Database Connection...");
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}

