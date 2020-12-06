package com.ilfalsodemetrio.utils.loaders;

import com.ilfalsodemetrio.utils.entity.Localita;
import com.ilfalsodemetrio.utils.entity.Persona;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PersonaLoader {
    private static final String DEFAULT = "/names.txt";
    private static List<String> cache;

    public static Persona rand() {
        long aDay = TimeUnit.DAYS.toMillis(1);
        long now = new Date().getTime();
        Date hundredYearsAgo = new Date(now - aDay * 365 * 100);
        Date tenDaysAgo = new Date(now - aDay * 10);

        Persona persona = new Persona();
        persona.setNome(pickRandom());
        persona.setCognome(pickRandom());
        persona.setSesso(new Random().nextBoolean()?Persona.Sesso.F.name():Persona.Sesso.M.name());
        persona.setDataNascita(between(hundredYearsAgo,tenDaysAgo));
        persona.setLocalita(LocalitaLoader.pickRandom());
        return persona;
    }

    private static String pickRandom()
    {
        InputStreamReader in = new InputStreamReader(PersonaLoader.class.getResourceAsStream(DEFAULT));
        String result = null;
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(in); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }

        return result;
    }

    private static Date between(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);
        return new Date(randomMillisSinceEpoch);
    }
}
