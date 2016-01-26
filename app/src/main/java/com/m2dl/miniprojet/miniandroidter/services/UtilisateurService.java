package com.m2dl.miniprojet.miniandroidter.services;

import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;

/**
 * Created by yan on 15/01/16.
 */
public class UtilisateurService {

    public static boolean enregistrer(Utilisateur utilisateur) {
        for (Utilisateur utilisateurEnregistre: Utilisateur.getListeUtilisateur()) {
            if (utilisateurEnregistre.getPseudo().equals(utilisateur)) {
                return false;
            }
        }
        utilisateur.sauvegarderEnBase();
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
        return Utilisateur.getUtilisateur(pseudo);
    }

}
