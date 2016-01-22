package com.m2dl.miniprojet.miniandroidter.domaine;

import android.util.Log;

import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public class Campus {

    private final static double TETA = 36.0;
    private static Point pHautGauche, pHautDroite, pBasGauche, pBasDroite;

    public static void init() {
        Point p1 = convertirPointInformatique(new Point(43.568698, 1.465387));
        Point p2 = convertirPointInformatique(new Point(43.560804, 1.473386));
        Point p3 = convertirPointInformatique(new Point(43.564647, 1.457949));
        Point p4 = convertirPointInformatique(new Point(43.556975, 1.466417));

        Log.d("p1", "" + p1);
        Log.d("p2", "" + p2);
        Log.d("p3", "" + p3);
        Log.d("p4", "" + p4);

        /*pHautGauche = new Point();
        pHautDroite = new Point();
        pBasGauche = new Point();
        pBasDroite = new Point();*/
    }

    public static Point getPointSurImage(Point pointReel) {
        return null;
    }

    /**
     * xReel / u1 = cos(0)
     * u1 = xReel / cos (0)
     *
     * y1 / xReel = tan(0)
     * y1 = tan(0) * xReel
     * y2 = yReel - y1
     * u2 / y2 = sin(0)
     * u2 = sin(0) * y2
     *
     * yInformatique = u1 + u2
     *
     * xReel / p2 = cos (0)
     * p2 = xReel / cos(0)
     *
     * p1 = yInformatique - p2
     * p1 / xInformatique = tan(0)
     * xInformatique = p1 / tan(0)
     *
     *
     * -----
     *
     *
     */
    public static Point convertirPointInformatique(Point pointReel) {
        double cosTETA = Math.cos(TETA);
        double sinTETA = Math.sin(TETA);
        double tanTETA = Math.tan(TETA);

        double xReel = pointReel.getX();
        double yReel = pointReel.getY();

        double yI = (xReel / cosTETA) + (sinTETA * (yReel - (tanTETA * xReel)));
        double xI = (yI - (xReel / cosTETA)) / tanTETA;
        return new Point(cosTETA * xReel, sinTETA * yReel);
    }

}
