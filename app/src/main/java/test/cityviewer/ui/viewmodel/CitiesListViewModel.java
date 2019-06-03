package test.cityviewer.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import test.cityviewer.CitiesRepository;
import test.cityviewer.service.repository.storge.CityDao;
import test.cityviewer.service.repository.storge.model.City;
import test.cityviewer.service.repository.storge.model.NetworkState;

public class CitiesListViewModel extends AndroidViewModel {
    private static final String TAG = CitiesListViewModel.class.getSimpleName();
    private CitiesRepository repository;

    private CityDao postDao;
    private ExecutorService executorService;

    public CitiesListViewModel(@NonNull Application application) {
        super(application);
        repository = CitiesRepository.getInstance(application);

        //postDao = CityD.getInstance(application).postDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<PagedList<City>> getCities() {
        return repository.getCities();
    }

    public LiveData<City> getCity() {
        return repository.getCity();
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

    public void addCity(City city) {
        Log.d(TAG, "addCity: " + city);

        return;
    }

}
