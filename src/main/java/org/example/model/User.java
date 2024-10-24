package org.example.model;

public abstract class User {
    private String name;
    private String userid;
    private String email;
    private String password;
    private String role;

    public User(String name, String userid, String email, String password, String role) {
        this.name = name;
        this.userid = userid;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }

    public String getEmail() {
        return email;
    }
    
    public String getRole(){
        return role;
    }

    public String getPassword() {
        return password;
    }

    public boolean authenticate(String password){
        return this.password.equals(password);
    }
}


