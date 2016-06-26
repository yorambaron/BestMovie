package com.yboweb.bestmovie;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yboweb.bestmovie.androidnavigationdrawerexample.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class YouTubeFragment extends Fragment {

    public YouTubeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_you_tube, container, false);
    }
}
