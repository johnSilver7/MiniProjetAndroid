package com.m2dl.miniprojet.miniandroidter.services;

import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;

/**
 * Created by yan on 15/01/16.
 */
public class UtilisateurService {

    public static boolean enregistrer(Utilisateur utilisateur) {
        return true;
    }

    public static Utilisateur connecter(String pseudo, String mdp) {
        for (Utilisateur utilisateur: Utilisateur.getListeUtilisateur()) {
            if (utilisateur.equals(new Utilisateur(pseudo, mdp))) {
                return utilisateur;
            }
        }
        return null;
    }

    public static Utilisateur connecter(String pseudo) {
        for (Utilisateur utilisateur: Utilisateur.getListeUtilisateur()) {
            if (utilisateur.getPseudo().equals(pseudo)) {
                return utilisateur;
            }
        }
        return null;
    }

}
