package com.beertag.android;

public class Constants {


    public static final String BASE_SERVER_URL
            //="http://10.178.211.134:8080/api";
            //= "http://192.168.43.119:8080/api";
            = "http://192.168.0.102:8080/api";

    //= "http://192.168.42.242:8080/api";

    public static final String EXTRA_KEY = "Beer_EXTRA_KEY";
    public static final long LIST_IDENTIFIER = 1;
    public static final long CREATE_IDENTIFIER = 2;
    public static final long DETAILS_IDENTIFIER = 3;
    public static final long LOGOUT_IDENTIFIER = 4;

    public static final long BEER_NAME_MIN_VALUE = 5;
    public static final long BEER_NAME_MAX_VALUE = 18;
    public static final String NAME_ERROR_MESSAGE = "Beer name must be between 5 and 18 chars";
    public static final String UNSUCCESSFUL_CREATION = "Unsuccessful create new beer!";
}
