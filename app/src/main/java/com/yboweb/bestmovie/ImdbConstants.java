package com.yboweb.bestmovie;

import android.util.Log;

/**
 * Created by test on 14/02/16.
 */
public class ImdbConstants {
    final String KEY = "cce4441214d822885a3de775079b5008";
    final String API_KEY = "api_key";
    final String IMDB_URL = "https://api.themoviedb.org/3";
    final String MOVIE = "movie";
    final String VIDEOS = "videos";
    final String IMDB_MOVIE = IMDB_URL + "/" + MOVIE;
    final String OMDB_URL = "http://www.omdbapi.com";
    final String ID_PARAM= "i=";
    final String IMAGES_param = "";
    final String OMDB_URL_ID = OMDB_URL + "?" + ID_PARAM;
    final String PLOT = "&plot=short&r=json";
    final String IMAGES = "images";
    final String PERSON = "person";
    final String SINGLE_IMAGE_url = "http://image.tmdb.org/t/p/";
    final Integer GET_DETAILS = 1;
    final Integer GET_PERSON = 2;



    static ImdbConstants ImdbConstantsObject = null;

    ImdbConstants() {

    }

    public String getMovieLists(Integer id) {

        // https://api.themoviedb.org/3/movie/131631/videos?api_key=cce4441214d822885a3de775079b5008
        return(IMDB_URL + "/" + MOVIE + "/" + id + "/" + VIDEOS + "?" + API_KEY + "=" + KEY);

    }

    public String getMovieUrl(Integer ImdbId) {

        /* String url = "https://api.themoviedb.org/3/movie/" + params[0].getId().toString() + "?api_key=cce4441214d822885a3de775079b5008"; */
        Log.d("PATH", "getMovieUrl:" + IMDB_MOVIE + "/" + ImdbId + "?" + API_KEY + "=" + KEY);
        return(IMDB_MOVIE + "/" + ImdbId + "?" + API_KEY + "=" + KEY);

    }

    public String getDetailsUrl(String ImdbId) {
        /* String queryURL = "http://www.omdbapi.com/?i=" + imdbId + "&plot=short&r=json"; */
        Log.d("PATH", "getDetailsUrl:" + OMDB_URL_ID + "/" + ImdbId + PLOT);
        return(OMDB_URL_ID + ImdbId + PLOT);
    }

    public String getImagesUrl (int id) {

        /* String Input = "https://api.themoviedb.org/3/movie/" + Id + "images?api_key=cce4441214d822885a3de775079b5008"; */
        Log.d("PATH", "getImagesUrl:" + IMDB_MOVIE + id +  "/" + IMAGES + "?" + API_KEY + "=" + KEY);
        return(IMDB_MOVIE + "/" +  id +  "/" + IMAGES + "?" + API_KEY + "=" + KEY);
    }

    public String getOneImage(String fileName, String size) {

        return(SINGLE_IMAGE_url + size + fileName);
    }

    public  String getSingleImage(String fileName, String size) {

        return(SINGLE_IMAGE_url + size + fileName);
    }
    public static synchronized  ImdbConstants getInstance()    {
        if (ImdbConstantsObject == null) {
            ImdbConstantsObject = new ImdbConstants();
        }

        return ImdbConstantsObject;
    }

    public String getPopular() {
        return( IMDB_URL + "/movie/popular" + "?" +  API_KEY + "=" + KEY);
    }

    public String getTopRated() {
        return( IMDB_URL + "/movie/top_rated" + "?" +  API_KEY + "=" + KEY);
    }

    public String getComingSoon() {
        return( IMDB_URL + "/movie/upcoming" + "?" +  API_KEY + "=" + KEY);
    }

    public String getActor(String actor) {
        final String IMDB_URL = "https://api.themoviedb.org/3";
        return(IMDB_URL + "/search/person?" + API_KEY + "=" + KEY + "&query=" + actor);
    }

    // https://api.themoviedb.org/3/search/movie?query=Star%20Wars:%20Greatest%20Moments&api_key=cce4441214d822885a3de775079b5008
    public String getSearchMovie(String input) {
        String urlString = IMDB_URL + "/search/movie?query=" + input + "&" + API_KEY + "=" + KEY;
        Log.d("Search:", urlString);
        return(urlString);

    }
    public String getActorDetails(String id) {
        return(IMDB_URL + "/" + "person/" + id + "?" + API_KEY + "=" + KEY);
    }

    public String getActorImages(String id) {
        return(IMDB_URL + "/" + "person/" + id + "/images?" + API_KEY + "=" + KEY);
    }


}
