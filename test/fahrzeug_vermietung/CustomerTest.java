/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fahrzeug_vermietung;

import java.time.LocalDate;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

/**
 *
 * @author oskar
 */

@RunWith(value = Parameterized.class)
public class CustomerTest {
    
    @Parameter(value=0)
    public double money;
    
    @Parameter(value=1)
    public double amount;
    
    @Parameter(value=2)
    public double expMoney;
    
    
    
    
     @Parameterized.Parameters(name = "{index}: add({0}+{1})= {2}")
    public static Iterable<Object[]> data1(){
        return Arrays.asList(new Object[][]{
            {100,20,120},
            {1000, -20000, 1000},
            {50,30,80},
            {5.0, 2.0, 7.0}
        });
    }
    
    public CustomerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    


    /**
     * Test of pay method, of class Customer.
     */
    @Test
    public void testPay() {
        System.out.println("pay");
        Customer instance = new Customer("Franz", LocalDate.now(), "+4367834", money, 0);
        instance.pay(amount);
        
        assertEquals(expMoney, instance.getMoney(),0.01);
    }


    
}
