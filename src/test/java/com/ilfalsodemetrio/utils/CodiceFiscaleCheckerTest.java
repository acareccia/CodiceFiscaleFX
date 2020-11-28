package com.ilfalsodemetrio.utils;

import com.ilfalsodemetrio.utils.entity.Localita;
import com.ilfalsodemetrio.utils.entity.Persona;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;


public class CodiceFiscaleCheckerTest {

    @Test
    public void testGetAllValidCodiciFiscali() throws Exception{
        Localita comune = new Localita();
        comune.setCodiceFiscale("F205");
        comune.setDescrizione("MILANO");

        Persona persona = new Persona();
        persona.setCognome("TEST");
        persona.setNome("TEST");
        persona.setSesso("M");
        persona.setDataNascita(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1990"));
        persona.setLocalita(comune);

        List<String> fiscali = CodiceFiscaleChecker.getAllValidCodiciFiscali(persona);
        System.out.println(Arrays.toString(fiscali.toArray()));

        Assert.assertEquals(128, fiscali.size());

    }
}
