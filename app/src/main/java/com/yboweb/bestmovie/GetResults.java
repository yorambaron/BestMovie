package com.yboweb.bestmovie;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yboweb.bestmovie.androidnavigationdrawerexample.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* import android.support.v4.app.FragmentManager; */

/**
 * Created by test on 21/01/16.
 */

public class GetResults extends AsyncTask<RowImagesInputData, Void, Activity> {

    private final String LOG_TAG = GetResults.class.getSimpleName();
    // IDs information
    final String PAGE = "page";
    final String RESULTS = "results";
    final String IMG_POSTER_PATH = "poster_path";
    final String OVERVIEW = "overview";
    final String ID = "id";
    final String TITLE = "title";
    final String IMDB_RATING = "vote_average";
    final String POPLARITY = "popularity";
    final String RELEASE_DATE="release_date";
    final String VOTE_AVERAGE="vote_average";
    String baseUrl;
    Activity myActivity;
    final LinearLayout myLayout = AndroidNavDrawerActivity.getLayout();
    ProgressBar mProgressBar;
    /* final FragmentManager FragmentManagerMovie = AndroidNavDrawerActivity.getMovieFragmentManager(); */


    List<Map<String, String>> myResult;
    List<Map<String, String>> myResultDetails = new ArrayList<Map<String, String>>();
    Map<String, String> myMap = new HashMap<String, String>();


        /* Most Popular: https://api.themoviedb.org/3/movie/popular?api_key=cce4441214d822885a3de775079b5008
        * Get details: https://api.themoviedb.org/3/movie/131631?api_key=cce4441214d822885a3de775079b5008
        Image: http://image.tmdb.org/t/p/w500/cWERd8rgbw7bCMZlwP207HUXxym.jpg
        Type of movies https://api.themoviedb.org/3/genre/movie/list?api_key=cce4441214d822885a3de775079b5008
        Release in 2015: https://api.themoviedb.org/3/discover/movie?api_key=cce4441214d822885a3de775079b5008&include_video=true&primary_release_year=2015

        Video http://www.imdb.com/video/imdb/vi1150334233/imdb/embed?autoplay=true&format=1080p&token=BCYisHv72GjkoFriwMRzAQpxKXCCURzoXOakaCJxnWR-2L5U9CV9dHzgy2T6HbTd-5058CiXhrCP%0D%0A__qzxGJSmVxgH8CHvfwjXLOS6en9m6tj9oU%0D%0A&ref_=vi_res_1080p
        Airing today: https://api.themoviedb.org/3/tv/airing_today?api_key=cce4441214d822885a3de775079b5008
        Get List of Videos: https://api.themoviedb.org/3/movie/131631/videos?api_key=cce4441214d822885a3de775079b5008
        Display a video in Youtube https://www.youtube.com/watch?v=C_Tsj_wTJkQ
        */

    @Override
    protected void onPostExecute(Activity params)  {
        try {


                       if(myResult.size() == 0) {
                            ProgressBar progressBar = (ProgressBar) params.findViewById(R.id.drawer_progress_bar);
                            progressBar.setVisibility(View.INVISIBLE);

                           

                           Toast toast = new Toast(params.getApplicationContext());
                           Toast.makeText(params.getBaseContext(), "No results for this search query",
                                   Toast.LENGTH_LONG).show();
                           toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                           toast.setDuration(Toast.LENGTH_LONG);
                           toast.setView(myLayout);
                           toast.show();
                return;
            }


            for (int i = 0; i < myResult.size(); i++) {
                Map<String, String> myMap1;
                myMap1 = myResult.get(i);


                int  id = Integer.parseInt(myMap1.get(ID));
                Log.d("JSON: ", "i: " + i + " ID " + id);




            }
            AndroidNavDrawerActivity.updateUI(myResult, params);
        } catch (NullPointerException ex) {
            Log.i("onPostExecute", "********** Holy crap! **********");
        }

        // TODO: Do more with result here, invoke all the methods
    }

    @Override
    protected Activity  doInBackground(RowImagesInputData... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        baseUrl = params[0].getURL();
        myActivity = params[0].getMyActivity();

        myResult = new ArrayList<Map<String, String>>();
        // Will contain the raw JSON response as a string.


        List<Map<String, String>> myMap = new ArrayList<Map<String, String>>();

        ReadURL readURL = new ReadURL();
        StringBuffer readBuffer = readURL.Read(baseUrl);
        searchQuery(readBuffer);

        return(params[0].getMyActivity());
    }



            public void searchQuery(StringBuffer buffer) {
            try {
                if(buffer == null) {
                    Log.d("searchQuery", "No data read");
                    return;
                }
                JSONObject SearchJson = new JSONObject(buffer.toString());
                JSONArray resultsArray = SearchJson.getJSONArray(RESULTS);


Log.d("searchQuery", "Returned." + resultsArray.length());


                for (int i = 0, count = 0; i < resultsArray.length(); i++) {
                    JSONObject node = resultsArray.getJSONObject(i);

                    String imageStr = node.getString("backdrop_path");

                    if(!imageStr.equals("null")) {

                        Map<String, String> myMap1 = new HashMap<String, String>();

                        String overview = node.getString(OVERVIEW);
                        String title = node.getString(TITLE);


                        String id = node.getString(ID);
                        ;

                        myMap1.put(OVERVIEW, overview);
                        myMap1.put(TITLE, title);
                        // myMap1.put(IMG_POSTER_PATH, imageStr);
                        myMap1.put("backdrop_path", imageStr);
                        myMap1.put(ID, id);
                        myMap1.put(POPLARITY, node.getString(POPLARITY));
                        myMap1.put(RELEASE_DATE, node.getString(RELEASE_DATE));
                        myMap1.put(VOTE_AVERAGE, node.getString(VOTE_AVERAGE));


                        // getOneDetails(new PairObject(i, Integer.parseInt(id)));
                        myResult.add(count, myMap1);
                        count++;
                    }

                }

            } catch (JSONException e) {
                Log.e("JSON: ", e.getMessage(), e);
                e.printStackTrace();
            }
        }



}

