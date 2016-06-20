package com.yboweb.bestmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.javacodegeeks.androidnavigationdrawerexample.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridViewActivity extends ActionBarActivity {
    private static final String TAG = GridViewActivity.class.getSimpleName();

    private GridView mGridView;
    private ProgressBar mProgressBar;

    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;
    /* private String FEED_URL = "http://javatechig.com/?json=get_recent_posts&count=45"; */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        Intent i = getIntent();

        if (i == null)
            Log.d("***DEBUG****", "Intent was null");
        else
            Log.d("**** DEBUG ***", "Intent OK");

        Bundle args = i.getExtras();
        String images = args.getString("images");

        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, mGridData);

        mGridView.setAdapter(mGridAdapter);
        mProgressBar.setVisibility(View.VISIBLE);

        //Grid view click event
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Get item at position
                GridItem item = (GridItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(GridViewActivity.this, DetailsActivity.class);
                ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);

                // Interesting data to pass across are the thumbnail size/location, the
                // resourceId of the source bitmap, the picture description, and the
                // orientation (to avoid returning back to an obsolete configuration if
                // the device rotates again in the meantime)

                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);

                //Pass the image title and url to DetailsActivity
                intent.putExtra("left", screenLocation[0]).
                        putExtra("top", screenLocation[1]).
                        putExtra("width", imageView.getWidth()).
                        putExtra("height", imageView.getHeight()).
                        putExtra("title", item.getTitle()).
                        putExtra("image", item.getImage());

                //Start details activity
                startActivity(intent);
            }
        });

        //Start download

        parseResult(images);

    }



    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */
    private void parseResult(String imagesString) {


        List<String> items = Arrays.asList(imagesString.substring(0, imagesString.length() - 1).substring(1).split("\\s*,\\s*"));
        for(int i = 0; i < items.size(); i++)
            Log.d("GetDetails", "i:" + i + " Value:" + items.get(i));

            GridItem item;
            for (int i = 0; i < items.size(); i++) {
                String url = items.get(i);

                String fullName = getOneImage(url, "w92");
                item = new GridItem();
                item.setImage(fullName);

                item.setTitle("title");

                mGridData.add(item);
            }

        mGridAdapter.setGridData(mGridData);


        //Hide progressbar
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public String getOneImage(String fileName, String size) {
        final String SINGLE_IMAGE_url = "http://image.tmdb.org/t/p/";
        return(SINGLE_IMAGE_url + size + fileName);
    }
}