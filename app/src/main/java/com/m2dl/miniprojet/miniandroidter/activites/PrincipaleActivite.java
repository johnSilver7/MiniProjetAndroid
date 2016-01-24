package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.m2dl.miniprojet.miniandroidter.domaine.Campus;
import com.m2dl.miniprojet.miniandroidter.domaine.Photo;
import com.m2dl.miniprojet.miniandroidter.domaine.Point;
import com.m2dl.miniprojet.miniandroidter.domaine.Tag;
import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;
import com.m2dl.miniprojet.miniandroidter.domaine.Zone;
import com.m2dl.miniprojet.miniandroidter.services.FichierService;
import com.m2dl.miniprojet.miniandroidter.services.UtilisateurService;

import java.util.Date;

/**
 * Created by yan on 15/01/16.
 */
public class PrincipaleActivite extends Activity {

    private final static int REQUETE_PRENDRE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_principale);

        // init
        Photo.PATH = getExternalFilesDir(null) + "/";
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                    Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Campus.init();
        recupererBaseDonnees();
        seConnecterSiPossible();
    }

    private void seConnecterSiPossible() {
        FichierService.init(getExternalFilesDir(null) + "");
        String login = FichierService.lireDansFichier();
        UtilisateurService.connecter(login);
    }

    private void recupererBaseDonnees() {
        //TODO recuperer la base de donnees

        Zone zone1 = new Zone("zone1", "salle1", new Point(43.568698, 1.465387));
        Zone zone2 = new Zone("zone2", "salle45", new Point(43.560804, 1.473386));
        Zone zone3 = new Zone("zone3", "salle23", new Point(43.564647, 1.457949));
        Zone zone4 = new Zone("zone4", "salle24", new Point(43.556975, 1.466417));

        Utilisateur user1 = new Utilisateur("test", "test");

        Photo photo1 = new Photo("photo1", new Date(), Tag.DEGRADATION, zone1, user1);
        Photo photo2 = new Photo("photo2", new Date(), Tag.RECYCLAGE, zone2, user1);
        Photo photo3 = new Photo("photo3", new Date(), Tag.FUITEDEAU, zone3, user1);
        Photo photo4 = new Photo("photo4", new Date(), Tag.FUITEDEAU, zone4, user1);
        Photo photo5 = new Photo("photo5", new Date(), Tag.FUITEDEAU, zone1, user1);
        Photo photo6 = new Photo("photo6", new Date(), Tag.FUITEDEAU, zone1, user1);
        Photo photo7 = new Photo("photo7", new Date(), Tag.FUITEDEAU, zone2, user1);

        Zone.ajouterZone(zone1);
        Zone.ajouterZone(zone2);
        Zone.ajouterZone(zone3);
        Zone.ajouterZone(zone4);

        Photo.ajouterPhoto(photo1);
        Photo.ajouterPhoto(photo2);
        Photo.ajouterPhoto(photo3);
        Photo.ajouterPhoto(photo4);
        Photo.ajouterPhoto(photo5);
        Photo.ajouterPhoto(photo6);
        Photo.ajouterPhoto(photo7);

        Utilisateur.ajouterUtilisateur(user1);
    }

    public void onClickAfficherCampus(View view) {
        startActivity(new Intent(this, CampusActivite.class));
    }

    public void onClickPrendrePhoto(View view) {
        if (Utilisateur.utilisateurConnecte != null) {
            startActivity(new Intent(this, PrendrePhotoActivite.class));
        } else {
            Intent intent = new Intent(this, ConnexionActivite.class);
            startActivityForResult(intent, REQUETE_PRENDRE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUETE_PRENDRE_PHOTO) {
            if (Utilisateur.utilisateurConnecte != null) {
                startActivity(new Intent(this, PrendrePhotoActivite.class));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        //TODO DEMANDER DE QUITTER
    }
}
