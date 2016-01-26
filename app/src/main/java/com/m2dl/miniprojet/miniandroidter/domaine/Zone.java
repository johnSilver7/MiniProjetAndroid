package com.m2dl.miniprojet.miniandroidter.domaine;

import com.m2dl.miniprojet.miniandroidter.services.ServeurService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public Zone(JSONObject jsonData) {
        try {
            this.nom = jsonData.getString("nom");
            this.salle = jsonData.getString("salle");
            double latitude = Double.parseDouble(jsonData.getString("latitude"));
            double longitude = Double.parseDouble(jsonData.getString("longitude"));
            this.point = new Point(latitude, longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nom", nom);
            jsonObject.put("salle", salle);
            jsonObject.put("latitude", point.getX());
            jsonObject.put("longitude", point.getY());
            ServeurService.sauvegarder("Zone", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ajouterZone(this); // ajout local
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

    public static void setListeAPartirDeJsonArray(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonData = (JSONObject) jsonArray.get(i);
                ajouterZone(new Zone(jsonData));
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
