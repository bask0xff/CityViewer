package test.cityviewer.service.repository.network.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;


import rx.subjects.ReplaySubject;
import test.cityviewer.service.repository.storge.model.City;

public class NetCitiesDataSourceFactory extends DataSource.Factory {

    private static final String TAG = NetCitiesDataSourceFactory.class.getSimpleName();
    private MutableLiveData<NetCitiesPageKeyedDataSource> networkStatus;
    private NetCitiesPageKeyedDataSource citiesPageKeyedDataSource;
    public NetCitiesDataSourceFactory() {
        this.networkStatus = new MutableLiveData<>();
        citiesPageKeyedDataSource = new NetCitiesPageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        networkStatus.postValue(citiesPageKeyedDataSource);
        return citiesPageKeyedDataSource;
    }

    public MutableLiveData<NetCitiesPageKeyedDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public ReplaySubject<City> getCities() {
        return citiesPageKeyedDataSource.getCities();
    }

    public ReplaySubject<City> addCity(City city) {
        return citiesPageKeyedDataSource.addCity(city);
    }

}
