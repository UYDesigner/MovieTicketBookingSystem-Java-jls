package Views;

import Models.User;
import dao.UserDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class WelcomeView {

    public void welcomeScreen(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome To The Movie Ticket Booking App ");
        System.out.println("Press 1 to Register\nPress 2 to Login\nPress 0 to Exit");
        int choice = 0;

        try {
            choice = Integer.parseInt(br.readLine());
            if (choice != 0 && choice != 1 && choice != 2) {
                System.out.println("Invalid Choice! Please enter 0, 1, or 2.");
                System.out.println();
                // Prompt user to try again
                welcomeScreen();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (choice) {
            case 1 -> register();
            case 2 -> login();
            case 0 -> System.exit(0);
        }
    }

    private void register()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter User Name : ");
        String name = sc.nextLine();
        System.out.print("Enter Email Address : ");
        String email = sc.nextLine();
        System.out.print("Enter Password : ");
        String password = sc.nextLine();

        try{
            if(UserDao.isExist(email))
            {
                System.out.println("This email is already associated with another account...");
            }
            else
            {
                User user = new User(name, email, password);
                int ans = UserDao.saveUser(user);
                System.out.println(ans == 1? "User Register Successfully..." : "There is a problem with user registration...");
            }
        }
        catch (SQLException EX)
        {
            EX.printStackTrace();
        }


    }

    private void login()
    {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Email : ");
        String email = sc.nextLine();
        System.out.print("Enter Password : ");
        String password = sc.nextLine();

        try{
            if (UserDao.isExist(email) == false) {

                System.out.println("User does not exist.");
                welcomeScreen();

            }
            else
            {
              User user = new User(email, password);
              String name = UserDao.userLogin(user);
                if (name != null) {
                    System.out.println();
                    System.out.println("Welcome, " + name + "! You are logged in.");
                    new UserView(email, password).userScreen();
                } else {
                    System.out.println("Incorrect password. Please try again.");
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


}
