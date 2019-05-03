package fahrzeug_vermietung;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import fahrzeug_vermietung.*;

/**
 *
 * @author oskar
 */
public class Vehicle {

    private int vID;
    private CarBrands brand;
    private String name;
    private double pricePDay;

    public Vehicle(String name,int vID, CarBrands brand, double pricePDay) {
        this.vID = vID;
        this.brand = brand;
        this.name = name;
        this.pricePDay = pricePDay;
    }

    
   
    
    
}
