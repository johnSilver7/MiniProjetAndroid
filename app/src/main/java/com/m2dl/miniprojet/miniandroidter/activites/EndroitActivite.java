package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.m2dl.miniprojet.miniandroidter.domaine.Photo;
import com.m2dl.miniprojet.miniandroidter.outils.DateOutils;
import com.m2dl.miniprojet.miniandroidter.outils.ImageOutils;
import com.m2dl.miniprojet.miniandroidter.services.ServeurService;


/**
 * Created by quentin on 19/01/16.
 */
public class EndroitActivite extends Activity {

    private Photo photo;

    private TextView tDate, tTag, tZone, tPosteur;
    private ImageView tPhotoPrise;

    private static final String CHEMIN_PHOTO_ENDROIT = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_endroit);

        tDate = (TextView) findViewById(R.id.activite_endroit_date);
        tTag = (TextView) findViewById(R.id.activite_endroit_tag);
        tZone = (TextView) findViewById(R.id.activite_endroit_zone);
        tPosteur = (TextView) findViewById(R.id.activite_endroit_posteur);
        tPhotoPrise = (ImageView) findViewById(R.id.activite_endroit_photoPrise);

        // Une activite m'a passe une photo
        String cheminPhoto = getIntent().getExtras().getString(CHEMIN_PHOTO_ENDROIT);
        photo = Photo.getPhoto(cheminPhoto);
        tDate.setText("Date prise:" + DateOutils.toStringDate(photo.getDate().getTime()));
        tTag.setText("TAG:" + photo.getTag().toString());
        tZone.setText("Zone:" + photo.getZone().toString());
        tPosteur.setText("Posté par:" + photo.getPosteur());
        tPhotoPrise.setBackgroundDrawable(ImageOutils.convertir(ServeurService.recuperer(cheminPhoto)));
    }
}
