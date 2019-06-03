package test.cityviewer.ui.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import test.cityviewer.service.repository.storge.model.City;

public class CityDetailsViewModel extends ViewModel {
    final private MutableLiveData city;

    public CityDetailsViewModel() {
        city = new MutableLiveData<City>();
    }

    public MutableLiveData<City> getCity() {
        return city;
    }
}
