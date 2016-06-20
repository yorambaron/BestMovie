package com.yboweb.bestmovie;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.javacodegeeks.androidnavigationdrawerexample.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by test on 07/02/16.
 */
public class GetDetails extends AsyncTask<ImagesObject  , Void, RowImagesOutputData> {

    DetailNames detailNames = DetailNames.getInstance();

    final String IMDB_ID = detailNames.getIdStr();
    final String YEAR = detailNames.getYearStr();
    final String RELEASE_DATE = detailNames.getReleaseDateStr();
    final String GENRES = detailNames.getGenresStr();
    final String ACTORS = detailNames.getActorsStr();
    final String DIRECTOR = detailNames.getDirectorStr();
    final String WRITER = detailNames.getWriterStr();
    final String METASCORE = detailNames.getMetascoreStr();
    final String AWARDS = detailNames.getAwardsStr();
    final String RATINGS = detailNames.getRatingsStr();
    final String VOTES = detailNames.getVotesCount();
    final String TITLE = detailNames.getTitleStr();
    final String RUNTIME = detailNames.getRuntimeStr();
    final String VOTES_COUNT = detailNames.getVotesStr();
    final String PLOT = detailNames.getPlotStr();
    final String RATED = detailNames.getRatedStr();
    final String VIDEO_ARRAY = detailNames.getVideoArrayStr();
    final String ACTORS_ARRAY = detailNames.getActorsArray();
    private ArrayList<String> savedImages = null;

    private static Map<String, String> myMap = new HashMap<String, String>();

    public static Map<String, String>  getMap() {
        return(myMap);
    }

