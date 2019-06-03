package test.cityviewer.service.repository.network;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import test.cityviewer.service.repository.network.paging.NetCitiesDataSourceFactory;
import test.cityviewer.service.repository.network.paging.NetCitiesPageKeyedDataSource;
import test.cityviewer.service.repository.storge.model.City;
import test.cityviewer.service.repository.storge.model.NetworkState;

import static test.cityviewer.Constants.LOADING_PAGE_SIZE;
import static test.cityviewer.Constants.NUMBERS_OF_THREADS;

/**
 * .
 */

public class CitiesNetwork {

    final private static String TAG = CitiesNetwork.class.getSimpleName();
    final private LiveData<PagedList<City>> citiesPaged;
    final private LiveData<NetworkState> networkState;

    public CitiesNetwork(NetCitiesDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<City> boundaryCallback){
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                (Function<NetCitiesPageKeyedDataSource, LiveData<NetworkState>>)
                        NetCitiesPageKeyedDataSource::getNetworkState);
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        citiesPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }


    public LiveData<PagedList<City>> getPagedCities(){
        return citiesPaged;
    }



    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
