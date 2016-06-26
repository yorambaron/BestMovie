package com.yboweb.bestmovie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.yboweb.bestmovie.androidnavigationdrawerexample.R;

/**
 * Created by test on 05/03/16.
 */

public class PlayVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

        public static final String DEVELOPER_KEY = "AIzaSyB1lVXkohfHUbwJdsdh3UsVyIzKk37QKtg";
        private static final int RECOVERY_DIALOG_REQUEST = 1;
        String url="C_Tsj_wTJkQ";
        String VIDEO_ID;

        YouTubePlayerFragment myYouTubePlayerFragment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_you_tube);
            myYouTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager()
                    .findFragmentById(R.id.youtubeplayerfragment);
            myYouTubePlayerFragment.initialize(DEVELOPER_KEY, this);
            //VIDEO_ID=getYoutubeVideoId(url);

            Bundle args = getIntent().getExtras();
            VIDEO_ID = args.getString("YOUTUBE_PARAM");

        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                            YouTubeInitializationResult errorReason) {
            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
            } else {
                String errorMessage = String.format(
                        "There was an error initializing the YouTubePlayer (%1$s)",
                        errorReason.toString());
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                            boolean wasRestored) {
            if (!wasRestored) {
                player.cueVideo(VIDEO_ID);
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode == RECOVERY_DIALOG_REQUEST) {
                // Retry initialization if user performed a recovery action
                getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
            }
        }

        protected YouTubePlayer.Provider getYouTubePlayerProvider() {
            return (YouTubePlayerView)findViewById(R.id.youtubeplayerfragment);
        }

    }

