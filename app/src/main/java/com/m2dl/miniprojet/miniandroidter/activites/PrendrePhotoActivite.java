package com.m2dl.miniprojet.miniandroidter.activites;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.m2dl.miniprojet.miniandroidter.domaine.Photo;
import com.m2dl.miniprojet.miniandroidter.domaine.Tag;
import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;
import com.m2dl.miniprojet.miniandroidter.domaine.Zone;
import com.m2dl.miniprojet.miniandroidter.outils.DateOutils;
import com.m2dl.miniprojet.miniandroidter.services.ServeurService;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public class PrendrePhotoActivite extends Activity {

    private static int largeurEcran, longueurEcran;

    private float yPosDepart;

    private static File photoPrise;
    private static Drawable imagePhotoPrise;
    private final static int REQUETE_CAPTURE = 1;

    private TextView tTitre, tPhoto, tGeo, tDate;
    private Spinner sTag, sZone;

    private final static String GEOLOCALISE_NON = "non", GEOLOCALISE_OUI = "oui",
            TITRE_CREATION_NOUVELLE_ZONE = "Nouvelle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_prendre_photo);

        // Recuperation des dimensions de l'ecran
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        largeurEcran = dm.widthPixels;
        longueurEcran = dm.heightPixels;

        // Recuperation views
        tTitre = (TextView) findViewById(R.id.activite_prendre_photo_titre);
        tPhoto = (TextView) findViewById(R.id.activite_prendre_photo_view_photo);
        tGeo = (TextView) findViewById(R.id.activite_prendre_photo_geolocalise);
        tDate = (TextView) findViewById(R.id.activite_prendre_photo_date);
        sTag = (Spinner) findViewById(R.id.activite_prendre_photo_spinner_tag);
        sZone = (Spinner) findViewById(R.id.activite_prendre_photo_spinner_zone);

        yPosDepart = tPhoto.getY();

        tPhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // have same code as onTouchEvent() (for the Activity) above

                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (imagePhotoPrise == null) {
                            prendrePhoto();
                        }
                }

                return true;
            }
        });

        afficherPhoto();
        preRemplirLesChamps();
    }

    private void preRemplirLesChamps() {
        tTitre.setText("Pseudo: " + Utilisateur.utilisateurConnecte.getPseudo());
        tGeo.setText(GEOLOCALISE_OUI);//TODO A FAIRE
        tDate.setText(DateOutils.toStringDate(new Date().getTime()));
        sTag.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, Tag.getListeString()));
        actualiserSpinnerZone();
    }

    private void actualiserSpinnerZone() {
        List<String> listeZoneSpinner = new ArrayList<>();
        listeZoneSpinner.add(TITRE_CREATION_NOUVELLE_ZONE);
        for (Zone zone: Zone.getListeZone()) {
            listeZoneSpinner.add(zone.toString());
        }
        sZone.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listeZoneSpinner));
    }

    public void onClickRecommencer(View v) {
        imagePhotoPrise = null;
        afficherPhoto();
    }


    public void onClickEnregistrer(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (imagePhotoPrise == null) {
            builder.setMessage("Vous devez prendre une photo.");
            builder.setNeutralButton("OK", null);
        } else if (tGeo.getText().equals(GEOLOCALISE_NON)) {
            builder.setMessage("Vous devez être géolocalisé.");
            builder.setNeutralButton("OK", null);
        } else if (sZone.getSelectedItem().equals(TITRE_CREATION_NOUVELLE_ZONE)) {
            final EditText eTitreZone = new EditText(this);
            builder.setView(getFormulaireCreationZone(eTitreZone));
            builder.setNegativeButton("Annuler", null);
            builder.setPositiveButton("Créer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO Verifier le titre de la zone entree
                    String titreNouvelleZone = eTitreZone.getText().toString();
                    Zone nouvelleZone = new Zone(titreNouvelleZone, "utile ?");
                    nouvelleZone.sauvegarderEnBase();
                    actualiserSpinnerZone();
                    sZone.setSelection(sZone.getCount() - 1);
                }
            });
        } else {
            //TODO enregistrer l'image et/ou la zone
            ServeurService serveurService = new ServeurService();
            boolean sucess = serveurService.stockerFichier("toto");

            builder.setMessage("Endroit enregistré avec succès !");
            builder.setNegativeButton("Retour", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PrendrePhotoActivite.this.onBackPressed();
                }
            });
            builder.setPositiveButton("Accéder au campus", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(PrendrePhotoActivite.this, CampusActivite.class));
                    PrendrePhotoActivite.this.onBackPressed();
                }
            });
        }

        builder.show();
    }

    private View getFormulaireCreationZone(EditText eTitreZone) {
        final float tailleTexte = 20f;

        // Layout pere
        LinearLayout layoutPere = new LinearLayout(this);
        layoutPere.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutPere.setOrientation(LinearLayout.VERTICAL);
        layoutPere.setGravity(Gravity.CENTER);

        // Titre du formulaire
        TextView tTitreFormulaire = new TextView(this);
        tTitreFormulaire.setTextColor(Color.BLACK);
        tTitreFormulaire.setTextSize(tailleTexte);
        tTitreFormulaire.setGravity(Gravity.CENTER);
        tTitreFormulaire.setText("Création d'une nouvelle zone");

        // Tableau du formulaire
        TableLayout tableauFormulaire = new TableLayout(this);
        tableauFormulaire.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tableauFormulaire.setGravity(Gravity.CENTER);

        // Ligne du titre de la zone
        TableRow ligne1 = new TableRow(this);
        ligne1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView tTitreZone = new TextView(this);
        tTitreZone.setTextColor(Color.BLACK);
        tTitreZone.setTextSize(tailleTexte);
        tTitreZone.setText("Titre:");

        eTitreZone.setTextColor(Color.BLACK);
        eTitreZone.setTextSize(tailleTexte);
        eTitreZone.setHint("Titre de la nouvelle zone");

        ligne1.addView(tTitreZone);
        ligne1.addView(eTitreZone);

        // Ajouts au pere
        layoutPere.addView(tTitreFormulaire);
        layoutPere.addView(ligne1);
        return layoutPere;
    }

    private void prendrePhoto() {
        photoPrise = new File(Photo.PATH + Photo.NOM_PHOTO_TEMP);
        Uri fileUri = Uri.fromFile(photoPrise);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUETE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUETE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPrise.getAbsolutePath());
            imagePhotoPrise = new BitmapDrawable(getResources(), bitmap);
            afficherPhoto();
        } else {
            imagePhotoPrise = null;
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void afficherPhoto() {
        int largeurPhoto = largeurEcran * 7 / 12;
        int longueurPhoto = longueurEcran * 7 / 12;

        if (imagePhotoPrise == null) {
            tPhoto.setY(longueurEcran / 12);
            tPhoto.setRotation(0);
            tPhoto.getLayoutParams().height = longueurPhoto;
            tPhoto.getLayoutParams().width = largeurPhoto;
            tPhoto.setBackgroundDrawable(null);
            tPhoto.setBackgroundColor(Color.GRAY);
            tPhoto.setText("Prendre une photo");
        } else {
            tPhoto.setRotation(270);
            tPhoto.setY(tPhoto.getY() + ((longueurPhoto / 2) - (largeurPhoto / 2)));
            tPhoto.getLayoutParams().height = largeurPhoto;
            tPhoto.getLayoutParams().width = longueurPhoto;
            tPhoto.setBackgroundDrawable(imagePhotoPrise);
            tPhoto.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        imagePhotoPrise = null;
        finish();
    }
}
