package com.m2dl.miniprojet.miniandroidter.activites;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.m2dl.miniprojet.miniandroidter.outils.MathOutils;

/**
 * Created by quentin on 21/01/16.
 */
public class CampusImage {

    private TextView tImage;

    private int x, y, largeur, longueur;

    private float zoom;
    private int distanceInitZoom;
    private static final float ZOOM_MAX = 10f;
    private static final float ZOOM_MIN = 0.5f;

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

            int x1, y1, x2 = 0, y2 = 0;
            x1 = (int) event.getX(0);
            y1 = (int) event.getY(0);
            if (event.getPointerCount() > 1) {
                x2 = (int) event.getX(1);
                y2 = (int) event.getY(1);
            }

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    // evite le 'recadrage automatique' lors du premier toucher
                    twoFingers = false;
                    decalageX1 = x1 - x;
                    decalageY1 = y1 - y;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    twoFingers = true;
                    initZoom(x1, y1, x2, y2);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!twoFingers) {
                        setCoordonnees(x1 - decalageX1, y1 - decalageY1);
                    } else {
                        zoomer(x1, y1, x2, y2);
                    }
                    break;
            }
            return true;
        }
    };

    private void initZoom(int x1, int y1, int x2, int y2) {
        distanceInitZoom = MathOutils.getDistance(x1, y1, x2, y2);
    }

    private void zoomer(int x1, int y1, int x2, int y2) {
        int distance = MathOutils.getDistance(x1, y1, x2, y2) - distanceInitZoom;
        int distanceAbsolu = Math.abs(distance);

        if (distance > 0) {
            // zoomer
            //zoom *= ((float) (distanceAbsolu + largeurInit) / (float) largeurInit);
            zoom *= 1.05f;
            if (zoom > ZOOM_MAX) {
                zoom = ZOOM_MAX;
            }
        } else {
            // dezoomer
            //zoom /= ((float) (distanceAbsolu + largeurInit) / (float) largeurInit);
            zoom /= 1.05f;
            if (zoom < ZOOM_MIN) {
                zoom = ZOOM_MIN;
            }
        }

        tImage.requestLayout();
        largeur = (int) ((float) largeurInit * zoom);
        longueur = (int) ((float) largeur / RAPPORT_LARGEUR_LONGUEUR_IMAGE_CAMPUS);
        tImage.getLayoutParams().width = largeur;
        tImage.getLayoutParams().height = longueur;
    }

    private void setCoordonnees(int x, int y) {
        tImage.setX(x);
        tImage.setY(y);
    }

}
