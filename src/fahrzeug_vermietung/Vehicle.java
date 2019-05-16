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

    private CarBrands brand;
    private String name;
    private double pricePDay;
    private LocalDate borrowTill;
    private Customer c;
    private final int vID;

    public Vehicle(CarBrands brand, String name, double pricePDay, LocalDate borrowTill, Customer c, int viD) {
        this.brand = brand;
        this.name = name;
        this.pricePDay = pricePDay;
        this.borrowTill = borrowTill;
        this.c = c;
        this.vID = viD;
    }

    public Vehicle(String name, CarBrands brand, double pricePDay, int viD) {
        this.brand = brand;
        this.name = name;
        this.pricePDay = pricePDay;
        this.vID = viD;
    }

    @Override
    public String toString() {

        if (borrowTill != null) {
            return String.format("%d %s %s Price: %.2f is borrowed by %s till %s", vID, brand, name, pricePDay, c.getName(), borrowTill);
        }

        return String.format("%d %s %S Price: %.2f ", vID, brand, name, pricePDay);
    }

    /**
     * method which is called when the cars is being borrowed and sets the
     * parameters
     *
     * @param c
     * @param d
     * @throws Exception
     */
    public void borrow(Customer c, LocalDate d) throws Exception {

        if (this.borrowTill != null) {
            throw new VehicleNotAvailable();
        }
        double price = (double) ((ChronoUnit.DAYS.between(LocalDate.now(), d) + 1) * pricePDay);
        if (price > c.getMoney()) {
            throw new NotEnoughMoney();
        }
        c.pay(-price);
        borrowTill = d;
        this.c = c;
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

    public int getVID() {
        return vID;
    }

    public Customer getCustomer() {
        return c;
    }

    public void vehicleBack() {
        borrowTill = null;
        c = null;
    }

}
