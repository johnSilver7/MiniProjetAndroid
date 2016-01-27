package com.m2dl.miniprojet.miniandroidter.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;
import com.m2dl.miniprojet.miniandroidter.domaine.Photo;
import com.m2dl.miniprojet.miniandroidter.domaine.Utilisateur;
import com.m2dl.miniprojet.miniandroidter.domaine.Zone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by yan on 15/01/16.
 */
public class ServeurService {

    private final static String URL_SERVEUR = "http://miniprojetandroidjyq.orgfree.com";

    public static void sauvegarder(String classe, JSONObject jsonObject) {
        String lien = URL_SERVEUR + "/script.php?choix=set&classe=" + classe +
                "&json=" + URLEncoder.encode(jsonObject.toString());

        if (classe.equals("Photo")) {
            try {
                uploadPhoto(jsonObject.getString("image"), Photo.PATH + Photo.NOM_PHOTO_TEMP,
                        Photo.getPhoto(jsonObject.getString("image")).getBitmap());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TODO traiter la reponse du serveur
        String reponse = envoyerRequeteServeur(lien);
        Log.d("json ENVOYE", jsonObject.toString());
        Log.d("reponse SERVEUR", reponse);
    }

    public static Bitmap getImageBitmap(String image) {
        try {
            URL urlImage = new URL(URL_SERVEUR + "/photos/" + image);
            HttpURLConnection connection =
                    (HttpURLConnection) urlImage.openConnection();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Charger la base de donnees localement
    public static void chargerBaseDeDonnees() {
        JSONArray utilisateurs = getJsonArrayDe("Utilisateur");
        JSONArray zones = getJsonArrayDe("Zone");
        JSONArray photos = getJsonArrayDe("Photo");

        Utilisateur.setListeAPartirDeJsonArray(utilisateurs);
        Zone.setListeAPartirDeJsonArray(zones);
        Photo.setListeAPartirDeJsonArray(photos);
    }

    private static String envoyerRequeteServeur(String lien) {
        String result;
        InputStream is;

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(lien);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        try{
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            return result;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JSONArray getJsonArrayDe(String classe) {
        String lien = URL_SERVEUR + "/script.php?choix=getListe&classe=" + classe;
        String json = envoyerRequeteServeur(lien);
        // mettre au propre
        int i;
        for (i = 0; i < json.length() &&
                !json.substring(i, i + 1).equals("["); i++) {
            // Rien
        }
        if (i >= json.length()) {
            json = "[]";
        } else {
            json = json.substring(i);
        }

        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean uploadPhoto(String nomImage, String cheminDurImage, Bitmap bitmap) {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(cheminDurImage);

        if (!sourceFile.isFile()) {
            Log.d("FICHIER", "FAUX");
            return false;
        }

        try {
            // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(URL_SERVEUR + "/upload.php?choix=photo");

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", nomImage);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + nomImage + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // retrieve the response from server
            int ch;
            InputStream is = conn.getInputStream();
            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) != -1 ) {
                b.append( (char)ch );
            }
            String s = b.toString();
            Log.i("Response", s);

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();
            is.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
