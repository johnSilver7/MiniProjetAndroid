package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.m2dl.miniprojet.miniandroidter.domaine.Tag;
import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;
import com.m2dl.miniprojet.miniandroidter.domaine.Zone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public class CampusActivite extends Activity {

    private TextView tTitre;
    private Spinner sTag, sUtilisateur, sZone;

    private TextView tImageCampus;
    private RelativeLayout layoutImage;
    private CampusImage campusImage;

    private int largeurEcran, longueurEcran;

    private final static String TEXTE_AUCUN_FILTRE = "Aucun filtre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_campus);

        // Recuperation des dimensions de l'ecran
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        largeurEcran = dm.widthPixels;
        longueurEcran = dm.heightPixels;

        // Recuperation des composants
        tTitre = (TextView) findViewById(R.id.activite_campus_tTitre);
        sTag = (Spinner) findViewById(R.id.activite_campus_spinner_tag);
        sZone = (Spinner) findViewById(R.id.activite_campus_spinner_zone);
        tImageCampus = (TextView) findViewById(R.id.activite_campus_image_campus);
        layoutImage = (RelativeLayout) findViewById(R.id.activite_campus_layout_image);
        sUtilisateur = (Spinner) findViewById(R.id.activite_campus_spinner_utilisateur);

        // Affichage des informations
        afficherTitre();
        afficherCarte();
        afficherSpinnerTag();
        afficherSpinnerUtilisateur();
        afficherSpinnerZone();
    }

    private void afficherTitre() {
        //TODO ajouter geolocalise
        tTitre.setText("Geolocalisé: <TODO>");
    }

    private void afficherCarte() {
        int largeurLayout = largeurEcran * 9 / 10;
        int longueurLayout = (int) ((float) largeurLayout /
                CampusImage.RAPPORT_LARGEUR_LONGUEUR_IMAGE_CAMPUS);

        layoutImage.getLayoutParams().width = largeurLayout;
        layoutImage.getLayoutParams().height = longueurLayout;

        tImageCampus.setText("");
        tImageCampus.setBackgroundDrawable(getResources().getDrawable(R.drawable.campus));

        campusImage = new CampusImage(tImageCampus, largeurLayout, longueurLayout);
    }

    private void afficherSpinnerTag() {
        List<String> listeTagSpinner = new ArrayList<>();
        listeTagSpinner.add(TEXTE_AUCUN_FILTRE);
        for (Tag tag: Tag.values()) {
            listeTagSpinner.add(Tag.toString(tag));
        }
        sTag.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listeTagSpinner));
    }

    private void afficherSpinnerUtilisateur() {
        List<String> listeUtilisateurSpinner = new ArrayList<>();
        listeUtilisateurSpinner.add(TEXTE_AUCUN_FILTRE);
        for (Utilisateur utilisateur: Utilisateur.getListeUtilisateur()) {
            listeUtilisateurSpinner.add(utilisateur.toString());
        }
        sUtilisateur.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listeUtilisateurSpinner));
    }

    private void afficherSpinnerZone() {
        List<String> listeZoneSpinner = new ArrayList<>();
        listeZoneSpinner.add(TEXTE_AUCUN_FILTRE);
        for (Zone zone : Zone.getListeZone()) {
            listeZoneSpinner.add(zone.toString());
        }
        sZone.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listeZoneSpinner));
    }

    @Override
    public void onBackPressed() {
        //TODO autre truc a faire ?
        finish();
    }
}
