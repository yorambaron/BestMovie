package com.yboweb.bestmovie;

/**
 * Created by test on 05/05/16.
 */
public class MovieRow {
    public String id;
    public String title;
    public String rating;
    public String voting;
    public String map;
    public String images;
    public String imageUrl;


    public MovieRow(String id, String title, String rating, String voting, String imageUrl, String map, String images) {
        this.id = id;
        this.rating = rating;
        this.title = title;
        this.voting = voting;
        this.map = map;
        this.images = images;
        this.imageUrl = imageUrl;
    }
}
