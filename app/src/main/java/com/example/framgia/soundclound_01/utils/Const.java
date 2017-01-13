package com.example.framgia.soundclound_01.utils;

import com.example.framgia.soundclound_01.BuildConfig;
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

    public class IntentKey {
        public static final String EXTRA_CATEGORY = "category";
        public static final String EXTRA_QUERY = "query";
        public static final String EXTRA_TITLE = "title";
        public static final String BROADCAST_UPDATE_CONTROL = "com.soundclound_01.UpdateControl";
        public static final String EXTRA_USER_NAME = "username";
        public static final String EXTRA_IMAGE_URL = "imageurl";
        public static final String EXTRA_ICON_PLAY_PAUSE = "EXTRA_ICON_PLAY_PAUSE";
        public static final String EXTRA_DURATION = "duration";
        public static final String EXTRA_FULL_DURATION = "fullduration";
        public static final String ACTION_UPDATE_AUDIO =
            "com.soundclound_01.action.ACTION_UPDATE_AUDIO";
        public static final String ACTION_PLAY = "com.soundcloud_01.action.ACTION_PLAY";
        public static final String ACTION_PAUSE = "com.soundcloud_01.action.ACTION_PAUSE";
        public static final String ACTION_PREVIOUS = "com.soundcloud_01.action.ACTION_PREVIOUS";
        public static final String ACTION_NEXT = "com.soundcloud_01.action.ACTION_NEXT";
        public static final String ACTION_GET_AUDIO_STATE = "com.soundcloud_01.action" +
            ".ACTION_AUDIO_SATTE";
        public static final String ACTION_PLAY_NEW_AUDIO = "com.soundcloud_01.action" +
            ".ACTION_PLAY_NEW_AUDIO";
        public static final String ACTION_UPDATE_SEEK_BAR = "com.soundcloud_01" +
            ".ACTION_UPDATE_SEEK_BAR";
        public static final String ACTION_SEEK_TO = "com.soundcloud_01.ACTION_SEEK_TO";
    }

    public class APIConst {
        public static final String BASE_URL_SOUNDCLOUD = "https://api-v2.soundcloud.com/";
        public static final String PATH_AUDIO = "charts";
        public static final String PATH_SEARCH_AUDIO = "search/tracks";
        public static final String PARAM_CLIENT_ID = "client_id";
        public static final String PARAM_KIND_AUDIO = "kind";
        public static final String PARAM_GENRE_AUDIO = "genre";
        public static final String PARAM_OFFSET = "offset";
        public static final String PARAM_QUERY = "q";
        public static final String PARAM_LIMIT = "limit";
        public static final String VALUE_GENRE_POPULAR = "soundcloud:genres:all-music";
        public static final String VALUE_KIND_TOP = "top";
        public static final String VALUE_LIMIT = "20";
        public static final String STREAM_URL = "/stream?" +
            Const.APIConst
                .PARAM_CLIENT_ID + "=" + BuildConfig.API_KEY;
    }

    public class RequestCode {
        public static final int PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;
    }
}
