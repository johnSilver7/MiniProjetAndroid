package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;
import com.m2dl.miniprojet.miniandroidter.services.FichierService;
import com.m2dl.miniprojet.miniandroidter.services.UtilisateurService;

/**
 * Created by yan on 15/01/16.
 */
public class ConnexionActivite extends Activity {

    private EditText eMdp, eLogin;
    private CheckBox cSession;
    private TextView pasEncoreInscrit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_connexion);

        eLogin = (EditText) findViewById(R.id.activite_connexion_pseudo);
        eMdp = (EditText) findViewById(R.id.activite_connexion_mdp);
        cSession = (CheckBox) findViewById(R.id.activite_connexion_box_souvenir);

        pasEncoreInscrit = (TextView) findViewById(R.id.activite_connexion_textInscription);

        pasEncoreInscrit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // have same code as onTouchEvent() (for the Activity) above

                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        pasEncoreInscrit();

                }

                return true;
            }
        });
    }

    public void garderSessionOuverte() {
        if (cSession.isChecked()) {
            FichierService.ecrireDansFichier(eLogin.getText().toString());
        } else {
            FichierService.ecrireDansFichier("");
        }
    }

    public void retour(View view) {
        onBackPressed();
    }

    public void pasEncoreInscrit() {
        startActivity(new Intent(this, InscriptionActivite.class));
    }

    public void connexion(View view) {
        String pseudo = eLogin.getText().toString();
        String mdp = eMdp.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final Utilisateur utilisateur = UtilisateurService.connecter(pseudo, mdp);

        if (utilisateur != null) {
            garderSessionOuverte();
            Utilisateur.utilisateurConnecte = utilisateur;
            builder.setMessage("Vous êtes à présent connecté");
            builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
