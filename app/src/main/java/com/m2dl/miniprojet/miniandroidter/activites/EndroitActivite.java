package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.m2dl.miniprojet.miniandroidter.domaine.Photo;
import com.m2dl.miniprojet.miniandroidter.domaine.Zone;
import com.m2dl.miniprojet.miniandroidter.outils.DateOutils;
import com.m2dl.miniprojet.miniandroidter.outils.ImageOutils;
import com.m2dl.miniprojet.miniandroidter.services.ServeurService;

import java.util.List;


/**
 * Created by quentin on 19/01/16.
 */
public class EndroitActivite extends Activity {

    private Zone zone;
    private Photo photo;

    private Button bZonePrec, bZoneSuiv, bPhotoPrec, bPhotoSuiv;
    private TextView tDate, tTag, tZone, tPosteur, tSalle;
    private ImageView tPhotoPrise;

    private List<Photo> listePhoto;
    private List<Zone> listeZone;

    public static final String CHEMIN_PHOTO_ENDROIT = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_endroit);

        tDate = (TextView) findViewById(R.id.activite_endroit_date);
        tTag = (TextView) findViewById(R.id.activite_endroit_tag);
        tZone = (TextView) findViewById(R.id.activite_endroit_zone);
        tPosteur = (TextView) findViewById(R.id.activite_endroit_utilisateur);
        tSalle = (TextView) findViewById(R.id.activite_endroit_salle);
        tPhotoPrise = (ImageView) findViewById(R.id.activite_endroit_image);
        bZonePrec = (Button) findViewById(R.id.activite_endroit_zone_precedente);
        bZoneSuiv = (Button) findViewById(R.id.activite_endroit_zone_suivante);
        bPhotoPrec = (Button) findViewById(R.id.activite_endroit_photo_precedent);
        bPhotoSuiv = (Button) findViewById(R.id.activite_endroit_photo_suivante);

        // Une activite m'a passe une photo
        String cheminPhoto = getIntent().getExtras().getString(CHEMIN_PHOTO_ENDROIT);
        photo = Photo.getPhoto(cheminPhoto);

        if (photo == null) {
            Log.d("photo", "null");
        }

        initPhoto();

        tZone.setText(photo.getZone().toString());
        zone = photo.getZone();
        tSalle.setText(zone.getSalle());
        afficherInformationPhoto(photo);

        listePhoto = zone.getListePhoto();
        listeZone = Zone.getListeZone();

        actualiserBoutons();
    }

    private void initPhoto() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        tPhotoPrise.getLayoutParams().height = dm.heightPixels * 7 / 12;
        tPhotoPrise.getLayoutParams().width = dm.widthPixels * 7 / 12;
    }

    private void actualiserBoutons() {
        bZonePrec.setEnabled(Zone.getListeZone().size() > 1);
        bZoneSuiv.setEnabled(Zone.getListeZone().size() > 1);
        bPhotoPrec.setEnabled(zone.getListePhoto().size() > 1);
        bPhotoSuiv.setEnabled(zone.getListePhoto().size() > 1);
    }

    private void afficherInformationPhoto(Photo photo) {
        tDate.setText(DateOutils.toStringDate(photo.getDate().getTime()));
        tTag.setText(photo.getTag().toString());
        tPosteur.setText(photo.getPosteur().toString());
        tPhotoPrise.setBackgroundColor(Color.GRAY);
        //TODO tPhotoPrise.setBackgroundDrawable(photo.getDrawable());
        actualiserBoutons();
    }

    private void afficherInformationZone(Zone zone) {
        tZone.setText(zone.getNom());
        tSalle.setText(zone.getSalle());
        listePhoto = zone.getListePhoto();
        photo = listePhoto.get(0);
        afficherInformationPhoto(photo);
        actualiserBoutons();
    }

    public void getPhotoPrecedente(View v) {
        int idPhoto = listePhoto.indexOf(photo);
        if (idPhoto - 1 >= 0) {
            photo = listePhoto.get(idPhoto - 1);
        } else {
            photo = listePhoto.get(zone.getListePhoto().size() - 1);
        }
        afficherInformationPhoto(photo);
    }

    public void getPhotoSuivante(View v) {
        int idPhoto = listePhoto.indexOf(photo);

        if (idPhoto + 1 < listePhoto.size()) {
            photo = listePhoto.get(idPhoto + 1);
        } else {
            photo = listePhoto.get(0);
        }
        afficherInformationPhoto(photo);
    }

    public void getZonePrecedente(View v) {
        int idZone = listeZone.indexOf(zone);

        if (idZone - 1 >= 0) {
            zone = listeZone.get(idZone - 1);
        } else {
            zone = listeZone.get(listeZone.size() - 1);
        }
        afficherInformationZone(zone);
    }

    public void getZoneSuivante(View v) {
        int idZone = listeZone.indexOf(zone);

        if (idZone + 1 < listeZone.size()) {
            zone = listeZone.get(idZone + 1);
        } else {
            zone = listeZone.get(0);
        }
        afficherInformationZone(zone);
    }


}