    @Override
    protected RowImagesOutputData doInBackground(ImagesObject... params) {
        try {

            ReadURL readURL = new ReadURL();

            savedImages = params[0].getImages();

            ImdbConstants imdbConstObject = ImdbConstants.getInstance();

            Log.d("SEQUENCE", "GetDetails index:" + params[0].getIndex());
            /* String url = "https://api.themoviedb.org/3/movie/" + params[0].getId().toString() + "?api_key=cce4441214d822885a3de775079b5008"; */
            String url = imdbConstObject.getMovieUrl(params[0].getId());
            StringBuffer bufferRead = readURL.Read(url);

            JSONObject node = new JSONObject(bufferRead.toString());
            String imdbId = node.getString(IMDB_ID);
            /* String queryURL = "http://www.omdbapi.com/?i=" + imdbId + "&plot=short&r=json"; */
            String queryURL = imdbConstObject.getDetailsUrl(imdbId);

            bufferRead = readURL.Read(queryURL);
            JSONObject nodeDetails = new JSONObject(bufferRead.toString());

            String year = getValueIfExists(nodeDetails, YEAR, "2016");
            String release_date = getValueIfExists(nodeDetails, RELEASE_DATE, "2016-01-101");
            String genres = getValueIfExists(nodeDetails, GENRES, "N/A");
            String actors = getValueIfExists(nodeDetails, ACTORS, "N/A");
            String director = getValueIfExists(nodeDetails, DIRECTOR, "N/A");
            String writer = getValueIfExists(nodeDetails, WRITER, "N/A");
            String metascore = getValueIfExists(nodeDetails, METASCORE, "0");
            String awards = getValueIfExists(nodeDetails, AWARDS, "N/A");
            String ratings = getValueIfExists(nodeDetails, RATINGS, "0");
            String votes = getValueIfExists(nodeDetails, VOTES, "0");
            String title = getValueIfExists(nodeDetails, TITLE, "N/A");
            String runtime = getValueIfExists(nodeDetails, RUNTIME, "0");
            String votecount = getValueIfExists(nodeDetails, VOTES_COUNT, "0");
            String plot = getValueIfExists(nodeDetails, PLOT, "N/A");
            String rated = getValueIfExists(nodeDetails, RATED, "0");
           // String videoAray = nodeDetails.getString(RATED);

            Log.d("DEBUG", "year:" + year);
            //Log.d("DEBUG", "release_date:" + release_date);
            Log.d("DEBUG", "genres:" + genres);
            Log.d("DEBUG", "actors:" + actors);
            Log.d("DEBUG", "director" + director);
            Log.d("DEBUG", "writer" + writer);
            Log.d("DEBUG", "metascore" + metascore);
            Log.d("DEBUG", "adwards" + awards);
            Log.d("DEBUG", "ratings" + ratings);
            Log.d("DEBUG", "votes" + votes);

            String[] actorsArray = actors.split(", ");

            Integer count = 0;
            List<HashMap<String, String>> actorMap = new ArrayList<HashMap<String, String>>();


            myMap.put(detailNames.getActorCountStr(), String.valueOf(actorsArray.length));
            for (String item : actorsArray) {
                Log.d("actors:", item);

                try {
                    final String encodedURL = URLEncoder.encode(item, "UTF-8");
                    String actorUrl = imdbConstObject.getActor(encodedURL);


                    bufferRead = readURL.Read(actorUrl);

            } catch (java.io.UnsupportedEncodingException e) {
                Log.e("JSON: ", e.getMessage(), e);
                e.printStackTrace();
            }




                try {
                    JSONObject actorObject = new JSONObject(bufferRead.toString());

                    JSONArray resultsArray = actorObject.getJSONArray("results");




                    JSONObject resultObject = resultsArray.getJSONObject(0);
                    String id = resultObject.getString("id");

                    ActorDetails actorDetails = new ActorDetails();
                    // Actors data is defined in two different URLs
                    String actorDetailsString = actorDetails.readActorDetails(id);
                    myMap.put(detailNames.getActorDetails() + "-" + count, actorDetailsString);


                    String Image = resultObject.getString("profile_path");
                    ImdbConstants imdbConstants =  ImdbConstants.getInstance();
                    String imageURL = imdbConstants.getOneImage(Image, "w342");
                            Log.d("actors", "Name:" + item + " id:" + id);

                    myMap.put(detailNames.getActorId() + "-" + count, id);
                    myMap.put(detailNames.getActorImage() + "-" + count, imageURL);
                    myMap.put(detailNames.getActorName() + "-" + count, item);


                    Gson gson = new Gson();
                    String resultString = gson.toJson(resultObject);
                    myMap.put(detailNames.getActorJson() + "-" + count, resultString);

                    count++;

                }   catch (JSONException e) {
                        Log.e("JSON: ", e.getMessage(), e);
                        e.printStackTrace();
                    }
                myMap.put(ACTORS_ARRAY, actorMap.toString());
            }


            myMap.put(YEAR, year);
            myMap.put(RELEASE_DATE, release_date);
            myMap.put(GENRES, genres);
            myMap.put(ACTORS, actors);
            myMap.put(DIRECTOR, director);
            myMap.put(WRITER, writer);
            myMap.put(METASCORE, metascore);
            myMap.put(AWARDS, awards);
            myMap.put(RATINGS, ratings);
            myMap.put(VOTES, votes);
            myMap.put(TITLE, title);
            myMap.put(RUNTIME, runtime);
            myMap.put(VOTES_COUNT, votecount);
            myMap.put(PLOT, plot);
            myMap.put(RATED, rated);

            Activity activity = params[0].getActivity();


            Log.d("Map", "Id=" + params[0].getId());
            /* get the video list */
            url = imdbConstObject.getMovieLists(params[0].getId());
            bufferRead = readURL.Read(url);

            JSONObject nodeVideo = new JSONObject(bufferRead.toString());
            JSONArray resultsArray = nodeVideo.getJSONArray("results");

            List<HashMap<String, String>> resultMap = new ArrayList<HashMap<String, String>>();

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject nodeJsonVideo = resultsArray.getJSONObject(i);

                String key = nodeJsonVideo.getString("key");
                String name = nodeJsonVideo.getString("name");
                HashMap<String, String> hmap = new HashMap<String, String>();
                hmap.put("key", key);
                hmap.put("name", name);
                resultMap.add(i, hmap);

            }

            /* Video is a JSON object */
            myMap.put(VIDEO_ARRAY, resultsArray.toString());
            for (int i = 0; i < resultMap.size(); i++) {
                HashMap<String, String> hmap1 = new HashMap<String, String>();
                hmap1 = resultMap.get(i);

                Log.d("MAP:", "i=" + i + " Key=" + hmap1.get("key"));
                Log.d("MAP:", "i=" + i + " name=" + hmap1.get("name"));
            }

            return (new RowImagesOutputData(activity, null, myMap));


        } catch (JSONException e) {
            Log.e("JSON: ", e.getMessage(), e);
            e.printStackTrace();
        }
        /* return (myMap); */
        ;
        return (null);

    }


    @Override
    protected void onPostExecute(RowImagesOutputData param) {
        final Activity activity = param.getActivity();
        Map<String, String> myMapData = param.getMap();
        LinearLayout layout = (LinearLayout) activity.findViewById(R.id.scroll_root_id);
        drawDetails(layout, activity, myMapData, savedImages.size(), null);






    }



    String getValueIfExists(JSONObject object, String property, String defaultValue) {
        try {
            if (object.has(property))
                return (object.getString(property));
            return (defaultValue);

        } catch (JSONException e) {
            Log.e("JSON: ", e.getMessage(), e);
            e.printStackTrace();
        }
        return(null);
    }

    public void drawDetails(LinearLayout layout, Activity myActivity, Map<String, String> myCurrentMap, final int numImages, final JSONArray jsonArray) {


        ViewPager mViewPager = (ViewPager) myActivity.findViewById(R.id.video_pager_id);
        CustomPagerAdapter mGridAdapter = new CustomPagerAdapter(myActivity);
        mGridAdapter.set_propop(0.7f);

        //header
        drawHeaderFields(myCurrentMap, myActivity);
        //Prepare gallery
        prepareGalery(numImages, jsonArray, mGridAdapter);
        //Prepare video list
        prepareVideoList(myCurrentMap, mGridAdapter);
        // Prepare gallery and video in one PageView
        mViewPager.setAdapter(mGridAdapter);
        mGridAdapter.notifyDataSetChanged();

        //Actors part
        drawActors(myCurrentMap, myActivity, layout);

        //Change sandbox indicator
        ProgressBar progressBarDetails = (ProgressBar) myActivity.findViewById(R.id.get_detetail_progress_bar);
        progressBarDetails.setVisibility(View.INVISIBLE);
    }

    private void drawActors(Map<String, String> myCurrentMap, Activity myActivity, LinearLayout layout) {
        final Activity activity = myActivity;
        Integer actorsCount = Integer.parseInt(myCurrentMap.get(detailNames.getActorCountStr()));


        CustomPagerAdapter mGridAdapter = new CustomPagerAdapter(activity);
        ViewPager mViewPager = (ViewPager) activity.findViewById(R.id.movie_actors_id);


        for (int i = 0; i < actorsCount; i++) {
            ImageItem item = new ImageItem();
            String actorImageUrl = myCurrentMap.get(detailNames.getActorImage() + "-" + i);
            item.setImage(actorImageUrl);

            item.setVoting("voting");
            item.setRating("rating");
            String actorName = myCurrentMap.get(detailNames.getActorName() + "-" + i);
            item.setTitle(actorName);
            item.setType(ImageItem.ACTOR_ITEM);

            try {
                String actorProperty = detailNames.getActorJson() + "-" + i;
                JSONObject combinedActor = new JSONObject();

                // Sometimes, there is no value here
                if(myCurrentMap.get(actorProperty) != null) {


                    final JSONObject actorJson = new JSONObject(myCurrentMap.get(actorProperty));
                    final JSONObject actorDetailsKson = new JSONObject(myCurrentMap.get(detailNames.getActorDetails() + "-" + i));

                    combinedActor.put("details", actorDetailsKson);
                    combinedActor.put("known_for", actorJson);


                    final String combinedActorsString = combinedActor.toString();
                    item.setActorsJson(combinedActorsString);

                }

            } catch (JSONException e) {
                Log.e("JSON: ", e.getMessage(), e);
                e.printStackTrace();
            }



            mGridAdapter.addItem(item);

        }


        mGridAdapter.set_propop(0.33f);
        mViewPager.setAdapter(mGridAdapter);
        mGridAdapter.notifyDataSetChanged();


    }

    private void drawHeaderFields(Map<String, String> myCurrentMap, Activity myActivity) {
        final Activity activity = myActivity;
        String title = myCurrentMap.get(TITLE);
        String year = " (" + myCurrentMap.get(YEAR) + ")";
        String textAndYear = " " + title + year;

        TextView titleTextView = (TextView) activity.findViewById(R.id.my_title);
        titleTextView.setText(textAndYear);



        TextView ratedText = (TextView) activity.findViewById(R.id.rating);
        ratedText.setText(myCurrentMap.get(RATED));

        TextView genreText = (TextView) activity.findViewById(R.id.genres);
        genreText.setText(myCurrentMap.get(GENRES));

        TextView runtimeText = (TextView) activity.findViewById(R.id.runtime);
        runtimeText.setText(myCurrentMap.get(RUNTIME));


        TextView overview = (TextView) activity.findViewById(R.id.overview);

        overview.setText(myCurrentMap.get(PLOT));

    }

    private void prepareVideoList(Map<String, String> myCurrentMap, CustomPagerAdapter mGridAdapter) {
        try {
            JSONArray jsonVideoData;
            final String resultsArrayStr = myCurrentMap.get(VIDEO_ARRAY);
            jsonVideoData = new JSONArray(resultsArrayStr);
            for (int i = 0; i < jsonVideoData.length(); i++) {
                final JSONObject nodeJsonVideo = jsonVideoData.getJSONObject(i);
                final String key = nodeJsonVideo.getString("key");

                String fullUrl = "http://img.youtube.com/vi/" + key + "/0.jpg";

                Log.d("Scrolling", "Video image is:" + fullUrl);

                ImageItem item = new ImageItem();
                item.setImage(fullUrl);
                item.setTitle("title");
                item.setVoting("voting");
                item.setRating("rating");
                item.setVideoKey(key);
                item.setType(ImageItem.VIDEO_ITEM);
                mGridAdapter.addItem(item);


            }
        } catch (JSONException e) {
            Log.e("JSON: ", e.getMessage(), e);
            e.printStackTrace();
        }


    }

    private void prepareGalery(int numImages, JSONArray jsonArray, CustomPagerAdapter mGridAdapter) {
        if(numImages > 0) {
            Integer index = 1;
            if (numImages == 1)
                index = 0;

            String name = null;
            ImdbConstants imdbConstants = ImdbConstants.getInstance();
            String fullImageUrl = null;

            if (jsonArray != null) {
                // Favorite (data comes from memory)
                try {
                    String tmp = jsonArray.get(index).toString();
                    JSONObject j = new JSONObject(tmp);
                    // We already have the full URLs.
                    fullImageUrl = j.getString("image");
                    Log.d("GetDetails:", "Favorite: Image string:" + jsonArray.toString());
                } catch (JSONException e) {
                    Log.e("JSON: ", e.getMessage(), e);
                    e.printStackTrace();
                }
            } else {
                name = savedImages.get(index);
                fullImageUrl = imdbConstants.getOneImage(name, "w342");
                Log.d("GetDetails:", "Image string:" + savedImages.toString());
            }

            String imagesString = null;

            if (jsonArray != null) {
                // The image that we have is full name and not with the right size.
                // We need to create a coma separated names that GridviewActivity can handle
                for (int i = 0; i < numImages; i++) {
                    try {
                        String tmp = jsonArray.get(i).toString();
                        JSONObject j = new JSONObject(tmp);
                        String fullUrl = j.getString("image");
                        int loc = fullUrl.lastIndexOf('/');
                        String privateName = fullUrl.substring(loc);
                        if (imagesString == null)
                            imagesString = privateName;
                        else
                            imagesString = imagesString + privateName;
                        if (i != numImages - 1)
                            imagesString = imagesString + ",";


                    } catch (JSONException e) {
                        Log.e("JSON: ", e.getMessage(), e);
                        e.printStackTrace();
                    }


                }
                imagesString = "[" + imagesString + "]";
                Log.d("Scrolling", "Full favorite:" + imagesString);
            } else {
                imagesString = savedImages.toString();
                Log.d("Scrolling", "Full name" + imagesString);
            }

            ImageItem itemImage = new ImageItem();
            itemImage.setImage(fullImageUrl);
            itemImage.setTitle("title");
            itemImage.setVoting("voting");
            itemImage.setRating("rating");
            itemImage.setimageGalleryNames(imagesString);
            itemImage.setType(ImageItem.GALLERY_ITEM);
            mGridAdapter.addItem(itemImage);
        }


    }

}
