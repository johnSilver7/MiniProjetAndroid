package com.m2dl.miniprojet.miniandroidter.utilitaires;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.m2dl.miniprojet.miniandroidter.activites.EndroitActivite;
import com.m2dl.miniprojet.miniandroidter.activites.R;
import com.m2dl.miniprojet.miniandroidter.domaine.Campus;
import com.m2dl.miniprojet.miniandroidter.domaine.Point;
import com.m2dl.miniprojet.miniandroidter.domaine.Zone;

/**
 * Created by quentin on 23/01/16.
 */
public class Pointer extends View implements View.OnTouchListener {

    private Zone zone;
    private int largeur, longueur;
    private final static double RAPPORT_LARGEUR_LONGUEUR = 0.69273743;

    public Pointer(Context context, RelativeLayout layout,
                   DisplayMetrics ecran, CampusImage campusImage, Zone zone) {
        super(context);
        this.zone = zone;
        layout.addView(this);

        init(ecran, campusImage);
    }

    private void init(DisplayMetrics ecran, CampusImage campusImage) {
        // dimension
        largeur = ecran.widthPixels / 10;
        longueur = (int) ((double) largeur / RAPPORT_LARGEUR_LONGUEUR);
        super.getLayoutParams().width = largeur;
        super.getLayoutParams().height = longueur;

        // image
        super.setBackgroundDrawable(getResources().getDrawable(R.drawable.pointer));

        // position
        Point pointSurImage = Campus.getPointSurImage(zone.getPoint());
        double xPointer = (pointSurImage.getX() * campusImage.getLargeur())
                / Campus.LARGEUR_IMAGE_CAMPUS;
        double yPointer = (pointSurImage.getY() * campusImage.getLongueur())
                / Campus.LONGUEUR_IMAGE_CAMPUS;
        super.setX((int) xPointer - (largeur / 2));
        super.setY((int) yPointer - longueur);

        // listener
        super.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(getContext(), EndroitActivite.class);
            intent.putExtra(EndroitActivite.CHEMIN_PHOTO_ENDROIT,
                    zone.getListePhoto().get(0).getImage());
            getContext().startActivity(intent);
        }
        return true;
    }
}
