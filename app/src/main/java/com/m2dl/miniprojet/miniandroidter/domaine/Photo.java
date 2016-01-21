package com.m2dl.miniprojet.miniandroidter.domaine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public class Photo {
    private String image;
    private Date date;

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public Tag getTag() {
        return tag;
    }

    public Zone getZone() {
        return zone;
    }

    public Utilisateur getPosteur() {
        return posteur;
    }

    private Tag tag;
    private Zone zone;
    private Utilisateur posteur;

    public static String PATH;
    public final static String NOM_PHOTO_TEMP = "photo.png";

    public static List<Photo> listePhoto = new ArrayList<>();

    public static Photo getPhoto(String chemin) {
        for (Photo p : listePhoto) {
            if (p.image.equals(chemin)) {
                return p;
            }
        }
        return null;
    }

}
