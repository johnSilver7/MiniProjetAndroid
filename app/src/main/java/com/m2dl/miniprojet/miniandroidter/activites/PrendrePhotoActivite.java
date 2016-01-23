package com.m2dl.miniprojet.miniandroidter.activites;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.m2dl.miniprojet.miniandroidter.domaine.Point;
import com.m2dl.miniprojet.miniandroidter.domaine.Tag;
import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;
import com.m2dl.miniprojet.miniandroidter.domaine.Zone;
import com.m2dl.miniprojet.miniandroidter.outils.DateOutils;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public class PrendrePhotoActivite extends Activity implements LocationListener {

    private static int largeurEcran, longueurEcran;

    double longitude, latitude;

    private LocationManager locationManager;
    private static File photoPrise;
    private static Drawable imagePhotoPrise;
    private final static int REQUETE_CAPTURE = 2;
    private int largeurPhoto = 0, longueurPhoto = 0;

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

        initPhoto();
        preRemplirLesChamps();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Mise à jour du location manager
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        //Si la géolocalisation est activée on s'abonne
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        desabonnementGPS();
    }

    public void abonnementGPS() throws SecurityException {
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    public void desabonnementGPS() throws SecurityException {
        locationManager.removeUpdates(this);
        tGeo.setText(GEOLOCALISE_NON);
    }

    private void preRemplirLesChamps() {
        tTitre.setText("Pseudo: " + Utilisateur.utilisateurConnecte.getPseudo());
        //tGeo.setText(GEOLOCALISE_OUI);//TODO A FAIRE
        tDate.setText(DateOutils.toStringDate(new Date().getTime()));
        sTag.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, Tag.getListeString()));
        actualiserSpinnerZone();
    }

    private void actualiserSpinnerZone() {
        List<String> listeZoneSpinner = new ArrayList<>();
        listeZoneSpinner.add(TITRE_CREATION_NOUVELLE_ZONE);
        for (Zone zone : Zone.getListeZone()) {
            listeZoneSpinner.add(zone.toString());
        }
        sZone.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listeZoneSpinner));
    }

    public void onClickPrendrePhotoRecommencer(View v) {
        prendrePhoto();
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
                    //TODO mettre salle et vrai geo
                    Zone nouvelleZone = new Zone(titreNouvelleZone,
                            "TODO", new Point(43.561993, 1.467992));//new Point(latitude, longitude));
                    nouvelleZone.sauvegarderEnBase();
                    actualiserSpinnerZone();
                    sZone.setSelection(sZone.getCount() - 1);
                }
            });
        } else {
            // Enregistre la photo sur le serveur et en local
            Zone zone = Zone.getZone((String) sZone.getSelectedItem());
            Tag tag = Tag.getTag((String) sTag.getSelectedItem());
            Date date = DateOutils.getDate(tDate.getText().toString());
            Photo photo = new Photo(photoPrise.getAbsolutePath(),
                    date, tag, zone, Utilisateur.utilisateurConnecte);

            photo.setDrawable(imagePhotoPrise);
            photo.sauvegarderEnBase();

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
            //imagePhotoPrise = null;
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initPhoto() {
        if (largeurPhoto == 0) {
            largeurPhoto = largeurEcran * 7 / 12;
            longueurPhoto = longueurEcran * 7 / 12;
            tPhoto.getLayoutParams().height = longueurPhoto;
            tPhoto.getLayoutParams().width = largeurPhoto;
        }

        tPhoto.setBackgroundDrawable(null);
        tPhoto.setBackgroundColor(Color.GRAY);
        tPhoto.setText("Prenez une photo");
    }

    private void afficherPhoto() {
        if (imagePhotoPrise == null) {
            initPhoto();
        } else {
            tPhoto.setBackgroundDrawable(imagePhotoPrise);
            tPhoto.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        imagePhotoPrise = null;
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        Log.d("GEO", this.latitude + " | " + this.longitude);
        tGeo.setText(GEOLOCALISE_OUI);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        if ("gps".equals(provider)) {
            abonnementGPS();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        if("gps".equals(provider)) {
            desabonnementGPS();
        }
    }
}
