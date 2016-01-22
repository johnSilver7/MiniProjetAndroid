package com.m2dl.miniprojet.miniandroidter.domaine;

import java.util.List;

/**
 * Created by yan on 15/01/16.
 */
public class Campus {

    private static Point pHautGauche, pHautDroite, pBasGauche, pBasDroite;

    public static void init() {
        Point p1 = new Point(43.568698, 1.465387);
        Point p2 = new Point(43.560804, 1.473386);
        Point p3 = new Point(43.564800, 1.458784);
        Point p4 = new Point(43.556975, 1.466417);

        // TODO les calculer reellement
        /*pHautGauche = new Point();
        pHautDroite = new Point();
        pBasGauche = new Point();
        pBasDroite = new Point();*/
    }

    public static Point getPointSurImage(Point pointReel) {
        //TODO
        return new Point(43.56, 1.46);
    }

}
