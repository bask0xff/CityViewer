package test.cityviewer;



import java.lang.reflect.Type;
import java.util.ArrayList;

import test.cityviewer.service.repository.storge.model.City;

public class Constants {
    // network
    public static final String CITIES_ARRAY_DATA_TAG = "response";
    public static final Type CITY_ARRAY_LIST_CLASS_TYPE = (new ArrayList<City>()).getClass();
    public static final String POPULAR_CITIES_BASE_URL = "https://gitlab.com/snippets/1717200/";
    public static final String PAGE_REQUEST_PARAM = "page";
    public static final int LOADING_PAGE_SIZE = 20;
    // DB
    public static final String DATA_BASE_NAME = "Cities.db";
    public static final int NUMBERS_OF_THREADS = 3;
}
