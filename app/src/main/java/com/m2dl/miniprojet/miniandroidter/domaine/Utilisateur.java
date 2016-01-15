package com.m2dl.miniprojet.miniandroidter.domaine;

/**
 * Created by yan on 15/01/16.
 */
public class Utilisateur {
    private String pseudo;
    private String mdp;

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
}
