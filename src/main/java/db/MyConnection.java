package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    static Connection con = null;

    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/movieTicketBookingSystemJava?useSSL=false",
                    "root",
                    "8982008982nicky"
            );

//            System.out.println("Connected To Database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("connection closed...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        MyConnection obj = new MyConnection();
//        obj.getConnection();
//    }


}
