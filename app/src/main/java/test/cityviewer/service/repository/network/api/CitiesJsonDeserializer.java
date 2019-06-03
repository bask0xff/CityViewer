package test.cityviewer.service.repository.network.api;


import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import test.cityviewer.Constants;
import test.cityviewer.service.repository.storge.model.City;

class CitiesJsonDeserializer implements JsonDeserializer {

    private static String TAG = CitiesJsonDeserializer.class.getSimpleName();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<City> cities = null;
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray citiesJsonArray = jsonObject.getAsJsonArray(Constants.CITIES_ARRAY_DATA_TAG);
            cities = new ArrayList<>(citiesJsonArray.size());
            for (int i = 0; i < citiesJsonArray.size(); i++) {
                City dematerialized = context.deserialize(citiesJsonArray.get(i), City.class);
                cities.add(dematerialized);
            }
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize City element: %s", json.toString()));
        }
        return cities;
    }
}
