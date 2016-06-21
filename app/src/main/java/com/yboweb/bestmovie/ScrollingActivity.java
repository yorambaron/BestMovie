package com.yboweb.bestmovie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.javacodegeeks.androidnavigationdrawerexample.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ScrollingActivity extends AppCompatActivity {

    static LinearLayout topLinearLayout;
    private CustomPagerAdapter mGridAdapter;
    //private ArrayList<ImageItem> mGridData;
    static private ProgressBar mProgressBar;
    android.support.v4.view.ViewPager mViewPager;
    private String imageUrl;
    private String rating;
    private String voting;
    private String title;
    private String mId;
    private String mapString;
    private String imagesList;
    private Activity activity = this;
    private static String  omdbId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);


        Intent intentExtras = getIntent();
        Bundle extraBundle = intentExtras.getExtras();


        Bundle args = getIntent().getExtras();
        mId  = args.getString("id");

        imageUrl = args.getString("image_url");
        rating = args.getString("rating");
        voting = args.getString("voting");
        title = args.getString("title");
        mapString = args.getString("map");
        imagesList = args.getString("images");

        Log.d("Scrolling", "image_url:" + args.getString("image_url") + " rating:" + args.getString("rating") + " voting:" + args.getString("voting") + " title:" + args.getString("title"));
        Log.d("scrolling", "Map:" + args.getString("map"));
        Log.d("scrolling", "Images:" + args.getString("images"));

        final Activity activity = this;


            int idInt = Integer.parseInt(mId);

            ImdbConstants imdbConstants = ImdbConstants.getInstance();
            String url = imdbConstants.getImagesUrl(idInt);



            /* mGridView = (ListView) findViewById(R.id.scroll_detail_id1); */
            mViewPager = (ViewPager) findViewById(R.id.scroll_detail_id);
            mProgressBar = (ProgressBar) findViewById(R.id.detail_progress_bar);
            mProgressBar.setVisibility(View.VISIBLE);

            //Initialize with empty data
            mGridAdapter = new CustomPagerAdapter(activity);


            topLinearLayout = new LinearLayout(this);


            Log.d("DET_IMAGES", "URL:" + url);
            RowImagesInputData m = new RowImagesInputData(url, activity, idInt, this, null, imdbConstants.GET_DETAILS);




            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            topLinearLayout.setLayoutParams(params);
            topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);






        if(mapString != null && imagesList != null) {
            try {

                // We have the data in memory (favorites).
                // This part draws the horizontal picture images
                JSONArray jsonArray = new JSONArray(imagesList);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String fullImageURL = object.getString("image");

                    ImageItem item = new ImageItem();
                    item.setImage(fullImageURL);
                    item.setTitle("title");
                    item.setVoting("voting");
                    item.setRating("rating");
                    item.setType(ImageItem.SCROLL_HORIZONTAL_ITEM);
                    mGridAdapter.addItem(item);


                }

                mViewPager.setClipToPadding(false);
                mViewPager.setPadding(0, 0, 0, 0);
                mViewPager.setAdapter(mGridAdapter);

                mProgressBar.setVisibility(View.INVISIBLE);

                GetDetails getDetails = new GetDetails();
                HashMap<String,String> map = new Gson().fromJson(mapString, new TypeToken<HashMap<String, String>>() {
                }.getType());
                Log.d("Scrolling", "Number of items:" + jsonArray.length());
                LinearLayout layout = (LinearLayout) activity.findViewById(R.id.scroll_root_id);

                // This part draws the rest of the page.
                getDetails.drawDetails(layout, this, map, jsonArray.length(), jsonArray);

            } catch (JSONException e) {
                    Log.e("JSON: ", e.getMessage(), e);
                    e.printStackTrace();
                }



        } else {
            //The getImages calls to GetDetails
                GetImages getImages = new GetImages();
                getImages.execute(m);

            }

    }

    private  String getOneImage(String fileName, String size) {
        final   String SINGLE_IMAGE_url = "http://image.tmdb.org/t/p/";
        return(SINGLE_IMAGE_url + size + fileName);
    }

    void  addItem(ImageItem imageItem) {
        mGridAdapter.addItem(imageItem);
    }



    public static void setId(String id) {
         omdbId =  id;
    }

    void setGridData() {
        mViewPager.setAdapter(mGridAdapter);
        mGridAdapter.set_propop(0.9f);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_scrolling, menu);

        /*
        MenuItem item = (MenuItem) menu.findItem(R.id.save);
        item.setEnabled(false);
        */
        return(super.onCreateOptionsMenu(menu));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if(id == R.id.share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            prepareShareIntent(sharingIntent);

            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }
        if (id == R.id.save) {

            ArrayList<ImageItem> mGridData  = mGridAdapter.getData();
            Log.d("Action", "Got save");
            int size = mGridData.size();

            // Map to String
            Map<String, String> myMap = GetDetails.getMap();
            JSONObject obj = new JSONObject(myMap);
            String mapString = obj.toString();

            // String to map
            HashMap<String,String> map = new Gson().fromJson(mapString, new TypeToken<HashMap<String, String>>() {
            }.getType());
            Log.d("Action", "Succeeded?");

    
            String imagesString = new Gson().toJson(mGridData);
            try {
                JSONArray jsonArray = new JSONArray(imagesString);


                Log.d("Action", "passed reading grid data");


            }   catch (JSONException e) {

                e.printStackTrace();
            }



            Log.d("Action", "Image Length: " + String.valueOf(size));
            Log.d("Action", "Details Length: " + myMap.size());

            Log.d("Action:", "Image:" + imageUrl);
            Log.d("Action:", "Title:" + title);
            Log.d("Action:", "Rating:" + rating);
            Log.d("Action:", "Voting:" + voting);
            Log.d("Action:", "id:" + id);

            DBHandler handler = new DBHandler(activity.getApplicationContext());
            MovieRow mRow = new MovieRow(mId, title, rating, voting, imageUrl, mapString, imagesString);
            if(handler.searchMoviebyId(mId) != null)
                handler.updateMovie(mRow);
            else
                handler.addMovie(mRow);
            MovieRow mRow1 = handler.searchMoviebyId(mId);


            Log.d("Action", "still alive");

        }
            return super.onOptionsItemSelected(item);
        }


    void setProgressBarInactive() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }



    static LinearLayout getLayout() {
        return (topLinearLayout);
    }

    public void  prepareShareIntent(Intent sharingIntent) {
        // Fetch Bitmap Uri locally

        ImageView imageX = mGridAdapter.getData().get(0).getImageVIew();
        Bitmap bmp = ((BitmapDrawable) imageX.getDrawable()).getBitmap();

        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Construct share intent as described above based on bitmap

        sharingIntent.setAction(Intent.ACTION_SEND);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Come see: " + title + " at: http://www.imdb.com/title/" + omdbId );
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Great movie: " + title);


        sharingIntent.setType("image/*");

    }


}



