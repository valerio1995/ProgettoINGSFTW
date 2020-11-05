package com.example.cv19;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class JUnitTest {
    Registrazione myVar;
    AggiungiRecensione var;

    @Test
    public void test_normalizzaParola(){
        //Arrange
        myVar = new Registrazione();
        //Asserts
        assertEquals("Test", myVar.normalizza_parola("test"));
        assertEquals(" ", myVar.normalizza_parola(" "));
        assertEquals("@12/", myVar.normalizza_parola("@12/"));
        assertEquals("Test con spazi", myVar.normalizza_parola("test con spazi"));
    }

    @Test
    public void test_validitaTesto(){
        //Arrange
        var = new AggiungiRecensione();
        //Asserts
        assertEquals(true, var.validita_testo("testo"));
        assertEquals(false, var.validita_testo("testo/80@"));
        assertEquals(true, var.validita_testo("test con spazi"));
        assertEquals(false, var.validita_testo("test con @spazi"));
    }
}
