package com.yboweb.bestmovie;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by test on 07/02/16.
 */
public class ReadURL {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    final String LOG_TAG = "ReadURL";


    StringBuffer Read(String inputURL) {

        String ReadURL;
        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast

           Log.d("DEBUG", "Input URL: " + inputURL);
            /* String encodedurl = URLEncoder.encode(inputURL, "UTF-8"); */
            URL url = new URL(inputURL);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            Log.d("Buffer ", buffer.toString());

            return (buffer);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        }  finally {
            if (urlConnection != null) {
                urlConnection.disconnect();

            }
            if (reader != null) {
                try {
                    reader.close();
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
            else {
                Log.d("DEBUG", "Read returned null" + " Url: " + inputURL);
                return null;
            }
        }

    }
}
