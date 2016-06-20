package com.yboweb.bestmovie;

/**
 * Created by test on 13/02/16.
 */
public class DetailNames {
    final String IMDB_ID = "imdb_id";
    final String YEAR = "Year";
    final String RELEASE_DATE = "Released";
    final String GENRES = "Genre";
    final String ACTORS = "Actors";
    final String DIRECTOR = "Director";
    final String WRITER = "Writer";
    final String METASCORE = "Metascore";
    final String AWARDS = "Awards";
    final String RATINGS = "imdbRating";
    final String VOTES = "imdbVotes";
    final String TITLE = "Title";
    final String RUNTIME = "Runtime";
    final String VOTES_COUNT = "imdbVotes";
    final String PLOT = "Plot";
    final String RATED = "Rated";

    // the following values are not in JSON
    final String VIDEO_ARRAY="video_array";
    final String ACTORS_ARRAY="actors_array";
    final String ACTORS_COUNT="actors_count";
    final String ACTOR_NAME="name";
    final String ACROR_IMAGE="actor_image";
    final String ACTOR_ID="actor_id";
    final String ACTOR_JSON="actor_json";
    final String ACTOR_DETAILS = "actor_details";
    static DetailNames myDetailsObject = null;

    DetailNames() {
    }

    String getIdStr() {return(IMDB_ID);}
    String getYearStr() {return(YEAR);}
    String getReleaseDateStr() {return(RELEASE_DATE);}
    String getGenresStr() {return(GENRES);}
    String getActorsStr() {return(ACTORS);}
    String getDirectorStr() { return(DIRECTOR);}
    String getWriterStr() { return(WRITER);}
    String getMetascoreStr() { return(METASCORE); }
    String getAwardsStr() {return(AWARDS); }
    String getRatingsStr() {return(RATINGS);}
    String getVotesStr() {return(VOTES); }
    String getTitleStr() {return(TITLE);}
    String getRuntimeStr() {return(RUNTIME);}
    String getVotesCount() { return(VOTES_COUNT);}
    String getPlotStr() {return(PLOT); }
    String getRatedStr() {return(RATED); }
    String getActorsArray() { return(ACTORS_ARRAY);}
    String getVideoArrayStr() {return(VIDEO_ARRAY); } /* not in JSON file, includes another JSON element */
    String getActorCountStr() {return(ACTORS_COUNT); } /* not in JSON file, includes another JSON element */
    String getActorId() {return(ACTOR_ID); } /* not in JSON file, includes another JSON element */
    String getActorName() {return(ACTOR_NAME); } /* not in JSON file, includes another JSON element */
    String getActorImage() {return(ACROR_IMAGE); } /* not in JSON file, includes another JSON element */
    String getActorJson() {return(ACTOR_JSON); } /* not in JSON file, includes another JSON element */
    String getActorDetails() {return(ACTOR_DETAILS); } /* not in JSON file, includes another JSON element */


    public static synchronized DetailNames getInstance()    {
        if (myDetailsObject == null) {
            myDetailsObject = new DetailNames();
        }

        return myDetailsObject;
    }
}

