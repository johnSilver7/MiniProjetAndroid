package com.m2dl.miniprojet.miniandroidter.outils;

/**
 * Created by quentin on 21/01/16.
 */
public class MathOutils {

    public static int getDistance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
