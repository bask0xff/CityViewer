package test.cityviewer.service.repository.storge;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import test.cityviewer.service.repository.storge.model.City;

import java.util.List;

@Dao
public interface CityDao {

    @Query("SELECT * FROM cities")
    List<City> getCities();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCity(City city);

    @Query("DELETE FROM cities")
    abstract void deleteAllCities();
}
