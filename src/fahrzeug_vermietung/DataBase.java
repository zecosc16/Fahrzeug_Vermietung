/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fahrzeug_vermietung;

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

    public void addVehicle(String name, int pricePDay, LocalDate d, CarBrands c) throws SQLException {
        Statement stat = conn.createStatement();

        if (d != null) {
            String sqlString = String.format("INSERT INTO public.\"Vehicle\"(name, \"pricePDay\", \"borrowTill\", \"CarBrand\")VALUES (\'%s\',%d,TO_DATE(\'%s\','YYYY-MM-DD'),\'%s\');", name, pricePDay, d, c);
            stat.executeUpdate(sqlString);
        } else {
            String sqlString = String.format("INSERT INTO public.\"Vehicle\"(name, \"pricePDay\",  \"CarBrand\")VALUES (\'%s\',%d,\'%s\');", name, pricePDay, c);
            stat.executeUpdate(sqlString);
        }

        stat.close();

    }

    public ArrayList<Vehicle> getVehicles() throws SQLException {
        CarBrands c = CarBrands.Audi;

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        Statement stat = conn.createStatement();
        String s = "SELECT name, \"pricePDay\", \"borrowTill\", \"vID\", \"CarBrand\", \"custID\" FROM public.\"Vehicle\"";
        ResultSet res = stat.executeQuery(s);

        Vehicle v;
        while (res.next()) {

            if (res.getDate("borrowTill") == null) {
                vehicles.add(new Vehicle(res.getString("name"), c.whichBrand(res.getString("CarBrand")), res.getDouble("pricePDay")));
            } else {
                vehicles.add(new Vehicle(c.whichBrand(res.getString("CarBrand")), res.getString("name"), res.getDouble("pricePDay"), res.getDate("borrowTill").toLocalDate(), c));
            }

        }

        stat.close();
        return vehicles;
    }

    public void addCustomer(Customer c) throws SQLException {
        Statement stat = conn.createStatement();
        String sqlString = String.format("INSERT INTO public.\"Customer\"(\"telNum\", money, \"gebDat\", name)VALUES (\'%s\', %d, TO_DATE(\'%s\','YYYY-MM-DD'),\'%s\')",c.getTelNum(),(int)c.getMoney(),c.getGebDat(),c.getName());
        stat.executeUpdate(sqlString);
        stat.close();
    }
    
    

}
