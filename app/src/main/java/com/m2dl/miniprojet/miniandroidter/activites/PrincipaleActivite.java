package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

        bSeDeconnecter = (Button) findViewById(R.id.activite_principale_bouton_deconnecter);

        Campus.init();
        recupererBaseDonnees();
        seConnecterSiPossible();
        afficherBoutonSeDeconnecter();
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
