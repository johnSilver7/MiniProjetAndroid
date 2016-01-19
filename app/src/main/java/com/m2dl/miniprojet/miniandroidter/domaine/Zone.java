package com.m2dl.miniprojet.miniandroidter.domaine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public class Zone {
    private String nom;
    private String salle;

    private static List<Zone> listeZone = new ArrayList<>();

    public Zone(String nom, String salle) {
        this.nom = nom;
        this.salle = salle;
    }

    public void sauvegarderEnBase() {
        //TODO A FAIRE
        ajouterZone(this);
    }

    public static List<Zone> getListeZone() {
        return listeZone;
    }

    public static void ajouterZone(Zone zone) {
        if (!listeZone.contains(zone)) {
            listeZone.add(zone);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Zone)) {
            return false;
        } else {
            return nom.equals(((Zone) o).nom);
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}
