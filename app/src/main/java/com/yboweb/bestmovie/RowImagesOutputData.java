package com.yboweb.bestmovie;

import android.app.Activity;
import android.widget.LinearLayout;

import java.util.Map;

/**
 * Created by test on 02/02/16.
 */

public class RowImagesOutputData {

    final static String ID = "id";
    final static String TITLE = "title";

    Map<String, String> myMap;

     Activity myActivity;
     LinearLayout myLayout;


    RowImagesOutputData(Activity activity, LinearLayout layout, Map<String, String> map) {
        myMap = map;
        myActivity = activity;
        myLayout = layout;

    }


    String getTitle() { return(myMap.get(TITLE));}
    Map<String, String> getMap() { return(myMap); }
    Activity getActivity() {return myActivity; }
    LinearLayout getLayout() {return(myLayout);}

}
