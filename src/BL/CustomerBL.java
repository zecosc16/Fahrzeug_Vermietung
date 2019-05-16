/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import fahrzeug_vermietung.Customer;
import fahrzeug_vermietung.Vehicle;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author oskar
 */
public class CustomerBL extends AbstractListModel{
        private ArrayList<Customer> customers= new ArrayList<>();

    public ArrayList<Customer> getCustomers() {
        return customers;
    }


       
    @Override
    public int getSize() {
        return customers.size();
    }

    @Override
    public Object getElementAt(int index) {
        return customers.get(index);
    }
    
    public void add(String name, LocalDate gebDat, String telNum, double money,int cID){
        customers.add(new Customer(name, gebDat, telNum, money, cID));
        fireIntervalAdded(this, customers.size(), customers.size());
    }

    public Customer get(int idx){
        return customers.get(idx);
    }
    
    /**
     * returns the customer with the given id
     * @param id
     * @return
     * @throws Exception 
     */
    public Customer getCWID(int id) throws Exception{
        for (Customer customer : customers) {
            if(customer.getCustID()==id)
                return customer;
        }
        throw new Exception("FK of vehicle is false(custID)");
    }
    
    /**
     * method for refreshing the list
     */
    public void update(){
        fireContentsChanged(this, 0, customers.size());
    }
    
    public void deleteCustomer(Customer c){
        customers.remove(c);
        fireIntervalRemoved(this, 0, customers.size());
    }
    
    
     public void export() throws IOException, NullPointerException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("./customer.csv"));
        for (Customer customer : customers) {
            bw.write(String.format("%d,%s,%s,%s,%d",customer.getCustID(),customer.getName(),customer.getGebDat(),customer.getTelNum(),(int)customer.getMoney() ));
            bw.newLine();
        }
        bw.close();
    }
    
}
