/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sknb.test.util;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sknb.util.NumberToString;

/**
 *
 * @author Sam
 */
public class NumberToStringTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testLargeNumbers() {
    	int zero = 0,
    		oneHundred = 100,
    		oneThousand = 1000;
    	
    	System.out.println("Testing basic numbers (0, 1, 100, 1000, 1,000,000, etc. to 1 quadrillion.");
    	
    	System.out.println("Testing " + zero);
    	assertEquals(NumberToString.numberToString(zero), "zero");
    	assertEquals(NumberToString.numberToOrdinalString(zero), "zeroth");
    }
    
    public void main(String args[]) {    	
        for (int i = 0; i <= 100; i++) {
        	System.out.println(NumberToString.numberToString(i) + ", " + 
        					   NumberToString.numberToOrdinalString(i));
        }
        
        System.out.println(NumberToString.numberToString(101) + ", " + 
				   		   NumberToString.numberToOrdinalString(101));
        
        System.out.println(NumberToString.numberToString(1000) + ", " + 
		   		   NumberToString.numberToOrdinalString(1000));
        
        System.out.println(NumberToString.numberToString(1001) + ", " + 
		   		   NumberToString.numberToOrdinalString(1001));
        
        System.out.println(NumberToString.numberToString(654321) + ", " + 
		   		   NumberToString.numberToOrdinalString(654321));
        
        System.out.println(NumberToString.numberToString(654321000) + ", " + 
		   		   NumberToString.numberToOrdinalString(654321000));
        
        System.out.println(NumberToString.numberToString(123456789) + ", " + 
		   		   NumberToString.numberToOrdinalString(123456789));
    }
}