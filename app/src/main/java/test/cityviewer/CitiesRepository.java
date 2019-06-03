package test.cityviewer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.paging.PagedList;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import test.cityviewer.service.repository.network.CitiesNetwork;
import test.cityviewer.service.repository.network.paging.NetCitiesDataSourceFactory;
import test.cityviewer.service.repository.storge.CitiesDatabase;
import test.cityviewer.service.repository.storge.model.City;
import test.cityviewer.service.repository.storge.model.NetworkState;

import rx.schedulers.Schedulers;

public class CitiesRepository {
    private static final String TAG = CitiesRepository.class.getSimpleName();
    private static CitiesRepository instance;
    final private CitiesNetwork network;
    final private CitiesDatabase database;
    final private MediatorLiveData liveDataMerger;
    private ExecutorService executorService;

    NetCitiesDataSourceFactory dataSourceFactory = new NetCitiesDataSourceFactory();

    private CitiesRepository(Context context) {

        network = new CitiesNetwork(dataSourceFactory, boundaryCallback);
        database = CitiesDatabase.getInstance(context.getApplicationContext());
        executorService = Executors.newSingleThreadExecutor();

        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getPagedCities(), value -> {
            liveDataMerger.setValue(value);
            Log.d(TAG, value.toString());
        });

        dataSourceFactory.getCities().
                observeOn(Schedulers.io()).
                subscribe(city -> {
                    database.cityDao().insertCity(city);
                });

    }

    private PagedList.BoundaryCallback<City> boundaryCallback = new PagedList.BoundaryCallback<City>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(database.getCities(), value -> {
                liveDataMerger.setValue(value);
                liveDataMerger.removeSource(database.getCities());
            });
        }
    };

    public static CitiesRepository getInstance(Context context){
        if(instance == null){
            instance = new CitiesRepository(context);
        }
        return instance;
    }

    public LiveData<PagedList<City>> getCities(){
        return  liveDataMerger;
    }

    public LiveData<City> getCity(){
        return  liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }


    public LiveData<PagedList<City>> setCity(City city) {
        executorService.execute(() -> database.cityDao().insertCity(city));

        liveDataMerger.addSource(database.getCities(), value -> {
            liveDataMerger.setValue(value);
            liveDataMerger.removeSource(database.getCities());
        });

        return liveDataMerger;
    }
}
