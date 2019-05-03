/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fahrzeug_vermietung;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author oskar
 */
public class VehicleBL extends AbstractListModel{

    private ArrayList<Vehicle> vehicle = new ArrayList<>();
    private int vID=1;
    
    @Override
    public int getSize() {
        return vehicle.size();
    }

    @Override
    public Object getElementAt(int index) {
        return vehicle.get(index);
    }
    
    public void add(String name, CarBrands brand, double pricePDay, int amount){
        for (int i = 0; i < amount; i++) {
            vehicle.add(new Vehicle(name, vID, brand, pricePDay));
            vID++;
            
        }
    }
    
}