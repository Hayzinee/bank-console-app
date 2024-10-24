package org.example.service;

import org.example.model.Admin;
import org.example.model.Customer;
import org.example.model.User;

import java.math.BigDecimal;
import java.util.*;

public class UserService {
    private final Map<String, User> users;
    private User currentUser;

    public UserService() {
        this.users = new HashMap<>();
        Admin defaultAdmin = new Admin("Admin", "admin1", "admin@bank.com", "admin123");
        users.put(defaultAdmin.getUserid(), defaultAdmin);

    }

    public boolean registerUser(String name, String userid, String email, String password, String role) {
        if (currentUser == null || !currentUser.getRole().equals("Admin")) {
            return false;
        }

        if (!isValidEmail(email) || !isValidPassword(password)) {
            return false;
        }

        if (users.values().stream().anyMatch(u -> u.getEmail().equals(email))) {
            return false;
        }

        User newUser;
        if (role.equals("Admin")) {
            newUser = new Admin(name, userid, email, password);
        } else {
            newUser = new Customer(name, userid, email, password);
        }
        users.put(newUser.getUserid(), newUser);
        return true;
    }

    public User login(String email, String password) {
        Optional<User> user = Optional.empty();
        for (User u : users.values()) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                user = Optional.of(u);
                break;
            }
        }
        if (user.isPresent()) {
            currentUser = user.get();
            return currentUser;
        }
        return null;
    }

    public List<User> getAllUsers() {
        if (currentUser != null && currentUser.getRole().equals("Admin")) {
            return new ArrayList<>(users.values());
        }
        return null;
    }

    public boolean deleteUser(String userId) {
        if (currentUser != null && currentUser.getRole().equals("Admin")) {
            if (users.containsKey(userId)) {
                users.remove(userId);
                return true;
            }
        }
        return false;
    }

    public boolean transferFunds(String fromUserId, String toUserId, BigDecimal amount) {
        if (currentUser == null || !currentUser.getUserid().equals(fromUserId)) {
            return false;
        }
        User fromUser = users.get(fromUserId);
        User toUser = users.get(toUserId);

        if (!(fromUser instanceof Customer) || !(toUser instanceof Customer)) {
            return false;
        }

        Customer fromCustomer = (Customer) fromUser;
        Customer toCustomer = (Customer) toUser;

        if (fromCustomer.getAccounts().getFirst().withdraw(amount)) {
            toCustomer.getAccounts().getFirst().deposit(amount);
            return true;
        }
        return false;
    }

    public boolean depositFunds(String userId, BigDecimal amount) {
        if (currentUser == null || !currentUser.getUserid().equals(userId)) {
            return false;
        }
        User fromUser = users.get(userId);

        if (!(fromUser instanceof Customer customer)) {
            return false;
        }

        customer.getAccounts().getFirst().deposit(amount);
        return true;

    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

}
