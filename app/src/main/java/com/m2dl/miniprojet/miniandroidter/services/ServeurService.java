package com.m2dl.miniprojet.miniandroidter.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by yan on 15/01/16.
 */
public class ServeurService {

    // REseau

    private String requeteReseau(String LIEN) {
        String contenu = "";
        return contenu;

    }

    public boolean stockerFichier(String lienFichier) {


        String reponse = requeteReseau("https://api.instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=code");

        return true;
    }

    public static File recuperer(String chemin) {
        return null;
    }

}
