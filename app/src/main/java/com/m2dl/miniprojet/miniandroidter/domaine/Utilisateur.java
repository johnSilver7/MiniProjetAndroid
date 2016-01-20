package com.m2dl.miniprojet.miniandroidter.domaine;

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

    @Override
    public String toString() {
        return pseudo;
    }
}
