package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import org.example.model.Customer;
import org.example.model.User;
import org.example.service.UserService;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.List;

public class BankSystemApp {
    private static UserService userService;
    private static Scanner scanner;

    public static void main(String[] args) {
        userService = new UserService();
        scanner = new Scanner(System.in);

        while (true) {
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== Bank System ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");
    }

    private static void login() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.login(email, password);
        if (user != null) {
            System.out.println("Login successful!");
            if (user.getRole().equals("Admin")) {
                showAdminMenu(user);
            } else {
                showCustomerMenu(user);
            }
        } else {
            System.out.println("Login failed!");
        }
    }

    private static void showAdminMenu(User admin) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Register new user");
            System.out.println("2. View all users");
            System.out.println("3. Delete user");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void showCustomerMenu(User customer) {
        while (true) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. Check balance");
            System.out.println("2. Transfer funds");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    checkBalance(customer);
                    break;
                case 2:
                    transferFunds(customer);
                    break;
                case 3:
                    depositFunds(customer);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }



    private static void registerUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter userid: ");
        String userid = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (Admin/Customer): ");
        String role = scanner.nextLine();

        if (userService.registerUser(name, userid, email, password, role)) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Registration failed!");
        }
    }

    private static void viewAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users != null) {
            System.out.println("\nAll Users:");
            for (User user : users) {
                System.out.println("ID: " + user.getUserid() +
                        ", Name: " + user.getName() +
                        ", Email: " + user.getEmail() +
                        ", Role: " + user.getRole());
            }
        } else {
            System.out.println("Access denied!");
        }
    }

    private static void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        String userId = scanner.nextLine();

        if (userService.deleteUser(userId)) {
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("Delete failed!");
        }
    }

    private static void checkBalance(User user) {
        if (user instanceof Customer customer) {
            System.out.println("Current balance: $" + customer.getAccounts().getFirst().getBalance());
        }
    }

    private static void transferFunds(User user) {
        System.out.print("Enter recipient user ID: ");
        String toUserId = scanner.nextLine();
        System.out.print("Enter amount to transfer: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (userService.transferFunds(user.getUserid(), toUserId, BigDecimal.valueOf(amount))) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed!");
        }
    }

    private static void depositFunds(User user) {
        System.out.print("Enter recipient user ID: ");
        String toUserId = scanner.nextLine();
        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (userService.depositFunds(user.getUserid(), BigDecimal.valueOf(amount))) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed!");
        }

    }
}
