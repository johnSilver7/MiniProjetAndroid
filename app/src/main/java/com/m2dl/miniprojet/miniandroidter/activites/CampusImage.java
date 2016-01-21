package com.m2dl.miniprojet.miniandroidter.activites;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by quentin on 21/01/16.
 */
public class CampusImage {

    private TextView tImage;
    private int x, y, largeur, longueur;

    private float zoom;
    private int xZoom1, yZoom1, xZoom2, yZoom2;

    private boolean twoFingers;
    private int decalageX1, decalageY1;

    private final int largeurInit, longueurInit;
    public final static float RAPPORT_LARGEUR_LONGUEUR_IMAGE_CAMPUS = 1.12465374f;

    public CampusImage(TextView tImage, int largeurInit, int longueurInit) {
        this.x = 0;
        this.y = 0;
        this.zoom = 1f;
        this.tImage = tImage;
        this.twoFingers = false;
        this.largeur = largeurInit;
        this.longueur = longueurInit;
        this.largeurInit = largeurInit;
        this.longueurInit = longueurInit;

        init();
    }

    private void init() {
        tImage.getLayoutParams().width = largeur;
        tImage.getLayoutParams().height = longueur;
        tImage.setOnTouchListener(onTouchListenerImageCampus);
    }

    private View.OnTouchListener onTouchListenerImageCampus = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int x1 = (int) event.getX(0);
            int y1 = (int) event.getY(0);
            int x2 = (int) event.getX(1);
            int y2 = (int) event.getY(1);

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    // evite le 'recadrage automatique' lors du premier toucher
                    twoFingers = false;
                    decalageX1 = (int) event.getX(0) - x;
                    decalageY1 = (int) event.getY(0) - y;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    twoFingers = true;
                    decalageX1 = 0;
                    decalageY1 = 0;
                    initZoom(x1, y1, x2, y2);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!twoFingers) {
                        // Deplace l'image
                        setCoordonnees(x1 - decalageX1, y1 - decalageY1);
                    } else {
                        // Zoome l'image
                        zoomer(x1, y1, x2, y2);
                    }
                    break;
            }
            return true;
        }
    };

    private void initZoom(int x1, int y1, int x2, int y2) {
        xZoom1 = x1;
        yZoom1 = y1;
        xZoom2 = x2;
        yZoom2 = y2;
    }

    private void zoomer(int x1, int y1, int x2, int y2) {
        //TODO
    }

    private void setCoordonnees(int x, int y) {
        tImage.setX(this.x = x);
        tImage.setY(this.y = y);
    }

}
