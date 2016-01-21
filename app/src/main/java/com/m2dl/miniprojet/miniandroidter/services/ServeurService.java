package com.m2dl.miniprojet.miniandroidter.services;

import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by yan on 15/01/16.
 */
public class ServeurService {

    // REseau

    private String requeteReseau(String LIEN) {
        String contenu = "";
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
            HttpGet httpGet = new HttpGet(LIEN);
            HttpResponse response = httpClient.execute(httpGet);

            // Get the response
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String currentline = "";
            while ((currentline = rd.readLine()) != null) {
                contenu += currentline + "\n";
            }

            return contenu;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean stockerFichier(String lienFichier) {


        String reponse = requeteReseau("https://api.instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=code");

        return true;
    }

    public static File recuperer(String chemin) {
        return null;
    }

}
