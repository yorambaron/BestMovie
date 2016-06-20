package com.yboweb.bestmovie;

/**
 * Created by test on 16/04/16.
 */




    public class MovieItem {
        private String image;
        private String title;
        private String id;

        public MovieItem() {
            super();
            image = null;
        }

        public String getImage(int position) {
            if(image == null) {

            }
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String  gettTitle() {
            return(title);
        }




        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return(id);
        }

    }




