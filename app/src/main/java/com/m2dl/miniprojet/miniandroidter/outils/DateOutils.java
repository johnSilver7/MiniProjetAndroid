package com.m2dl.miniprojet.miniandroidter.outils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by quentin on 19/01/16.
 */
public class DateOutils {

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

}
