package com.m2dl.miniprojet.miniandroidter.services;

import android.util.Log;

import com.m2dl.miniprojet.miniandroidter.domaine.Photo;
import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;
import com.m2dl.miniprojet.miniandroidter.domaine.Zone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * Created by yan on 15/01/16.
 */
public class ServeurService {

    private final static String URL = "http://miniprojetandroidjyq.orgfree.com/script.php";

    public static void sauvegarder(String classe, JSONObject jsonObject) {
        String lien = URL + "?choix=set&classe=" + classe +
                "&json=" + URLEncoder.encode(jsonObject.toString());

        if (classe.equals("Photo")) {
            // TODO traiter le cas ou il faut envoyer la photo
        }

        // TODO traiter la reponse du serveur
        String reponse = envoyerRequeteServeur(lien);
        Log.d("json ENVOYE", jsonObject.toString());
        Log.d("reponse SERVEUR", reponse);
    }


    // Charger la base de donnees localement
    public static void chargerBaseDeDonnees() {
        JSONArray utilisateurs = getJsonArrayDe("Utilisateur");
        JSONArray zones = getJsonArrayDe("Zone");
        JSONArray photos = getJsonArrayDe("Photo");

        Utilisateur.setListeAPartirDeJsonArray(utilisateurs);
        Zone.setListeAPartirDeJsonArray(zones);
        Photo.setListeAPartirDeJsonArray(photos);
    }

    private static String envoyerRequeteServeur(String lien) {
        String result;
        InputStream is;

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(lien);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        try{
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            return result;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JSONArray getJsonArrayDe(String classe) {
        String lien = URL + "?choix=getListe&classe=" + classe;
        String json = envoyerRequeteServeur(lien);
        // mettre au propre
        int i;
        for (i = 0; i < json.length() &&
                !json.substring(i, i + 1).equals("["); i++) {
            // Rien
        }
        if (i >= json.length()) {
            json = "[]";
        } else {
            json = json.substring(i);
        }

        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
