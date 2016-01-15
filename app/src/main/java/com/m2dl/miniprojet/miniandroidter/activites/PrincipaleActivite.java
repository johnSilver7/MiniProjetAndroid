package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by yan on 15/01/16.
 */
public class PrincipaleActivite extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        setContentView(R.layout.activite_principale);

    }

    public void onClickSinscrire(View view) {
        startActivity(new Intent(this, InscriptionActivite.class));
    }

    public void seConnecter(View view) {
        startActivity(new Intent(this, ConnexionActivite.class));
    }
}
