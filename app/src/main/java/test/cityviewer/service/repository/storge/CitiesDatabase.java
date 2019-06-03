package test.cityviewer.service.repository.storge;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import test.cityviewer.service.repository.storge.model.City;
import test.cityviewer.service.repository.storge.paging.DBCitiesDataSourceFactory;

import static test.cityviewer.Constants.DATA_BASE_NAME;
import static test.cityviewer.Constants.NUMBERS_OF_THREADS;


/**
 * The Room database that contains the Users table
 */
@Database(entities = {City.class}, version = 3)
public abstract class CitiesDatabase extends RoomDatabase {

    private static final String TAG = CitiesDatabase.class.getSimpleName();
    private static CitiesDatabase instance;
    public abstract CityDao cityDao();
    private static final Object sLock = new Object();
    private LiveData<PagedList<City>> citiesPaged;

    public static CitiesDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        CitiesDatabase.class, DATA_BASE_NAME)
                        .build();
                instance.init();

            }
            return instance;
        }
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        DBCitiesDataSourceFactory dataSourceFactory = new DBCitiesDataSourceFactory(cityDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        citiesPaged = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<City>> getCities() {
        return citiesPaged;
    }
}
