package dao;

import Models.User;
import db.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class UserDao {

    public static String convertToLowerCase(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            // Check if the character is uppercase
            if (c >= 'A' && c <= 'Z') {
                // Convert uppercase to lowercase by adding 32 to its ASCII value
                c = (char) (c + 32);
            }
            result.append(c);
        }

        return result.toString();
    }


    public static boolean isExist(String email) throws SQLException {
        Connection con = MyConnection.getConnection();
        String userEmail = convertToLowerCase(email);
        PreparedStatement ps = con.prepareStatement("select email from user");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String e = rs.getString(1);
            String em = convertToLowerCase(e);
            if (em.equals(userEmail)) {
//                System.out.println("True");
                return true;

            }
        }
//        System.out.println("false");
        return false;
    }


    public static int saveUser(User user) throws SQLException {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("insert into user (name, email, password) values (?,?,?)");
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());

        int ans = ps.executeUpdate();
        return ans;


    }

    public static String userLogin(User user) throws SQLException {
        String name = null;
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("Select name from user where email = ? and password = ?");
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ResultSet rs = null;
        rs = ps.executeQuery();

        if (rs.next()) {
//            System.out.println(rs.getString("name"));
            name = rs.getString("name");
        }

        return name;
    }

    public static User fetchUserData(String email) throws SQLException
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("Select * from user where email = ? ");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            User user = new User(rs.getString(2), rs.getString(3), rs.getString(4) );
            return user;
        }

        return null;
    }

    public static int updateUser(String name, String email) throws SQLException
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("update user set name = ? where email = ? ");
        ps.setString(1, name);
        ps.setString(2,email);
        int ans = ps.executeUpdate();
        return ans;
    }



}
