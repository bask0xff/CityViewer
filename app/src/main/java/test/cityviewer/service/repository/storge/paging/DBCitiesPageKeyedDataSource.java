package test.cityviewer.service.repository.storge.paging;

import android.arch.paging.PageKeyedDataSource;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import test.cityviewer.service.repository.storge.CityDao;
import test.cityviewer.service.repository.storge.model.City;

public class DBCitiesPageKeyedDataSource extends PageKeyedDataSource<String, City> {

    public static final String TAG = DBCitiesPageKeyedDataSource.class.getSimpleName();
    private final CityDao cityDao;
    public DBCitiesPageKeyedDataSource(CityDao dao) {
        cityDao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, City> callback) {
        List<City> cities = cityDao.getCities();
        if(cities.size() != 0) {
            callback.onResult(cities, "0", "1");
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, City> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, City> callback) {
    }
}
