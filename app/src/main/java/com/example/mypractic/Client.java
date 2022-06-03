package com.example.mypractic;

public class Client {
    private String name;
    private String email;
    private String password;

    public Client(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Client() {

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
