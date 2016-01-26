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
public class Utilisateur {
    private String pseudo;
    private String mdp;

    public static Utilisateur utilisateurConnecte = null;
    private static List<Utilisateur> listeUtilisateur = new ArrayList<>();

    public Utilisateur(String pseudo, String mdp) {
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    public Utilisateur(JSONObject jsonData) {
        try {
            this.pseudo = jsonData.getString("pseudo");
            this.mdp = jsonData.getString("mdp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public static List<Utilisateur> getListeUtilisateur() {
        return listeUtilisateur;
    }

    public static void ajouterUtilisateur(Utilisateur utilisateur) {
        if (!listeUtilisateur.contains(utilisateur)) {
            listeUtilisateur.add(utilisateur);
        }
    }

    public static Utilisateur getUtilisateur(String pseudo) {
        for (Utilisateur utilisateur: listeUtilisateur) {
            if (utilisateur.pseudo.equals(pseudo)) {
                return utilisateur;
            }
        }
        return null;
    }

    public static void setListeAPartirDeJsonArray(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonData = (JSONObject) jsonArray.get(i);
                ajouterUtilisateur(new Utilisateur(jsonData));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sauvegarderEnBase() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pseudo", pseudo);
            jsonObject.put("mdp", mdp);
            ServeurService.sauvegarder("Utilisateur", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ajouterUtilisateur(this); // ajout local
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Utilisateur)) {
            return false;
        } else {
            return pseudo.equals(((Utilisateur) o).pseudo) &&
                    mdp.equals(((Utilisateur) o).mdp);
        }
    }

    @Override
    public String toString() {
        return pseudo;
    }
}
