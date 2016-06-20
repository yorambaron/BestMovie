package com.yboweb.bestmovie;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by test on 11/02/16.
 */
public class ImagesObject {
    private Integer myIndex;
    private Integer myId;
    private Activity  myActivity;
    private ArrayList<String> savedImages = new ArrayList<String>();

    ImagesObject(Integer index, Integer id, Activity activity, ArrayList<String> savedImages) {
        myIndex = index;
        myId = id;
        myActivity  = activity;
        this.savedImages = savedImages;
    }

    Integer getIndex() { return(myIndex); }
    Integer getId() { return(myId); }
    Activity getActivity() { return myActivity; }
    ArrayList<String> getImages() { return(savedImages);}
}
