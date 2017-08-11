package com.example.android.eatout;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by sauhardpant on 2017-08-11.
 */



public class NetworkUtils extends MainActivity{
    final static String ZOMATO_URL = "https://developers.zomato.com/api/v2.1/search?apikey=a14399473dda131cb917e581b48c21bb&";
    final static String QUERY_PARAM = "q";

    //function builds the URL
    public static URL buildUrl (String searchQueryFromEditText) {
        Uri builtUri = Uri.parse(ZOMATO_URL).buildUpon().appendQueryParameter(QUERY_PARAM, searchQueryFromEditText).build();

        URL url = null;
        //storing the URL in a new var and returning it
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    //still need to connect to the internet
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
