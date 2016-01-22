package com.m2dl.miniprojet.miniandroidter.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by yan on 21/01/16.
 */
public class FichierService {

    private static int taille = 0;
    private static String contenu = "";
    private static String nom;

    public static void init(String chemin) {
        nom = chemin + "/pseudo.txt";
    }

    public static void ecrireDansFichier(String s) {
        contenu = s;
        FileWriter writer = null;
        try{
            writer = new FileWriter(nom, false);
            writer.write(contenu);
        }catch(Exception e) {
            // ERREUR
        } finally{
            try {
                writer.close();
            } catch (Exception e) {
                // ERREUR
            }
        }
    }

    public static String lireDansFichier() {
        contenu = "";
        try{
            BufferedReader buff = new BufferedReader(new FileReader(nom));
            try {
                String line;
                while ((line = buff.readLine()) != null) {
                    contenu += line;
                }
                taille = contenu.length();
            } finally {
                buff.close();
            }
        } catch (Exception e) {
            // ERREUR
        }
        return contenu;
    }
}
