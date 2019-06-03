package test.cityviewer.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import test.cityviewer.R;
import test.cityviewer.service.repository.storge.model.City;
import test.cityviewer.ui.viewmodel.CitiesListViewModel;

public class CitiesActivity extends AppCompatActivity {

    private static final String TAG = CitiesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        if (findViewById(R.id.fragmentsContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }

            CitiesListFragment citiesListFragment = new CitiesListFragment();

            citiesListFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentsContainer, citiesListFragment).commit();
        }
    }


}

