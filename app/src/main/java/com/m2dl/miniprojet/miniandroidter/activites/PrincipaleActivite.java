package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.m2dl.miniprojet.miniandroidter.domaine.Campus;
import com.m2dl.miniprojet.miniandroidter.domaine.Photo;
import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;

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
