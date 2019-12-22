package top.chorg.iCommerce.api;

import java.util.Date;

public class Customer {
    public int id;
    public String username;
    public String email;
    public double balance;
    public Date registerTime;

    public Customer(int id, String username, String email, double balance, Date registerTime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.balance = balance;
        this.registerTime = registerTime;
    }
}
