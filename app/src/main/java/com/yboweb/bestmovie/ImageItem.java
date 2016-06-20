package com.yboweb.bestmovie;

import android.widget.ImageView;

/**
 * Created by test on 24/03/16.
 */



    public class ImageItem {
        private String image = null;
        private String title = null;
        private String rating = null;
        private String voting = null;
        private String id = null;
        private String videoKey = null;
        private String mapString = null;
        private String mapImages = null;
        private String imageList = null;
        private String imageGalleryNames = null;
        private String actorsJaon = null;
        ImageView keepImageView = null;
        private int type = -1;

        final static public int MAIN_VERTICAL_ITEM = 1;
        final static public int SCROLL_HORIZONTAL_ITEM = 2;
        final static public int GALLERY_ITEM = 3;
        final static public int VIDEO_ITEM = 4;
        final static public int ACTOR_ITEM = 5;
        final static public int ACTOR_DETAIL_IMAGE_ITEM = 6;
        final static public int ACTOR_DETAIL_MOVIE_ITEM = 7;


        public ImageItem() {
            super();
        }

        public String getImage() {
            if(image == null) {

            }
            return image;
        }


        public void  setType(int type) { this.type = type; }
        public int getType() { return (type); }

        public void setImage(String image) {
            this.image = image;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String  gettTitle() {
            return(title);
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String gettRating() {
            return(rating);
        }

        public void setVoting(String voting) {
            this.voting = voting;
        }
        public String getVoting() {
            return(voting);
        }


        public void setMap(String mapString) {
            this.mapString = mapString;
        }
        public String getMap() {
            return(mapString);
        }

        public void setImages(String mapImages) {
        this.mapImages = mapImages;
    }
        public String getImages() {
        return(mapImages);
    }

    public void setImageList(String imageList) { this.imageList = imageList; }
    public String getImageList() { return this.imageList;}
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return(id);
    }
    public void setVideoKey(String key) { videoKey = key;}
    String getVideoKey() { return(videoKey); }

    void setimageGalleryNames(String name) {
        imageGalleryNames = name;
    }

    String getimageGalleryNames() {
        return(imageGalleryNames);
    }

    public void setActorsJson(String name) {this.actorsJaon = name;}
    public String getActorsJaon() {return actorsJaon; }
    public void setImageView(ImageView imageview) { keepImageView = imageview; }
    public ImageView getImageVIew() { return(keepImageView); }
}


