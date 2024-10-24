package org.example.model;

public class Admin extends User {
    public Admin(String name, String userid, String email, String password) {
        super(name, userid, email, password,"Admin");
    }

}
