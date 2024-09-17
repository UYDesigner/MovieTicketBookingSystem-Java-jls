package dao;

import Models.Movie;
import db.MyConnection;

import java.sql.*;

public class DataDao {



    public static boolean fetchMovies() throws SQLException
    {

        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from moviedetails");
        ResultSet rs = ps.executeQuery();
        System.out.println();
        System.out.println("     " + "-------------------------------------------------------------------------");
        System.out.println("     " + "---                 ** Movie Currently Available **                   ---");
        System.out.println("     " + "-------------------------------------------------------------------------");
        System.out.println();
        while (rs.next())
        {
            System.out.println("Id : " + rs.getInt(1) + "  ,  " + "Movie Title : " + rs.getString(2) + "  ,  " + " " +
                    "Genre" +
                    " : " + rs.getString(3) );
            System.out.println("Description : " + rs.getString(7));
            System.out.println("Language : " + rs.getString(8));
            System.out.println("Rating : " + rs.getString(9));
            System.out.println("Status : " + rs.getString(10));
            System.out.println("Price : "+ rs.getString(11));
            System.out.println("ShowTime : "+ rs.getString(12));
            System.out.println("Location : "+rs.getString(13));
            System.out.println();
            System.out.println();


        }
        if(rs != null)
        {
            return true;
        }


        return false;
    }

    public static boolean checkMovieId(int id) throws SQLException
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from moviedetails");
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
          int  rse = rs.getInt(1);

            if(rse == id)
            {

                return true;
            }
        }

        return false;
    }

    public static Movie fetchMovieById(int id) throws SQLException
    {
        int movieId = id;
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from moviedetails where id = ?");
        ps.setInt(1, movieId);
        ResultSet rs = ps.executeQuery();
        System.out.println();
        System.out.println();
        System.out.println("------------------------------      *** Movie Detail ***      " +
                "------------------------------");
        System.out.println();
        while (rs.next())
        {

            System.out.println( "Movie Title : " + rs.getString(2) + "  ,  " + " " +
                    "Genre" +
                    " : " + rs.getString(3)  );
            System.out.println("Description : " + rs.getString(7));
            System.out.println("Language : " + rs.getString(8));
            System.out.println("Rating : " + rs.getString(9));
            System.out.println("Status : " + rs.getString(10));
            System.out.println("Release Date : " + rs.getString(4));
            System.out.println("Price : "+ rs.getString(11));
            System.out.println("ShowTime : "+ rs.getString(12));
            System.out.println("Location : "+rs.getString(13));
            System.out.println(
                    "--------------------------------------------------------------------------------------------");
            System.out.println();


            Movie movie = new Movie(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(8),
                    rs.getString(11), rs.getString(12),rs.getString(10), rs.getString(13) );

            return movie;

        }


        return null;

    }

    public static int saveTicketCode(String email, int randomCode, String movieName) throws SQLException
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("insert into ticketcode (email, randomcode, moviename) values (?," +
                "?, ?)");
        ps.setString(1,email);
        ps.setInt(2,randomCode);
        ps.setString(3,movieName);
        int ans = ps.executeUpdate();
        return ans;

    }

    public static void fetchMovieByName(String name) throws SQLException
    {

        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from moviedetails where title = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {

            System.out.println( "      " +"Movie Title : " + rs.getString(2) + "  ,  " + " " +
                    "Genre" +
                    " : " + rs.getString(3)  );

            System.out.println( "      " +"Language : " + rs.getString(8));
            System.out.println( "      " +"Rating : " + rs.getString(9));
            System.out.println( "      " +"Status : " + rs.getString(10));
            System.out.println( "      " +"Release Date : " + rs.getString(4));
            System.out.println( "      " +"Price : "+ rs.getString(11));
            System.out.println( "      " +"ShowTime : "+ rs.getString(12));


            Movie movie = new Movie(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(8),
                    rs.getString(11), rs.getString(12),rs.getString(10), rs.getString(13) );

            return ;

        }


        return ;

    }

    public static boolean checkTicketInfo(int code) throws SQLException
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from ticketcode where randomcode = ?");
        ps.setInt(1,code);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            System.out.println("     " + "-------------------------------------------------------------------------");
            System.out.println("     " + "---                      ** Ticket Information **                     ---");
            System.out.println("     " + "-------------------------------------------------------------------------");
            fetchMovieByName(rs.getString(4));
            System.out.println( "      " +"Your Email : " + rs.getString(2));
            System.out.println("     " + "-------------------------------------------------------------------------");

            System.out.println("     " + "-------------------------------------------------------------------------");

            return true;

        }

        return false;
    }

    public static int cancelTicket(int code) throws SQLException
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(" delete from ticketcode where randomcode = ? ");
        ps.setInt(1,code);
        int ans = ps.executeUpdate();
        return ans;


    }


}
