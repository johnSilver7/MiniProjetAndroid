package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;
import com.m2dl.miniprojet.miniandroidter.services.UtilisateurService;

/**
 * Created by yan on 15/01/16.
 */
public class ConnexionActivite extends Activity {

    private Button bRetour, bLogin;
    private EditText eMdp, eLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        setContentView(R.layout.activite_connexion);
        bRetour = (Button) findViewById(R.id.activite_connexion_bRetour);
        bLogin = (Button) findViewById(R.id.activite_connexion_bLogin);
        eMdp = (EditText) findViewById(R.id.activite_connexion_pseudo);
        eLogin = (EditText) findViewById(R.id.activite_connexion_mdp);
    }

    public void retour(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void connexion(View view) {
        String pseudo = eLogin.getText().toString();
        String mdp = eMdp.getText().toString();

        final Utilisateur utilisateur = UtilisateurService.connecter(pseudo, mdp);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (utilisateur != null) {
            builder.setMessage("Vous êtes à présent connecté");
            builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Utilisateur.utilisateurConnecte = utilisateur;
                    finish();
                }
            });
        }
        else {
            builder.setMessage("Mauvais login ou mot de passe");
            builder.setNeutralButton("ok", null);
            eMdp.setText("");
        }

        builder.show();
    }
}
