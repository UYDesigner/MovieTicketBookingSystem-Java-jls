package Views;

import Models.Movie;
import Models.User;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import dao.DataDao;
import dao.UserDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class UserView {

    String email;
    String password;

    public UserView(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static void userScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        System.out.println();
        System.out.println("Welcome To The MovieBazzar App ");
        System.out.println("Please select an option:");
        System.out.println();
        System.out.println("1. Show Available Movies");
        System.out.println("2. Book Tickets");
        System.out.println("3. Check Booking Status");
        System.out.println("4. Cancel Booking");
        System.out.println("5. View Account Details");
        System.out.println("6. Update Account");
        System.out.println("0. Exit");
        int choice = 0;

        try {
            choice = Integer.parseInt(br.readLine());
            if (choice != 0 && choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6) {
                System.out.println("Invalid Choice! Please choice correct option...");
                System.out.println();
                // Prompt user to try again
                userScreen();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (choice) {
            case 1 -> showAvailableMovies();
            case 2 -> bookingTickets();
            case 3 -> checkBookingStatus();
            case 4 -> cancelBooking();
            case 5 -> viewAccountDetails();
            case 6 -> updateAccount();
            case 0 -> System.exit(0);
        }
    }

    public static void showAvailableMovies() {
        try {
            if (DataDao.fetchMovies()) {
                userScreen();
            } else {
                System.out.println("Server Error Please Try Later...");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int randomCode() {
        Random random = new Random();
        return random.nextInt(90000) + 10000;
    }


    public static void bookingTickets() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Id Of Movie : ");
        int id = sc.nextInt();
        try {
            if (DataDao.checkMovieId(id)) {


                Movie movie = DataDao.fetchMovieById(id);

                System.out.println(movie.getStatus().equals("Upcoming") ?
                        "This movie has not been released in theaters." :
                        "This movie is currently running in theaters.");
                System.out.println("You can book the tickets in advance.");
                System.out.print("Want to proceed? Enter '1' for Yes or '0' for No: ");
                int ans = sc.nextInt();
                if (ans == 1) {
                    System.out.println("Check the location:");
                    System.out.println("This movie is currently available in " + movie.getTheater() + ".");
                    System.out.println("If you want to book tickets, enter '1' for Yes or '0' for No: ");
                    int res = sc.nextInt();
                    if (res == 1) {
                        System.out.print("Enter the number of tickets: ");
                        int num = sc.nextInt();
                        int price = Integer.parseInt(movie.getPrice());
                        System.out.println("Total Price: " + price * num);
                        System.out.print("Want to continue, enter '1' for Yes or '0' for No: ");
                        int con = sc.nextInt();
                        if (con == 1) {
                            System.out.print("Enter email address : ");
                            String email = sc.nextLine();
                            if (UserDao.isExist(email)) {
                                int randomCode = randomCode();
                                String movieName = movie.getTitle();
                                int save = DataDao.saveTicketCode(email, randomCode, movieName);
                                System.out.println();
                                System.out.println();
                                System.out.println(save == 1 ? "Your Ticket Code is " + randomCode : "Error in save ticket " +
                                        "code");
                                System.out.println("*** Remember The Ticket Code ***");

                                userScreen();
                            } else {
                                System.out.println("Invalid Email !, Try Again");
                                System.out.print("Enter email address : ");
                                String email2 = sc.nextLine();

                                if (UserDao.isExist(email2)) {
                                    int randomCode2 = randomCode();
                                    System.out.println();
                                    String movieName2 = movie.getTitle();
                                    int save = DataDao.saveTicketCode(email2, randomCode2, movieName2);
                                    System.out.println();
                                    System.out.println();
                                    System.out.println(save == 1 ? "Your Ticket Code is " + randomCode2 : "Error in " +
                                            "save ticket " +
                                            "code");
                                    System.out.println("*** Remember The Ticket Code ***");

                                    userScreen();
                                } else {
                                    System.out.println("Invalid Email...");
                                    userScreen();
                                }
                            }


                        } else {
                            System.out.println("Thank you.");
                            userScreen();
                        }

                    } else {
                        System.out.println("Thank you.");
                        userScreen();
                    }


                } else {
                    System.out.println("Thank you.");
                    userScreen();
                }

            } else {
                System.out.println();
                System.out.println("Invalid Id, try again...");
                System.out.println();
                userScreen();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void checkBookingStatus() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Your Ticket Code ");
        int code = sc.nextInt();

        try {
            if (DataDao.checkTicketInfo(code)) {
                userScreen();
            } else {
                System.out.println("Invalid Code...");
                userScreen();
            }
        } catch (SQLException E) {
            E.printStackTrace();
        }


    }

    public static void cancelBooking()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Ticket Code : ");
        int code = sc.nextInt();

        try {
          int ans = DataDao.cancelTicket(code);
            System.out.println(ans == 1 ? "Your Booking cancelled ..." : "Server Error");
            userScreen();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void viewAccountDetails()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Your Email : ");
        String email =sc.nextLine();
        try{

            if(UserDao.isExist(email))
            {
                User user = UserDao.fetchUserData(email);
                System.out.println();
                System.out.println("-----------------------------------------------");
                System.out.println("-------------  View Account Details -----------");
                System.out.println("-----------------------------------------------");

                System.out.println(" User Name : " + user.getName());
                System.out.println(" Email Address : " + user.getEmail());

                System.out.println("-----------------------------------------------");
                System.out.println("-----------------------------------------------");
                userScreen();

            }
            else
            {
                System.out.println("Invalid Email...");
                userScreen();
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void updateAccount()
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Email Address : ");
        String email = sc.nextLine();
        System.out.print("Enter Password : ");
        String password = sc.nextLine();

        try{
            if(UserDao.isExist(email))
            {
                System.out.print("Enter User Name : ");
                String name = sc.nextLine();
                int ans = UserDao.updateUser(name, email);
                System.out.println();
                System.out.println(ans == 1 ? "Account Updated Successfully ..." : "Problem in updating account...");
                System.out.println();
                new WelcomeView().welcomeScreen();
            }
            else
            {
                System.out.println("This email is not associated with any account...");
            }
        }
        catch (SQLException EX)
        {
            EX.printStackTrace();
        }
    }

}


