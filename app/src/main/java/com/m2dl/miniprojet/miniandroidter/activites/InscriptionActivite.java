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
public class InscriptionActivite extends Activity {

    private EditText ePseudo, eMdp, eMdp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_inscription);

        ePseudo = (EditText) findViewById(R.id.activite_insciption_ePseudo);
        eMdp = (EditText) findViewById(R.id.activite_insciption_eMdp);
        eMdp2 = (EditText) findViewById(R.id.activite_inscription_eMdp2);
    }

    public void validerInscription(View view) {
        String pseudo = ePseudo.getText().toString();
        String mdp = eMdp.getText().toString();
        String mdp2 = eMdp2.getText().toString();
        Utilisateur utilisateur = new Utilisateur(pseudo, mdp);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (!mdp.equals(mdp2)) {
            builder.setMessage("Mots de passe différents.");
            builder.setNeutralButton("ok", null);
        } else if (UtilisateurService.enregistrer(utilisateur)) {
            builder.setMessage("Inscription réussie !");
            builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    InscriptionActivite.this.onBackPressed();
                }
            });
        } else {
            builder.setMessage("Echec d'inscription !");
            builder.setNeutralButton("ok", null);
        }
        builder.show();
    }

    public void annulerInscription(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
