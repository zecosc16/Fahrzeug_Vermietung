/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import fahrzeug_vermietung.CarBrands;
import fahrzeug_vermietung.Customer;
import fahrzeug_vermietung.Vehicle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    /**
     * refreshes the list
     */
    public void update() {
        fireContentsChanged(this, 0, vehicle.size());
    }

    public void deleteVehicle(Vehicle v) {
        vehicle.remove(v);
        fireIntervalRemoved(this, 0, vehicle.size());
    }

    /**
     * returns true if a vehicle is borrowed by the customer given
     *
     * @param c
     * @return
     */
    public boolean hasBorrowed(Customer c) {
        for (Vehicle vehicle : vehicle) {
            if (vehicle.getCustomer().equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns ArrayList with the indexes of vehicles whose borrowDate is over
     * todayDate
     *
     * @return
     */
    public ArrayList<Integer> getExpiredCars() {
        ArrayList<Integer> v = new ArrayList<>();
        for (Vehicle vehicle : vehicle) {
            if (vehicle.getBorrowTill().isAfter(LocalDate.now())) {
                v.add(this.vehicle.indexOf(vehicle));
            }
        }
        return v;
    }

    /**
     * exports all Vehicles into a file
     *
     * @param f
     * @throws IOException
     * @throws NullPointerException
     */
    public void export() throws IOException, NullPointerException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("./file.csv"));
        for (Vehicle vehicle : vehicle) {
            if (vehicle.getBorrowTill() == null) {
                bw.write(String.format("%d,%s,%s,%d", vehicle.getVID(), vehicle.getBrand(), vehicle.getName(), vehicle.getPricePDay()));
            } else {
                bw.write(String.format("%d,%s,%s,%d,%s,%d", vehicle.getVID(), vehicle.getBrand(), vehicle.getName(), vehicle.getPricePDay(), vehicle.getBorrowTill(), vehicle.getCustomer().getCustID()));
            }
            bw.newLine();
        }
        bw.close();
    }

}
