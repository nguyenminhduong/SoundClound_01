package com.example.framgia.soundclound_01.utils;

import com.example.framgia.soundclound_01.R;

public class Const {
    public static class Resource {
        public static int[] IMAGE_CATEGORY = {
            R.drawable.ic_altinaterock,
            R.drawable.ic_ambient,
            R.drawable.ic_classical,
            R.drawable.ic_country,
            R.drawable.ic_dance_edm,
            R.drawable.ic_deep_house,
            R.drawable.ic_disco,
            R.drawable.ic_drum_bass,
            R.drawable.ic_dubstep,
            R.drawable.ic_electronic,
            R.drawable.ic_folk_singer_song_writer,
            R.drawable.ic_hiphop_rap,
            R.drawable.ic_house,
            R.drawable.ic_indie,
            R.drawable.ic_jazz_blues,
            R.drawable.ic_latin,
            R.drawable.ic_metal,
            R.drawable.ic_piano,
            R.drawable.ic_pop,
            R.drawable.ic_ab_soul,
            R.drawable.ic_reggae,
            R.drawable.ic_rock,
            R.drawable.ic_sound_track,
            R.drawable.ic_trance,
            R.drawable.ic_world,
            R.drawable.ic_audio_book,
            R.drawable.ic_business,
            R.drawable.ic_comedy,
            R.drawable.ic_entertainment,
            R.drawable.ic_learning,
            R.drawable.ic_new,
            R.drawable.ic_spirituality_religion,
            R.drawable.ic_science,
            R.drawable.ic_sports,
            R.drawable.ic_story_telling,
            R.drawable.ic_technology};
    }

    public class APIConst {
        public class BaseURL {
            public static final String BASE_URL_SOUNDCLOUD = "https://api-v2.soundcloud.com/";
        }

        public class Param {
            public static final String PARAM_CLIENT_ID = "client_id";
            public static final String PARAM_KIND_AUDIO = "kind";
            public static final String PARAM_GENRE_AUDIO = "genre";
            public static final String PARAM_OFFSET = "offset";
            public static final String PARAM_QUERY = "q";
            public static final String PARAM_LIMIT = "limit";
            public static final String VALUE_GENRE_POPULAR = "soundcloud:genres:all-music";
            public static final String VALUE_KIND_TOP = "top";
            public static final String VALUE_LIMIT = "20";
        }

        public class Path {
            public static final String PATH_AUDIO =
                "charts";
            public static final String PATH_SEARCH_AUDIO = "search";
        }

        public class APIKey {
            public static final String API_KEY_SOUNDCLOUD = "08f79801a998c381762ec5b15e4914d5";
        }
    }
}
