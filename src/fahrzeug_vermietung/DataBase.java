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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oskar
 */
public class DataBase {

    private Connection conn;
    private static DataBase theInstance;

    private DataBase() throws SQLException {
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

    public void listAllDepartments() throws SQLException {
        Statement stat = conn.createStatement();
        String sqlString = "SELECT * FROM Vehicle";
        ResultSet res = stat.executeQuery(sqlString);
        while (res.next()) {
            System.out.println("" + res.getString("empno"));
        }
    }

    public void add() {
        Statement stat;
        try {
            stat = conn.createStatement();

            String sqlString = "INSERT INTO public.\"Vehicle\"( \"name\", \"pricePDay\", \"borrowTill\")VALUES ('halo',20,TO_DATE('20.02.1981','DD.MM.YY'))";;
            
            stat.executeUpdate(sqlString);
            
        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
        }
    }
}
