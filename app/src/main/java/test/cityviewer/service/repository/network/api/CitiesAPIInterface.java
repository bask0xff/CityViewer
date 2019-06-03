package test.cityviewer.service.repository.network.api;



import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.cityviewer.service.repository.storge.model.City;

import static test.cityviewer.Constants.*;

public interface CitiesAPIInterface {

    @GET("raw")
    Call<ArrayList<City>> getCities(@Query(PAGE_REQUEST_PARAM) int page);
}
