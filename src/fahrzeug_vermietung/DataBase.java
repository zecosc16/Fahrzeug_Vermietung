/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fahrzeug_vermietung;

import BL.CustomerBL;
import BL.VehicleBL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory.Result;

/**
 *
 * @author oskar
 */
public class DataBase {

    private Connection conn;
    private static DataBase theInstance;

    public DataBase() throws SQLException {
        try {

            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:1521/Vehicle_rental", "postgres", "Pw.12345");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized DataBase getInstance() throws SQLException {
        if (theInstance == null) {
            theInstance = new DataBase();
        }

        return theInstance;
    }

    /**
     * the method adds the Vehicle to the Database and return the fk(the Vehicle
     * id)
     *
     * @param name
     * @param pricePDay
     * @param
     * @return
     * @throws SQLException
     */
    public int addVehicle(String name, int pricePDay, CarBrands c) throws SQLException {
        Statement stat = conn.createStatement();

        String sqlString = String.format("INSERT INTO public.\"Vehicle\"(name, \"pricePDay\",  \"CarBrand\")VALUES (\'%s\',%d,\'%s\');", name, pricePDay, c);
        stat.executeUpdate(sqlString);

        String getID = "SELECT \"vID\" FROM public.\"Vehicle\"  ORDER BY \"vID\" DESC LIMIT 1;";
        ResultSet r = stat.executeQuery(getID);
        r.next();
        int vID = r.getInt("vID");

        stat.close();
        return vID;
    }
//    public int addVehicle(String name, int pricePDay,LocalDate d,CarBrands c) throws SQLException {
//        Statement stat = conn.createStatement();
//
//        if (d != null) {
//            String sqlString = String.format("INSERT INTO public.\"Vehicle\"(name, \"pricePDay\", \"borrowTill\", \"CarBrand\")VALUES (\'%s\',%d,TO_DATE(\'%s\','YYYY-MM-DD'),\'%s\');", name, pricePDay, d, c);
//            stat.executeUpdate(sqlString);
//        } else {
//            String sqlString = String.format("INSERT INTO public.\"Vehicle\"(name, \"pricePDay\",  \"CarBrand\")VALUES (\'%s\',%d,\'%s\');", name, pricePDay, c);
//            stat.executeUpdate(sqlString);
//        }
//
//        String getID = "SELECT \"vID\" FROM public.\"Vehicle\"  ORDER BY \"vID\" DESC LIMIT 1;";
//        ResultSet r = stat.executeQuery(getID);
//        r.next();
//        int vID = r.getInt("vID");
//        
//        stat.close();
//        return vID;
//    }

    public void initialize(VehicleBL vehicles, CustomerBL customers) throws SQLException, Exception {

        this.getCustomers(customers);
        this.getVehicles(vehicles, customers);

    }

    public void getVehicles(VehicleBL bl, CustomerBL customers) throws SQLException, Exception {
        CarBrands c = CarBrands.Audi;

        Statement stat = conn.createStatement();
        String s = "SELECT name, \"pricePDay\", \"borrowTill\", \"vID\", \"CarBrand\", \"custID\" FROM public.\"Vehicle\"";
        ResultSet res = stat.executeQuery(s);

        Vehicle v;
        while (res.next()) {

            if (res.getDate("borrowTill") == null) {
                bl.add(res.getString("name"), c.whichBrand(res.getString("CarBrand")), res.getDouble("pricePDay"), res.getInt("vID"));
            } else {

                bl.add(c.whichBrand(res.getString("CarBrand")), res.getString("name"), res.getDouble("pricePDay"), res.getDate("borrowTill").toLocalDate(), customers.getCWID(res.getInt("custID")), res.getInt("vID"));
            }

        }

        stat.close();
    }

    public void getCustomers(CustomerBL customers) throws SQLException {
        Statement stat = conn.createStatement();
        String s = "SELECT \"telNum\", money, \"gebDat\", name, \"cID\" FROM public.\"Customer\";";
        ResultSet res = stat.executeQuery(s);
        while (res.next()) {
            customers.add(res.getString("name"), res.getDate("gebDat").toLocalDate(), res.getString("telNum"), res.getInt("money"), res.getInt("cID"));
        }
        stat.close();
    }

    public int addCustomer(String name, LocalDate gebDat, String telNum, double money) throws SQLException {
        Statement stat = conn.createStatement();
        String sqlString = String.format("INSERT INTO public.\"Customer\"(\"telNum\", money, \"gebDat\", name)VALUES (\'%s\', %d, TO_DATE(\'%s\','YYYY-MM-DD'),\'%s\')", telNum, (int) money, gebDat, name);
        stat.executeUpdate(sqlString);

        String getID = "SELECT \"cID\" FROM public.\"Customer\"  ORDER BY \"cID\" DESC LIMIT 1;";
        ResultSet r = stat.executeQuery(getID);

        r.next();
        int cID = r.getInt("cID");
        stat.close();
        return cID;

    }

    public void VehicleBorrowed(Vehicle v, Customer c) throws SQLException {
        Statement stat = conn.createStatement();

        String ve = String.format("UPDATE public.\"Vehicle\" SET \"borrowTill\"=TO_DATE(\'%s\','YYYY-MM-DD'),\"custID\"=%d WHERE \"vID\"=%d;", v.getBorrowTill(), v.getCustomer().getCustID(), v.getVID());
        stat.executeUpdate(ve);

//        String cu = String.format("UPDATE public.\"Customer\" SET \"money\"=%d WHERE \"cID\"=%d;",(int) c.getMoney(), c.getCustID());
//        stat.executeUpdate(cu);
        stat.close();
    }

    public void updateCustomer(Customer c) throws SQLException {
        Statement stat = conn.createStatement();
        String cu = String.format("UPDATE public.\"Customer\" SET \"telNum\"=%s, money=%d, \"gebDat\"=TO_DATE(\'%s\','YYYY-MM-DD') WHERE \"cID\"=%d;", c.getTelNum(), (int) c.getMoney(), c.getGebDat(), c.getCustID());
        stat.executeUpdate(cu);
        stat.close();
    }

    public void updateVehicle(Vehicle v) throws SQLException {
        Statement stat = conn.createStatement();
        String ve;
        if (v.getBorrowTill() == null) {
            System.out.println("true");
            ve = String.format("UPDATE public.\"Vehicle\" SET name='%s', \"pricePDay\"=%d, \"borrowTill\"=NULL, \"CarBrand\"='%s', \"custID\"=NULL WHERE \"vID\"=%d;", v.getName(), (int) v.getPricePDay(), v.getBrand(), v.getVID());
        } else //            ve = String.format("UPDATE public.\"Vehicle\" SET name=%s, \"pricePDay\"=%d, \"borrowTill\"=TO_DATE(\'%s\','YYYY-MM-DD'), \"CarBrand\"=%s, \"custID\"=%d WHERE \"vID\"=%d;", v.getName(),(int)v.getPricePDay(),v.getBorrowTill(),v.getBrand(),v.getCustomer().getCustID(),v.getVID());
        {
            ve = String.format("UPDATE public.\"Vehicle\" SET  \"pricePDay\"=%d, \"borrowTill\"=TO_DATE(\'%s\','YYYY-MM-DD'), \"custID\"=%d WHERE \"vID\"=%d;", (int) v.getPricePDay(), v.getBorrowTill(), v.getCustomer().getCustID(), v.getVID());
        }

        stat.executeUpdate(ve);
        stat.close();
    }

    
    
}
