package com.example.framgia.soundclound_01.utils;

public class Const {
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
            public static final String VALUE_GENRE_POPULAR="soundcloud:genres:all-music";
            public static final String VALUE_KIND_TOP="top";
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
