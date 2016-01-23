package com.m2dl.miniprojet.miniandroidter.domaine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public class Zone {
    private String nom;
    private String salle;
    private Point point;


    private static List<Zone> listeZone = new ArrayList<>();

    private List<Photo> listePhoto = new ArrayList<Photo>();

    public Zone(String nom, String salle, Point point) {
        this.nom = nom;
        this.salle = salle;
        this.point = point;
    }

    public String getNom() {
        return nom;
    }

    public String getSalle() {
        return salle;
    }

    public Point getPoint() {
        return point;
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
            return nom.equals(((Zone) o).nom) && salle.equals(((Zone) o).salle);
        }
    }

    @Override
    public String toString() {
        return nom + ":" + salle;
    }

    public List<Photo> getListePhoto() {
        return this.listePhoto;
    }

    public void ajouterListePhoto(Photo photo) {
        if (!listePhoto.contains(photo)) {
            this.listePhoto.add(photo);
        }
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public static Zone getZone(String zoneToString) {
        for (Zone zone: listeZone) {
            if (zone.toString().equals(zoneToString)) {
                return zone;
            }
        }
        return null;
    }
}
