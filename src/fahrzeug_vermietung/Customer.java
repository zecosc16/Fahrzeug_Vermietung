package fahrzeug_vermietung;

import java.time.LocalDate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oskar
 */
public class Customer {
    private String name;
    
    private LocalDate gebDat;
    private String telNum;
    private double money;
    private int custID;

    public Customer(String name, LocalDate gebDat, String telNum, double money,int custID) {
        this.name = name;
        this.gebDat = gebDat;
        this.telNum = telNum;
        this.money = money;
        this.custID=custID;
    }

    public String getName() {
        return name;
    }

    public LocalDate getGebDat() {
        return gebDat;
    }

    public String getTelNum() {
        return telNum;
    }

    public double getMoney() {
        return money;
    }
    
    
    
    
    public void pay(double amount){
        money+=amount;
    }
    
    
    @Override
    public String toString() {
        return String.format("%d %s has %.2f€",custID,name,money);
    }
}
