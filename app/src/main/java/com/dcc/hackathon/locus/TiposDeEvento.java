package com.dcc.hackathon.locus;

import java.util.ArrayList;

/**
 * Created by Alex on 8/28/2016.
 */
public class TiposDeEvento {
    private static ArrayList<String> names = new ArrayList<String>();

    static {
        names.add("");
        names.add("Festa");
        names.add("Festival");
        names.add("Palestra");
        names.add("Apresentação");
    }

    public static String get(int i) {
        return names.get(i);
    }

}
