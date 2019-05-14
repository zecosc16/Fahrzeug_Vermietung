package fahrzeug_vermietung;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Exceptions.NotEnoughMoney;
import Exceptions.VehicleNotAvailable;
import fahrzeug_vermietung.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author oskar
 */
public class Vehicle {

    public Vehicle(CarBrands brand, String name, double pricePDay, LocalDate borrowTill, Customer c,int viD) {
        this.brand = brand;
        this.name = name;
        this.pricePDay = pricePDay;
        this.borrowTill = borrowTill;
        this.c = c;
        this.viD=viD;
    }

    private CarBrands brand;
    private String name;
    private double pricePDay;
    private LocalDate borrowTill;
    private Customer c;
    private final int viD;
    
    

    public Vehicle(String name, CarBrands brand, double pricePDay,int viD) {
        this.brand = brand;
        this.name = name;
        this.pricePDay = pricePDay;
        this.viD=viD;
    }

    @Override
    public String toString() {
        boolean t=true;
        if(borrowTill!= null)
            t=false;
        
        return String.format("%s %s available: %s", brand,name,t);
    }
    
    public void borrow(Customer c,LocalDate d) throws Exception{
        if(this.borrowTill!=null)
            throw new VehicleNotAvailable();
       double price = (double) (ChronoUnit.DAYS.between( LocalDate.now(),d)*pricePDay);
       if(price>c.getMoney())
           throw new NotEnoughMoney();
       c.pay(-price);
       borrowTill=d;
       this.c=c;
       
    }

   
    public CarBrands getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public int getPricePDay() {
        return (int) pricePDay;
    }

    public LocalDate getBorrowTill() {
        return borrowTill;
    }

    public void vehicleBack(){
        borrowTill=null;
        c=null;
    }
    
    
    
   
    
    
}
