/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import fahrzeug_vermietung.CarBrands;
import fahrzeug_vermietung.Customer;
import fahrzeug_vermietung.Vehicle;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author oskar
 */
public class VehicleBL extends AbstractListModel {

    private ArrayList<Vehicle> vehicle = new ArrayList<>();

    @Override
    public int getSize() {
        return vehicle.size();
    }

    @Override
    public Object getElementAt(int index) {
        return vehicle.get(index);
    }

    public void add(String name, CarBrands brand, double pricePDay, int vID) {
        vehicle.add(new Vehicle(name, brand, pricePDay, vID
        ));

        fireIntervalAdded(this, vehicle.size(), vehicle.size());
    }

    public Vehicle get(int idx) {
        return vehicle.get(idx);
    }

    public void add(CarBrands whichBrand, String string, double aDouble, LocalDate toLocalDate, Customer get, int vID) {
        vehicle.add(new Vehicle(whichBrand, string, aDouble, toLocalDate, get, vID));
        fireIntervalAdded(this, vehicle.size(), vehicle.size());
    }
}
