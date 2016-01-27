package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m2dl.miniprojet.miniandroidter.domaine.Campus;
import com.m2dl.miniprojet.miniandroidter.domaine.Photo;
import com.m2dl.miniprojet.miniandroidter.domaine.Point;
import com.m2dl.miniprojet.miniandroidter.domaine.Tag;
import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;
import com.m2dl.miniprojet.miniandroidter.domaine.Zone;
import com.m2dl.miniprojet.miniandroidter.services.FichierService;
import com.m2dl.miniprojet.miniandroidter.services.ServeurService;
import com.m2dl.miniprojet.miniandroidter.services.UtilisateurService;

import java.util.Date;

/**
 * Created by yan on 15/01/16.
 */
public class PrincipaleActivite extends Activity {

    private final static int DELAI_MISE_A_JOUR_BDD = 1000;
    private final static int REQUETE_PRENDRE_PHOTO = 1;

    private Button bSeDeconnecter;
    private TextView textViewChargement;
    private RelativeLayout layoutPere;

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

        layoutPere = (RelativeLayout) findViewById(R.id.activite_principale_layout_pere);
        bSeDeconnecter = (Button) findViewById(R.id.activite_principale_bouton_deconnecter);

        afficherChargement(true);

        Campus.init();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recupererBaseDonnees();
                afficherChargement(false);
                seConnecterSiPossible();
                afficherBoutonSeDeconnecter();
            }
        }, 100);
    }

    private void afficherChargement(boolean charge) {
        for (int i = 0; i < layoutPere.getChildCount(); i++) {
            layoutPere.getChildAt(i).
                    setVisibility(charge ? View.INVISIBLE : View.VISIBLE);
            layoutPere.getChildAt(i).setEnabled(!charge);
        }
        if (charge) {
            // Recuperation des dimensions de l'ecran
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            textViewChargement = new TextView(this);
            layoutPere.addView(textViewChargement);
            textViewChargement.getLayoutParams().width = dm.widthPixels;
            textViewChargement.getLayoutParams().height = dm.heightPixels;
            textViewChargement.setGravity(Gravity.CENTER);
            textViewChargement.setTextSize(40);
            textViewChargement.setTypeface(null, Typeface.BOLD);
            textViewChargement.setTextColor(Color.WHITE);
            textViewChargement.setX(0);
            textViewChargement.setY(0);
            textViewChargement.setText("Chargement ...");
        } else {
            layoutPere.removeView(textViewChargement);
        }
    }

    private void afficherBoutonSeDeconnecter() {
        boolean affiche = (Utilisateur.utilisateurConnecte != null);
        bSeDeconnecter.setVisibility(affiche ? View.VISIBLE : View.INVISIBLE);
        bSeDeconnecter.setEnabled(affiche);
    }

    private void seConnecterSiPossible() {
        FichierService.init(getExternalFilesDir(null) + "");
        String login = FichierService.lireDansFichier();
        Utilisateur.utilisateurConnecte = UtilisateurService.connecter(login);
    }

    private void recupererBaseDonnees() {
        ServeurService.chargerBaseDeDonnees();
    }

    public void onClickAfficherCampus(View view) {
        startActivity(new Intent(this, CampusActivite.class));
    }

    public void onClickSeDeconnecter(View view) {
        Utilisateur.utilisateurConnecte = null;
        afficherBoutonSeDeconnecter();
        FichierService.ecrireDansFichier("");
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
        afficherBoutonSeDeconnecter();
    }

    @Override
    public void onBackPressed() {
        // rien
    }
}
