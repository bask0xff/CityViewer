package test.cityviewer.service.repository.storge.paging;

import android.arch.paging.DataSource;

import test.cityviewer.service.repository.storge.CityDao;


public class DBCitiesDataSourceFactory extends DataSource.Factory {

    private static final String TAG = DBCitiesDataSourceFactory.class.getSimpleName();
    private DBCitiesPageKeyedDataSource citiesPageKeyedDataSource;
    public DBCitiesDataSourceFactory(CityDao dao) {
        citiesPageKeyedDataSource = new DBCitiesPageKeyedDataSource(dao);
    }

    @Override
    public DataSource create() {
        return citiesPageKeyedDataSource;
    }

}
