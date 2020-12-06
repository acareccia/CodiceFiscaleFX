package com.ilfalsodemetrio.utils.loaders;

import com.ilfalsodemetrio.utils.entity.Localita;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocalitaLoader {
    private static final String DEFAULT = "/listacomuni.txt";
    private static List<Localita> cache;

    public static List<Localita> loader() {
        if (cache != null) {
            return cache;
        }
        List<Localita> list = new ArrayList<>();
        BufferedReader br = null;
        InputStreamReader in = new InputStreamReader(
                LocalitaLoader.class.getResourceAsStream(DEFAULT));
        br = new BufferedReader(in);

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                String comune[] = line.split(";");
                list.add(new Localita(comune[6], comune[1], comune[2]));
                sb.append(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (list.size() > 0) {
            cache = list;
        }

        return list;
    }

    public static Localita pickRandom()
    {
        List<Localita> list = LocalitaLoader.loader();
        return list.get(new Random().nextInt(list.size()));
    }
}
