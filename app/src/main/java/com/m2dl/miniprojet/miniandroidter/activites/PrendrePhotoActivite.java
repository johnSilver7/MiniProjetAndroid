package com.m2dl.miniprojet.miniandroidter.activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m2dl.miniprojet.miniandroidter.domaine.Photo;

import java.io.File;

/**
 * Created by yan on 15/01/16.
 */
public class PrendrePhotoActivite extends Activity {

    private static int largeurEcran, longueurEcran;

    private static File photoPrise;
    private static Drawable imagePhotoPrise;
    private final static int REQUETE_CAPTURE = 1;

    private TextView viewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_prendre_photo);

        // Recuperation des dimensions de l'ecran
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        largeurEcran = dm.widthPixels;
        longueurEcran = dm.heightPixels;

        afficherPhoto();
    }

    public void onClickPrendrePhoto(View view) {
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
        if (viewPhoto == null) {
            viewPhoto = new TextView(this);
            ((RelativeLayout) findViewById(R.id.activite_prendre_photo_layout_pere)).addView(viewPhoto);
            viewPhoto.setX((largeurEcran / 2) - (longueurEcran / 6));
            viewPhoto.setY(longueurEcran * 2 / 3);
            viewPhoto.getLayoutParams().height = largeurEcran / 3;
            viewPhoto.getLayoutParams().width = longueurEcran / 3;
        }

        if (imagePhotoPrise == null) {
            viewPhoto.setRotation(0);
            viewPhoto.setBackgroundDrawable(null);
            viewPhoto.setBackgroundColor(Color.BLUE);
            viewPhoto.setText("Aucune photo prise.");
        } else {
            viewPhoto.setRotation(270);
            viewPhoto.setBackgroundDrawable(imagePhotoPrise);
            viewPhoto.setText("");
        }
    }
}
