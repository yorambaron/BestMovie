package com.yboweb.bestmovie;

import android.app.Activity;

/**
 * Created by test on 25/01/16.
 */
public class RowImagesInputData {
    private String MyURL;
    private Activity Myactivity;
    private int myId;
    private ScrollingActivity mScrolling;
    private ActorScrollingActivity mActorScrolling;
    private Integer mType;

    RowImagesInputData(String URL, Activity activity, int id, ScrollingActivity scrollingActivity, ActorScrollingActivity actorScrollingActivity, int type) {
        MyURL = URL;
        Myactivity = activity;
        myId = id;

        mScrolling = scrollingActivity;
        mActorScrolling = actorScrollingActivity;
        mType = type;
    }

    String getURL() {
        return (MyURL);
    }

    Activity getMyActivity() {
        return Myactivity;
    }


    int getId() {
        return (myId);
    }

    ScrollingActivity getScrollingActivity() {return(mScrolling);}
    ActorScrollingActivity getActorsScrolling() { return(mActorScrolling); }
    Integer getType() { return(mType); }


}
