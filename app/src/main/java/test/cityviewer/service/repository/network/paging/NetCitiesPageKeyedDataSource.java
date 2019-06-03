package test.cityviewer.service.repository.network.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.ReplaySubject;
import test.cityviewer.service.repository.network.api.CitiesAPIInterface;
import test.cityviewer.service.repository.network.api.TheCityDBAPIClient;
import test.cityviewer.service.repository.storge.model.City;
import test.cityviewer.service.repository.storge.model.NetworkState;

public class NetCitiesPageKeyedDataSource extends PageKeyedDataSource<String, City> {

    private static final String TAG = NetCitiesPageKeyedDataSource.class.getSimpleName();
    private final CitiesAPIInterface citiesService;
    private final MutableLiveData networkState;
    private final ReplaySubject<City> citiesObservable;
    private final ReplaySubject<City> cityObservable;

    NetCitiesPageKeyedDataSource() {
        citiesService = TheCityDBAPIClient.getClient();
        networkState = new MutableLiveData();
        citiesObservable = ReplaySubject.create();
        cityObservable = ReplaySubject.create();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<City> getCities() {
        return citiesObservable;
    }

    public ReplaySubject<City> addCity(City city) {
        return cityObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, City> callback) {
        networkState.postValue(NetworkState.LOADING);

        loadInitialCities(callback);
    }

    private void loadInitialCities(LoadInitialCallback<String, City> callback) {
        Call<ArrayList<City>> callBack = citiesService.getCities(1);
        callBack.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(1), Integer.toString(2));
                    networkState.postValue(NetworkState.LOADED);
                    response.body().forEach(citiesObservable::onNext);
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));

                    loadInitialCities(callback);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {
                String errorMessage;
                Log.e(TAG, "onFailure-1: " + t.getMessage() );
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(), Integer.toString(1), Integer.toString(2));
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, City> callback) {
        networkState.postValue(NetworkState.LOADING);
        final AtomicInteger page = new AtomicInteger(0);
        try {
            page.set(Integer.parseInt(params.key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        getCities(callback, page.get());
    }

    private void getCities(LoadCallback<String, City> callback, int pageNumber) {
        Call<ArrayList<City>> callBack = citiesService.getCities(pageNumber);
        callBack.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                if (response.isSuccessful()) {
                    networkState.postValue(NetworkState.LOADED);
                    response.body().forEach(citiesObservable::onNext);
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("loadAfter API CALL", response.message());

                    getCities(callback, pageNumber);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {
                String errorMessage;
                Log.e(TAG, "onFailure-2: " + t.getMessage() );
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(),Integer.toString(pageNumber));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, City> callback) {

    }
}
