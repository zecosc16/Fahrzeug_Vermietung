/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import fahrzeug_vermietung.Customer;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author oskar
 */
public class CustomerBL extends AbstractListModel{
        private ArrayList<Customer> customers= new ArrayList<>();
        private int custID=1;
        
    @Override
    public int getSize() {
        return customers.size();
    }

    @Override
    public Object getElementAt(int index) {
        return customers.get(index);
    }
    
    public void add(String name, LocalDate gebDat, String telNum, double money){
        customers.add(new Customer(name, gebDat, telNum, money, custID));
        custID++;
        fireIntervalAdded(this, customers.size(), customers.size());
    }

    public Customer get(int idx){
        return customers.get(idx);
    }
    
    
    
}
