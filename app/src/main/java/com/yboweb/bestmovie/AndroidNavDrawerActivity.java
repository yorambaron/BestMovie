package com.yboweb.bestmovie;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.yboweb.bestmovie.androidnavigationdrawerexample.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AndroidNavDrawerActivity extends ActionBarActivity
            implements NavigationDrawerFragment.NavigationDrawerCallbacks {


        private NavigationDrawerFragment mNavigationDrawerFragment;
        private CharSequence mTitle;
        static LinearLayout topLinearLayout;
        static HorizontalScrollView scrollView;
        static FragmentManager movieFragmentManager;
        Activity mActivity;

        private static final String TAG = GridViewActivity.class.getSimpleName();

        static private ListView mGridView;
        static private ProgressBar mProgressBar;

        static  VerticalAdapter mGridAdapter;
        static private ArrayList<ImageItem> mGridData;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Initialize the SDK before executing any other operations,
            FacebookSdk.sdkInitialize(getApplicationContext());
//            AppEventsLogger.activateApp(this);

            // Change the arrow icon to the standard menu
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_hamburger);
            final Activity activity = this;
            mActivity = this;

            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            mTitle = getTitle();

            movieFragmentManager = getSupportFragmentManager();
            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));


            topLinearLayout = new LinearLayout(this);
            topLinearLayout.setOrientation(LinearLayout.VERTICAL);



            mGridView = (ListView) findViewById(R.id.vertical_scroll_id);

            mProgressBar = (ProgressBar) findViewById(R.id.drawer_progress_bar);
            mProgressBar.setVisibility(View.VISIBLE);


            //Initialize with empty data
            mGridData = new ArrayList<>();
            mGridAdapter = new VerticalAdapter(this, R.layout.row_main_layout, mGridData);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    //Get item at position
                    ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                    String Myid = item.getId();


                    Intent intent = new Intent(activity, ScrollingActivity.class);
                    Bundle args = new Bundle();
                    args.putString("id", item.getId());
                    args.putString("image_url", item.getImage());
                    args.putString("rating", item.gettRating());
                    args.putString("voting", item.getVoting());
                    args.putString("title", item.gettTitle());
                    intent.putExtras(args);
                    startActivity(intent);
                }
            });

        }



        /**
         * Parsing the feed results and get the list
         *
         * @param result
         */


        static void updateUI(List<Map<String, String>> myResult, Activity activity) {
            final DetailNames detailNames = DetailNames.getInstance();



            final String YEAR = detailNames.getYearStr();

            mGridData = new ArrayList<>();
            mGridAdapter = new VerticalAdapter(activity, R.layout.row_main_layout, mGridData);


            for (int i = 0; i < myResult.size(); i++) {
                Map<String, String> myMap1 = new HashMap<String, String>();
                myMap1 = myResult.get(i);
                String url = myMap1.get("backdrop_path");
                if (url != null) {
                    String id;
                    String SINGLE_IMAGE_url = "http://image.tmdb.org/t/p/";
                    String fullName = SINGLE_IMAGE_url + "w780" + url;
                    ImageItem item = new ImageItem();
                    item.setImage(fullName);
                    item.setType(ImageItem.MAIN_VERTICAL_ITEM);
                    String title = myMap1.get("title");
                    if (title == null)
                        title = "N/A";
                    String year = myMap1.get("release_date");
                    String[] myDate = year.split("-");

                    String fullTitle;
                    if (year != null) {
                        fullTitle = title + "(" + myDate[0] + ")";
                    } else {
                        fullTitle = title;
                    }

                    id = myMap1.get("id");
                    item.setTitle(fullTitle);

                    String voting = myMap1.get("popularity");
                    double votingDouble = Double.parseDouble(voting);

                    NumberFormat formatter = new DecimalFormat("#0.00");
                    String  votingStrNicer = formatter.format(votingDouble);

                    String rating = myMap1.get("vote_average");
                    item.setVoting("Voting: " + votingStrNicer);
                    item.setRating("Rating: " +  rating);
                    item.setId(id);
                    mGridData.add(item);
                }

            }
            mGridAdapter = new VerticalAdapter(activity, R.layout.row_main_layout, mGridData);
            mGridView.setAdapter(mGridAdapter);
            mGridAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.INVISIBLE);

        }


    static void updateUIFavorite(List<Map<String, String>> myResult, Activity activity) {
        mGridData = new ArrayList<>();
        final Activity mActivity = activity;
        mGridAdapter = new VerticalAdapter(mActivity, R.layout.row_main_layout, mGridData);

        for (int i = 0; i < myResult.size(); i++) {

            String image = null;
            String title = null;
            String voting = null;
            String rating = null;
            String id = null;
                Map<String, String>  myMap1 = myResult.get(i);
            image = myMap1.get("image1");

            if (image != null) {

                title = myMap1.get("title");
                if(title == null)
                    title = "N/A";
                voting = myMap1.get("voting");
                if(voting == null)
                    voting = "N/A";
                rating = myMap1.get("rating");
                if(rating == null)
                    rating = "N/A";
                ImageItem item = new ImageItem();
                id = myMap1.get("id");

                item.setImage(image);
                item.setTitle(title);
                item.setRating(rating);
                item.setVoting(voting);
                item.setId(id);
                item.setImages(myMap1.get("images"));
                item.setMap(myMap1.get("map"));
                mGridData.add(item);
            }

        }
        mGridAdapter = new VerticalAdapter(mActivity, R.layout.row_main_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Get item at position
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                String Myid = item.getId();


                Intent intent = new Intent(mActivity, ScrollingActivity.class);
                Bundle args = new Bundle();
                args.putString("id", Myid);
                args.putString("image_url", item.getImage());
                args.putString("rating", item.gettRating());
                args.putString("voting", item.getVoting());
                args.putString("title", item.gettTitle());
                args.putString("map", item.getMap());
                args.putString("images", item.getImages());
                intent.putExtras(args);
                mActivity.startActivity(intent);
            }
        });
        mGridView.setAdapter(mGridAdapter);
        mGridAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.INVISIBLE);
             /* mProgressBar.setVisibility(View.INVISIBLE); */
    }

    public class MyOnClickListener implements LinearLayout.OnClickListener
    {

        int myId;


        @Override
        public void onClick(View v)
        {

            Map<String, String> m = new HashMap<String, String>();
            m.put("id",String.valueOf(myId));
            Activity myActivity = getActivity();



            Intent intent = new Intent(getActivity(), ScrollingActivity.class);

            Bundle args = new Bundle();
            args.putString("id", String.valueOf(myId));

            /*
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(m);
            args.putString("json", json);
            */
            intent.putExtras(args);
            myActivity.startActivity(intent);

        }

    }







        static LinearLayout getLayout() {
            return(topLinearLayout);
    }

    static FragmentManager  getMovieFragmentManager() {
        return(movieFragmentManager);
    }

    public Activity getActivity() {
        return(mActivity);
    }


          String getOneImage(String fileName, String size) {
               String SINGLE_IMAGE_url = "http://image.tmdb.org/t/p/";
            return(SINGLE_IMAGE_url + size + fileName);
        }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        Activity activity = this;
        ImdbConstants imdbConstants = ImdbConstants.getInstance();


        switch (number) {
            case 1:
                mTitle = "Most Popular";
                String popularUrl = imdbConstants.getPopular();

                GetResults popular = new GetResults();

                Log.d("searchQuery:", popularUrl);

                popular.execute(new RowImagesInputData(popularUrl, activity, 0,  null, null,imdbConstants.GET_DETAILS ));


                break;
            case 2:
                mTitle = "Top Rated";

                String topRatedUrl = imdbConstants.getTopRated();
                GetResults topRated = new GetResults();

                Log.d("searchQuery:", topRatedUrl );

                topRated.execute(new RowImagesInputData(topRatedUrl, activity, 0, null, null, imdbConstants.GET_DETAILS));

                break;
            case 3:
                mTitle = "Coming soon";
                String comingSoonUrl = imdbConstants.getComingSoon();
                GetResults comingSoon = new GetResults();
                Log.d("searchQuery:", comingSoonUrl);
                comingSoon.execute(new RowImagesInputData(comingSoonUrl, activity, 0, null, null, imdbConstants.GET_DETAILS));
                break;

            case 4:
                mTitle = "Favorite";

                DBHandler handler = new DBHandler(getActivity().getApplicationContext());
                List<Map<String, String>> myResult = new ArrayList<Map<String, String>>();;

                List<MovieRow> movieList =  handler.readAll();

                if(movieList == null) {


                    Toast.makeText(activity, "No data",
                            Toast.LENGTH_LONG).show();
                    return;

                }

                for(int i = 0; i < movieList.size(); i++ ) {

                    Map<String, String> myMap1 = new HashMap<String, String>();
                    MovieRow movieRow = movieList.get(i);

                    myMap1.put("title", movieRow.title);
                    myMap1.put("voting", movieRow.voting);
                    myMap1.put("rating", movieRow.rating);
                    myMap1.put("image1", movieRow.imageUrl);
                    myMap1.put("id", movieRow.id);
                    myMap1.put("map", movieRow.map);
                    myMap1.put("images", movieRow.images);
                    myResult.add(myMap1);
                }

                updateUIFavorite(myResult, getActivity());
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.

        /*
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_scrolling, menu);
        */


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.search);
        final SearchView searchView;
            MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setActionView(menuItem, searchView = new SearchView(getActivity()));

         searchView.setSearchableInfo(
                 searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryHint(getResources().getString(R.string.hint_search));




        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final TextView textView = (TextView) searchView.findViewById(id);
        textView.setHintTextColor(Color.WHITE);
        textView.setTextColor(Color.WHITE);
        textView.setText("");









        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                Log.d("Search-here", "SearchView.OnQueryTextListener queryTextListener");
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //close keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                Log.d("Search", "text is:" + query );
                try {
                    final String encodedURL = URLEncoder.encode(query, "UTF-8");
                    ImdbConstants imdbConstants = ImdbConstants.getInstance();
                    String searchUrl = imdbConstants.getSearchMovie(encodedURL);

                    GetResults searchMovieResults = new GetResults();

                    Log.d("searchQuery:", searchUrl );


                    textView.setHintTextColor(Color.WHITE);
                    textView.setTextColor(Color.WHITE);
                    textView.setText("");


                    searchMovieResults.execute(new RowImagesInputData(searchUrl, getActivity(), 0,  null, null, imdbConstants.GET_DETAILS));

                } catch (UnsupportedEncodingException e) {
                    Log.e("JSON: ", e.getMessage(), e);
                    e.printStackTrace();
                }
                return(false);

            }
        };
        searchView.setOnQueryTextListener(queryTextListener);




        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((AndroidNavDrawerActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
