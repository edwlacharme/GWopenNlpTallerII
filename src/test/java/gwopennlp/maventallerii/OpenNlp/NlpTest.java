/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gwopennlp.maventallerii.OpenNlp;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author EST_1F_GC1_011
 */
public class NlpTest {
    
    public NlpTest() {
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
     * Test of validar method, of class Nlp.
     */
    @org.junit.Test
    public void testValidar() throws Exception {
        System.out.println("validar");
        String rx = "I keep the butter in the fridge.";
        Nlp instance = new Nlp();
        String expResult = "Active voice";
        String result = instance.validar(rx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        if(result.equals(expResult)) {
            System.out.println("Respuesta correcta");
        }else {
            fail("The test case is a prototype.");
        }
    }

    /**
     * Test of contarTiposVoz method, of class Nlp.
     
    @org.junit.Test
    public void testContarTiposVoz() throws Exception {
        System.out.println("contarTiposVoz");
        List<String> listaSentence = null;
        Nlp instance = new Nlp();
        String expResult = "";
        String result = instance.contarTiposVoz(listaSentence);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
}
