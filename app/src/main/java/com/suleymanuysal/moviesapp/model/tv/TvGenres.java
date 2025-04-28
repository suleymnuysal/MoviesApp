package com.suleymanuysal.moviesapp.model.tv;

import android.widget.Switch;

public class TvGenres {

    public String getGenresForTv(int genreId){

        switch (genreId){

            case 10759:
                return "Action & Adventure";
            case 16:
                return "Animation";

            case 35:
                return "Comedy";

            case 80:
                return "Crime";

            case 18:
                return "Drama";


            case 10751:
                return "Family";


            case 10762:
                return "Kids";

            case 9648:
                return "Mystery";

            case 10763:
                return "News";

            case 10764:
                return "Reality";

            case 10765:
                return "Sci-Fi & Fantasy";

            case 10766:
                return "Soap";

            case 10767:
                return "Talk";

            case 10768:
                return "War & Politics";

            case 37:
                return "Western";

            default:
                return "null";

        }


    }

}
