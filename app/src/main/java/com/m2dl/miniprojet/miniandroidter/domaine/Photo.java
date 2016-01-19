package com.m2dl.miniprojet.miniandroidter.domaine;

import java.util.Date;

/**
 * Created by yan on 15/01/16.
 */
public class Photo {
    private String image;
    private Date date;
    private Tag tag;
    private Zone zone;
    private Utilisateur posteur;

    public static String PATH;
    public final static String NOM_PHOTO_TEMP = "photo.png";

}
