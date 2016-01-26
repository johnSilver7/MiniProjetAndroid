package com.m2dl.miniprojet.miniandroidter.domaine;

import android.graphics.drawable.Drawable;

import com.m2dl.miniprojet.miniandroidter.services.ServeurService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        zone.ajouterListePhoto(this);
    }

    public Photo(JSONObject jsonData) {
        try {
            this.image = jsonData.getString("image");
            this.date = new Date(Long.parseLong(jsonData.getString("date")));
            this.tag = Tag.getTag(jsonData.getString("tag"));
            this.zone = Zone.getZone(jsonData.getString("zone"));
            this.posteur = Utilisateur.getUtilisateur(jsonData.getString("posteur"));
            // TODO recuperer drawable de la photo
            this.zone.ajouterListePhoto(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public void sauvegarderEnBase() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image", image);
            jsonObject.put("date", date.getTime());
            jsonObject.put("tag", Tag.toString(tag));
            jsonObject.put("zone", zone.toString());
            jsonObject.put("posteur", posteur.getPseudo());
            ServeurService.sauvegarder("Photo", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ajouterPhoto(this); // ajout local
    }

    public static void setListeAPartirDeJsonArray(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonData = (JSONObject) jsonArray.get(i);
                ajouterPhoto(new Photo(jsonData));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

}
