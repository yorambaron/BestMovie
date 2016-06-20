package com.yboweb.bestmovie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.javacodegeeks.androidnavigationdrawerexample.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ActorScrollingActivity extends AppCompatActivity {

    static LinearLayout topLinearLayout;

    static private    ArrayList<ImageItem> mGridImageData;
    android.support.v4.view.ViewPager mViewImagePager;
    private CustomPagerAdapter mGridImageAdapter;

    // Actors part

    static private    ArrayList<ImageItem> mGridMovieData;
    android.support.v4.view.ViewPager mViewMoviePager;
    private CustomPagerAdapter mGridMovieAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_scrolling);


        Intent intentExtras = getIntent();
        Bundle extraBundle = intentExtras.getExtras();

        Bundle args = getIntent().getExtras();
        String combinedActor = args.getString("combined");

        try {
            JSONObject combinedActorJson = new JSONObject((combinedActor));
            JSONObject detailsObject = combinedActorJson.getJSONObject("details");
            JSONObject knownForObject = combinedActorJson.getJSONObject("known_for");
            String id  = detailsObject.getString("id");

            final Activity activity = this;




            ImdbConstants imdbConstants = ImdbConstants.getInstance();
            String url = imdbConstants.getActorImages(id);


            ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.actor_progress_bar);
            mProgressBar.setVisibility(View.VISIBLE);


                //Images part
            mViewImagePager = (ViewPager) findViewById(R.id.actor_images_id);
            mGridImageAdapter = new CustomPagerAdapter(activity);
            mGridImageData = new ArrayList<>();


            //actots part
            mViewMoviePager = (ViewPager) findViewById(R.id.actor_movie_images_id);
            mGridMovieAdapter = new CustomPagerAdapter(activity);
            mGridMovieData = new ArrayList<>();


            topLinearLayout = new LinearLayout(this);


            Log.d("DET_IMAGES", "URL:" + url);
            RowImagesInputData m = new RowImagesInputData(url, activity, -1,  null, this, imdbConstants.GET_PERSON);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            topLinearLayout.setLayoutParams(params);
            topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);


            GetImages getImages = new GetImages();
            getImages.execute(m);
            updateDetailsActor(detailsObject, knownForObject, activity);
        } catch (JSONException e) {
            Log.e("JSON: ", e.getMessage(), e);
            e.printStackTrace();
        }



    }



        void updateDetailsActor(JSONObject detailsObject, JSONObject knownForObject, Activity activity) {

        try {
            TextView nameView = (TextView) findViewById(R.id.acor_name_id);
            String name = detailsObject.getString("name");
            nameView.setText(name + ", known for");

            TextView descriptionView = (TextView) findViewById(R.id.acor_description_id);
            String description = detailsObject.getString("biography");
            descriptionView.setText(description);

            JSONObject nameValuePairsObject = knownForObject.getJSONObject("nameValuePairs");
            JSONObject knownForInerObject = nameValuePairsObject.getJSONObject("known_for");
            JSONArray knownForArray = knownForInerObject.getJSONArray("values");
            ImdbConstants imdbconstant = ImdbConstants.getInstance();



            for(Integer i = 0; i < knownForArray.length(); i++) {
                JSONObject j = knownForArray.getJSONObject(i);
                JSONObject realObject = j.getJSONObject("nameValuePairs");
                String title = ""; // String might be empty
                if( realObject.has("title"))
                     title = realObject.getString("title");
                String imageName = realObject.getString("poster_path");
                String id = realObject.getString("id");
                String fullImage = imdbconstant.getSingleImage(imageName, "w185");

                ImageItem item = new ImageItem();
                item.setImage(fullImage);
                item.setTitle(title);
                item.setId(id);
                item.setType(ImageItem.ACTOR_DETAIL_MOVIE_ITEM);
                mGridMovieAdapter.addItem(item);
                Log.d("Actors", "i = " + i + " known for name:" + title);
                Log.d("Actors", "i = " + i + " known for image:" + fullImage);
            }

            mGridMovieAdapter.set_propop(0.33f);
            mViewMoviePager.setAdapter(mGridMovieAdapter);


        } catch (JSONException e) {
        Log.e("JSON: ", e.getMessage(), e);
        e.printStackTrace();
    }



    }


    void add(ImageItem item) {
        mGridImageData.add(item);
    }
    void setGridData() {

        mGridImageAdapter.set_propop(0.33f);
        mGridImageAdapter.setGridData(mGridImageData);
        mViewImagePager.setAdapter(mGridImageAdapter);

    }

    static LinearLayout getLayout() {
        return (topLinearLayout);
    }
}



