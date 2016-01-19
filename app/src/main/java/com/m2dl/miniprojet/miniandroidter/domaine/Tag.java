package com.m2dl.miniprojet.miniandroidter.domaine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public enum Tag {
    RECYCLAGE, DEGRADATION, FUITEDEAU;

    public static String toString(Tag tag) {
        switch (tag) {
            case RECYCLAGE: return "Recyclage";
            case DEGRADATION: return "Dégradation";
            case FUITEDEAU: return "Fuite d'eau";
        }
        return null;
    }

    public static List<String> getListeString() {
        List<String> liste = new ArrayList<>();
        for (Tag tag: values()) {
            liste.add(toString(tag));
        }
        return liste;
    }

}
