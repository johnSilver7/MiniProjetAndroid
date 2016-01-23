package com.m2dl.miniprojet.miniandroidter.outils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by quentin on 19/01/16.
 */
public class DateOutils {

    /**
     * Renvoie la date en chaine de caractere en format: 'JJ/MM/AAAA hh:mm'.
     * @param date date de type timestamp en base
     * @return la date en chaine de caractere
     */
    public static String toStringDate(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        int jour = calendar.get(Calendar.DAY_OF_MONTH);
        int mois = calendar.get(Calendar.MONTH) + 1;
        int annee = calendar.get(Calendar.YEAR );

        int heure = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return
                (jour <= 9 ? "0" : "") + jour + "/" +
                (mois <= 9 ? "0" : "") + mois + "/" + annee + " " +
                (heure <= 9 ? "0" : "") + heure + ":" +
                (minute <= 9 ? "0" : "") + minute;
    }

    public static Date getDate(String dateToString) {
        int jour = Integer.parseInt(dateToString.substring(0, 2));
        int mois = Integer.parseInt(dateToString.substring(3, 5));
        int annee = Integer.parseInt(dateToString.substring(6, 10));

        int heure = Integer.parseInt(dateToString.substring(11, 13));
        int min = Integer.parseInt(dateToString.substring(14, 16));
        return new Date(annee, mois, jour, heure, min, 0);
    }

}
