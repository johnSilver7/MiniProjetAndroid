package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.m2dl.miniprojet.miniandroidter.domaine.Tag;
import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;
import com.m2dl.miniprojet.miniandroidter.domaine.Zone;
import com.m2dl.miniprojet.miniandroidter.services.ServeurService;
import com.m2dl.miniprojet.miniandroidter.utilitaires.CampusImage;
import com.m2dl.miniprojet.miniandroidter.utilitaires.Pointer;

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

    private DisplayMetrics dm;
    private int largeurEcran, longueurEcran;

    private List<Pointer> listePointer = new ArrayList<>();

    private final static String TEXTE_AUCUN_FILTRE = "Aucun filtre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_campus);

        // Met a jour la base de donnees
        // TODO charger la base dans un autre thread
        //ServeurService.chargerBaseDeDonnees();

        // Recuperation des dimensions de l'ecran
        dm = new DisplayMetrics();
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
        initPointers();
        afficherPointers();

        parametrerSpinners();
    }

    private void initPointers() {
        for (Zone zone: Zone.getListeZone()) {
            listePointer.add(new Pointer(this, layoutImage, dm, campusImage, zone));
        }
    }

    private void afficherPointers() {
        for (Pointer pointer: listePointer) {
            pointer.afficher(true);

            // filtrage utilisateur
            if (sUtilisateur.getSelectedItemPosition() != 0
                    && !pointer.containsPosteur((String) sUtilisateur.getSelectedItem())) {
                pointer.afficher(false);
            }
            // filtrage tag
            if (sTag.getSelectedItemPosition() != 0
                    && !pointer.containsTag((String) sTag.getSelectedItem())) {
                pointer.afficher(false);
            }
            // filtrage zone
            if (sZone.getSelectedItemPosition() != 0
                    && !pointer.getZone().equals(Zone.getZone((String) sZone.getSelectedItem()))) {
                pointer.afficher(false);
            }
        }
    }

    private void parametrerSpinners() {
        sTag.setOnItemSelectedListener(onClickSurItemSpinner);
        sZone.setOnItemSelectedListener(onClickSurItemSpinner);
        sUtilisateur.setOnItemSelectedListener(onClickSurItemSpinner);
    }

    private AdapterView.OnItemSelectedListener onClickSurItemSpinner =
            new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            afficherPointers();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // rien ?
        }
    };

    private void afficherTitre() {
        tTitre.setText(Utilisateur.utilisateurConnecte == null ?
                "Non connecté" : "Connecté: " + Utilisateur.utilisateurConnecte);
    }

    private void afficherCarte() {
        int largeurLayout = largeurEcran * 9 / 10;
        int longueurLayout = (int) ((float) largeurLayout /
                CampusImage.RAPPORT_LARGEUR_LONGUEUR_IMAGE_CAMPUS);

        layoutImage.getLayoutParams().width = largeurLayout;
        layoutImage.getLayoutParams().height = longueurLayout;

        tImageCampus.setText("");
        tImageCampus.setBackgroundDrawable(getResources().getDrawable(R.drawable.campus));
        tImageCampus.getLayoutParams().width = largeurLayout;
        tImageCampus.getLayoutParams().height = longueurLayout;

        campusImage = new CampusImage(tImageCampus, largeurLayout, longueurLayout);
    }

    private void afficherSpinnerTag() {
        List<String> listeTagSpinner = new ArrayList<>();
        listeTagSpinner.add(TEXTE_AUCUN_FILTRE);
        for (Tag tag: Tag.values()) {
            listeTagSpinner.add(Tag.toString(tag));
        }
        sTag.setAdapter(new ArrayAdapter<>(
                this, R.layout.spinner_layout, listeTagSpinner));
    }

    private void afficherSpinnerUtilisateur() {
        List<String> listeUtilisateurSpinner = new ArrayList<>();
        listeUtilisateurSpinner.add(TEXTE_AUCUN_FILTRE);
        for (Utilisateur utilisateur: Utilisateur.getListeUtilisateur()) {
            listeUtilisateurSpinner.add(utilisateur.toString());
        }
        sUtilisateur.setAdapter(new ArrayAdapter<>(
                this, R.layout.spinner_layout, listeUtilisateurSpinner));
    }

    private void afficherSpinnerZone() {
        List<String> listeZoneSpinner = new ArrayList<>();
        listeZoneSpinner.add(TEXTE_AUCUN_FILTRE);
        for (Zone zone : Zone.getListeZone()) {
            listeZoneSpinner.add(zone.toString());
        }
        sZone.setAdapter(new ArrayAdapter<>(
                this, R.layout.spinner_layout, listeZoneSpinner));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
