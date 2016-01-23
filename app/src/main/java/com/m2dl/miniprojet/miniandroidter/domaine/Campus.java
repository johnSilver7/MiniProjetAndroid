package com.m2dl.miniprojet.miniandroidter.domaine;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public class Campus {

    private static double x1x3Reel, y1y2Reel, diffX, diffY;
    private final static double TETA = 39.704;
    private static Point pHautGauche, pHautDroite, pBasGauche, pBasDroite;

    public final static double RAPPORT_LARGEUR_LONGUEUR_IMAGE_CAMPUS = 1.12465374;
    public final static double LARGEUR_IMAGE_CAMPUS = 1000.0;
    public final static double LONGUEUR_IMAGE_CAMPUS =
            LARGEUR_IMAGE_CAMPUS / RAPPORT_LARGEUR_LONGUEUR_IMAGE_CAMPUS;

    public static void init() {
        Point p1 = convertirPointInformatique(new Point(43.568698, 1.465387));
        Point p2 = convertirPointInformatique(new Point(43.560804, 1.473386));
        Point p3 = convertirPointInformatique(new Point(43.564647, 1.457949));
        Point p4 = convertirPointInformatique(new Point(43.556975, 1.466417));

        // ajustement (correction des mini erreur)
        double xP1P3Corrige = (p1.getX() + p3.getX()) / 2.0;
        double yP1P2Corrige = (p1.getY() + p2.getY()) / 2.0;
        double xP2P4Corrige = (p2.getX() + p4.getX()) / 2.0;
        double yP3P4Corrige = (p3.getY() + p4.getY()) / 2.0;

        // affectation des quatres coordonnees de notre image
        pHautGauche = new Point(xP1P3Corrige - ((xP2P4Corrige - xP1P3Corrige) * (205.0 / 381.0)),
                yP1P2Corrige - ((yP3P4Corrige - yP1P2Corrige) * (291.0 / 262.0)));
        pHautDroite = new Point(xP2P4Corrige + ((xP2P4Corrige - xP1P3Corrige) * (224.0 / 381.0)),
                pHautGauche.getY());
        pBasGauche = new Point(pHautGauche.getX(),
                yP3P4Corrige + ((yP3P4Corrige - yP1P2Corrige) * (168.0 / 262.0)));
        pBasDroite = new Point(pHautDroite.getX(), pBasGauche.getY());

        // test
        Log.d("HAUT GAUCHE", pHautGauche + "");
        Log.d("HAUT DROITE", pHautDroite + "");
        Log.d("BAS GAUCHE", pBasGauche + "");
        Log.d("BAS DROITE", pBasDroite + "");

        // affectation des 'constantes'
        x1x3Reel = pHautGauche.getX();
        y1y2Reel = pHautGauche.getY();
        diffX = pHautDroite.getX() - pHautGauche.getX();
        diffY = pBasGauche.getY() - pHautGauche.getY();

        // ajuster les points pour l'image
        pHautGauche.setX(0);
        pHautGauche.setY(0);
        pHautDroite.setX(LARGEUR_IMAGE_CAMPUS);
        pHautDroite.setY(0);
        pBasGauche.setX(0);
        pBasGauche.setY(LONGUEUR_IMAGE_CAMPUS);
        pBasDroite.setX(LARGEUR_IMAGE_CAMPUS);
        pBasDroite.setY(LONGUEUR_IMAGE_CAMPUS);

       /* Log.d("HAUT GAUCHE", pHautGauche + "");
        Log.d("HAUT DROITE", pHautDroite + "");
        Log.d("BAS GAUCHE", pBasGauche + "");
        Log.d("BAS DROITE", pBasDroite + "");

        // TEST
        Point p2TP1 = new Point(43.564549, 1.467902);
        Point p2TP1Informatique = getPointSurImage(p2TP1);
        Log.d("2TP1", p2TP1Informatique + "");

        Point p3R3 = new Point(43.561324, 1.469542);
        Point p3R3I = getPointSurImage(p3R3);
        Log.d("p3R3I", p3R3I + "");*/
    }

    public static Point getPointSurImage(Point pointReel) {
        Point pI = convertirPointInformatique(pointReel);
        pI.setX(LARGEUR_IMAGE_CAMPUS * (pI.getX() - x1x3Reel) / diffX);
        pI.setY(LONGUEUR_IMAGE_CAMPUS * (pI.getY() - y1y2Reel) / diffY);
        return pI;
    }

    public static Point convertirPointInformatique(Point pointReel) {
        double cosTETA = Math.cos(Math.toRadians(TETA));
        double sinTETA = Math.sin(Math.toRadians(TETA));

        double xR = pointReel.getX();
        double yR = pointReel.getY();

        double xI = (xR * cosTETA) - (yR * sinTETA);
        double yI = (xR * sinTETA) + (yR * cosTETA);

        // mode 'mirroir'
        return new Point(-xI, -yI);
    }

}
