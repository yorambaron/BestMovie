package com.yboweb.bestmovie;

import org.json.JSONArray;

/**
 * Created by test on 18/02/16.
 */
public class MovieDetails {
    JSONArray myJsonArray;
    RowImagesInputData rowImageInputData;

    MovieDetails(JSONArray jsonArrray, RowImagesInputData m) {

        myJsonArray = jsonArrray;
        rowImageInputData = m;
    }

    JSONArray getJsonArray() {
        return(myJsonArray);
    }

    RowImagesInputData getRowImageInputData() {
        return(rowImageInputData);
    }
}
