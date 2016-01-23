package com.m2dl.miniprojet.miniandroidter.domaine;

import android.graphics.drawable.Drawable;

import com.m2dl.miniprojet.miniandroidter.outils.ImageOutils;
import com.m2dl.miniprojet.miniandroidter.services.ServeurService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public class Photo {
    private String image;
    private Date date;
    private Tag tag;
    private Zone zone;
    private Utilisateur posteur;
    private Drawable drawable;

    public static String PATH = "";
    public final static String NOM_PHOTO_TEMP = "photo.png";

    private static List<Photo> listePhoto = new ArrayList<>();

    public Photo(String image, Date date, Tag tag, Zone zone, Utilisateur posteur) {
        this.image = image;
        this.date = date;
        this.tag = tag;
        this.zone = zone;
        this.posteur = posteur;

        this.drawable = ImageOutils.convertir(ServeurService.recuperer(image));

        zone.ajouterListePhoto(this);
    }

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

    public static Photo getPhoto(String chemin) {
        for (Photo p : listePhoto) {
            if (p.image.equals(chemin)) {
                return p;
            }
        }
        return null;
    }

    public Drawable getDrawable() {
        return this.drawable;
    }

    public static List<Photo> getListePhoto() {
        return listePhoto;
    }

    public static void ajouterPhoto(Photo photo) {
        if (!listePhoto.contains(photo)) {
            listePhoto.add(photo);
        }
    }

}
