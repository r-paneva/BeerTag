package com.beertag.android.utils;

public class Constants {


    public static final String BASE_SERVER_URL
            //="http://10.178.211.134:8080/api";
            //= "http://192.168.43.119:8080/api";
            = "http://192.168.0.102:8080/api";
            //= "http://192.168.42.242:8080/api";
            //= "http://192.168.43.121:8080/api";


    public static final String BEER_EXTRA_KEY = "Beer_EXTRA_KEY";
    public static final long LIST_IDENTIFIER = 1;
    public static final long CREATE_IDENTIFIER = 2;
    public static final long DETAILS_IDENTIFIER = 3;
    public static final long LOGOUT_IDENTIFIER = 4;
    public static final long IMAGE_IDENTIFIER = 5;
    public static final long GET_PICTURE_IDENTIFIER = 6;
    public static final long HOME_IDENTIFIER = 7;
    public static final long MY_BEERS_IDENTIFIER = 8;

    public static final long BEER_NAME_MIN_VALUE = 5;
    public static final long BEER_NAME_MAX_VALUE = 18;
    public static final String NAME_ERROR_MESSAGE = "Beer Name must be between 5 and 18 chars";
    public static final String UNSUCCESSFUL_CREATION = "Unsuccessful create new beer picture!";
    public static final String COUNTRY_ERROR_MESSAGE = "Country can't be empty";
    public static final String STYLE_ERROR_MESSAGE = "Style can't be empty";
    public static final String PREFERENCES_BEER_ID_KEY = "beerId";
    public static final String PREFERENCES_BEER_NAME_KEY = "beerName";
    public static final String BEER_IMAGE_KEY = "beerImage" ;


    static final int IMAGE_QUALITY = 50;
    public static final String ERROR_LOADING_IMAGE = "Error Loading Image";
    public static final String IMAGE_MESSAGE_EXTRA = "Image message extra";
    public static final String USER_PROFILE_IMAGE_KEY = "userImage";
    public static final String IMAGE_FILE_TYPE = "image/*";
    public static final String IMAGE_CHANGE_ERROR_MESSAGE = "Beer image update failed!";


    public static final String ERROR_MESSAGE = "Error: ";


    public static final String USER_EXTRA_KEY = "USER_EXTRA_KEY";
    public static final String PREFERENCES_USER_ID_KEY = "userId";
    public static final String PREFERENCES_USER_NAME_KEY = "userName";
    public static final String USER_IMAGE_KEY = "userImage";
    public static final String PREFERENCES_USER_FULL_NAME_KEY = "fullName";
    public static final String ERROR_LOADING_USER_IMAGE = "You have no picture";

    public static final int VISIBLE_CODE_VALUE = 0;
    public static final String UNSUCCESSFUL_LOGIN = "Unsuccessful login";
    public static final String USER_CACHE_IMAGE_KEY = "userCacheImage" ;

}


