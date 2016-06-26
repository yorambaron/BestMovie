package com.yboweb.bestmovie;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yboweb.bestmovie.androidnavigationdrawerexample.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 14/02/16.
 */
public class GetImages extends AsyncTask<RowImagesInputData, Void, MovieDetails> {

    private final String LOG_TAG = GetImages.class.getSimpleName();
    ImdbConstants  imdbConstObject = ImdbConstants.getInstance();


    static ArrayList<String> savedImages = new ArrayList<String>();

    @Override
    protected MovieDetails
    doInBackground(RowImagesInputData... params) {

        String url = params[0].getURL();

        List<Map<String, String>> myResult = new ArrayList<Map<String, String>>();

        int mContent = params[0].getType();
        ReadURL readURL = new ReadURL();
        StringBuffer readBuffer = readURL.Read(url);


        try {
            JSONObject jsonBuffer = new JSONObject(readBuffer.toString());
            String mContentJson;

            if(mContent == imdbConstObject.GET_DETAILS) {
                mContentJson = "backdrops";
            }  else {
                mContentJson = "profiles";
            }

            JSONArray resultsArray = jsonBuffer.getJSONArray(mContentJson);


            Log.d("MostPopular", "Returned." + resultsArray.length());

            return (new MovieDetails(resultsArray, params[0]));

        } catch (JSONException e) {
            Log.e("JSON: ", e.getMessage(), e);
            e.printStackTrace();
        }
        return (null);
    }

    private  String getOneImage(String fileName, String size) {
        final   String SINGLE_IMAGE_url = "http://image.tmdb.org/t/p/";
        return(SINGLE_IMAGE_url + size + fileName);
    }

    public static Integer  convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }


    public void displayView(MovieDetails param) {
        int mContent = param.getRowImageInputData().getType();
        Activity  activity = param.getRowImageInputData().getMyActivity();
        int maxEntries = param.getJsonArray().length();
        ScrollingActivity scrollingActivity  = param.getRowImageInputData().getScrollingActivity();

        if(maxEntries == 0) {
            scrollingActivity.setProgressBarInactive();
            Activity myActivity = param.getRowImageInputData().getMyActivity();
            Toast.makeText(myActivity, "No data",
                    Toast.LENGTH_LONG).show();
            return;
        }

        ActorScrollingActivity actorScrollingActivity = param.getRowImageInputData().getActorsScrolling();




        /* save images for gallery */
        try {
            for(Integer i = 0; i < maxEntries; i++) {
                Map<String, String> myMap1 = new HashMap<String, String>();

                JSONObject node  = param.getJsonArray().getJSONObject(i);
                String imagePath = node.getString("file_path");
                String imageSize = "w342";
                if(mContent == imdbConstObject.GET_PERSON)
                    imageSize= "w185";
                String fullName = getOneImage(imagePath, imageSize);
                Log.d("Image ", i + " " + fullName);

                if(imagePath != null) {

                    myMap1.put("file_path", imagePath);
                    savedImages.add(i, imagePath);


                    if(mContent == imdbConstObject.GET_DETAILS) {
                        ImageItem item = new ImageItem();
                        item.setImage(fullName);
                        item.setRating("rating");
                        item.setVoting("voting");
                        item.setTitle("title");
                        item.setType(ImageItem.SCROLL_HORIZONTAL_ITEM);

                        scrollingActivity.addItem(item);
                    } else {
                        ImageItem item = new ImageItem();
                        item.setImage(fullName);
                        item.setType(ImageItem.ACTOR_DETAIL_IMAGE_ITEM);
                        actorScrollingActivity.add(item);

                    }
                }
                if(mContent == imdbConstObject.GET_DETAILS) {

                    scrollingActivity.setGridData();
                } else {
                    actorScrollingActivity.setGridData();

                    ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.actor_progress_bar);
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }



        } catch (JSONException e) {
            Log.e("JSON: ", e.getMessage(), e);
            e.printStackTrace();
        }

        if(mContent == imdbConstObject.GET_DETAILS) {
            ProgressBar progressBarDetails = (ProgressBar) activity.findViewById(R.id.get_detetail_progress_bar);
            progressBarDetails.setVisibility(View.VISIBLE);

            GetDetails getDetails = new GetDetails();
            Integer id = param.getRowImageInputData().getId();

            getDetails.execute(new ImagesObject(0, id, activity, savedImages));

            if (maxEntries == 0) {
                ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.detail_progress_bar);
                progressBar.setVisibility(View.INVISIBLE);


            /* No images, adjust the height to 0
                LinearLayout imageLayout = (LinearLayout) activity.findViewById(R.id.scroll_root_id);
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) imageLayout.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                imageLayout.setLayoutParams(params);
                */


            } else {


            }
        }
    }

    protected void onPostExecute(MovieDetails param) {

        displayView(param);


    }

}
