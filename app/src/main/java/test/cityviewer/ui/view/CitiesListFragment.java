package test.cityviewer.ui.view;

import android.arch.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import test.cityviewer.R;
import test.cityviewer.service.repository.storge.model.City;
import test.cityviewer.ui.adapter.CitiesPageListAdapter;
import test.cityviewer.ui.listeners.ItemClickListener;
import test.cityviewer.ui.viewmodel.CityDetailsViewModel;
import test.cityviewer.ui.viewmodel.CitiesListViewModel;


public class CitiesListFragment extends Fragment implements ItemClickListener {

    private static final String TAG = CitiesListFragment.class.getSimpleName();
    protected CitiesListViewModel viewModel;
    private CityDetailsViewModel detailsViewModel;

    protected RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private Button addBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities, container, false);

        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.citiesRecyclerView);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                boolean loading = false;
                int visibleThreshold = 5;
                if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                    loading = true;
                }
            }
        });

        viewModel = ViewModelProviders.of(getActivity()).get(CitiesListViewModel.class);
        observersRegisters();

        return view;
    }

    private void addTestCity() {
        City city = new City();
        city.setCountry("Italy");
        city.setCapital("Rome");

        List<String> images = new ArrayList<>();
        images.add("https://afar-production.imgix.net/uploads/syndication/holland_americas/images/s3YsgKtkvF/original_Rome.AGE.RM.crop.Y9R-2304587.jpg?w=1440&h=523&fit=crop");
        city.setBackdropPath(images);
        city.setDescription("Hot country");

    }

    private void fetchTimelineAsync(int page) {
        swipeContainer.setRefreshing(false);

    }

    private void observersRegisters() {

        final CitiesPageListAdapter pageListAdapter = new CitiesPageListAdapter(this);
        viewModel.getCities().observe(this, pageListAdapter::submitList);
        viewModel.getNetworkState().observe(this, networkState -> {
            pageListAdapter.setNetworkState(networkState);
        });
        recyclerView.setAdapter(pageListAdapter);
        detailsViewModel = ViewModelProviders.of(getActivity()).get(CityDetailsViewModel.class);
    }

    @Override
    public void OnItemClick(City city) {
        detailsViewModel.getCity().postValue(city);
        if (!detailsViewModel.getCity().hasActiveObservers()) {
            CityDetailsFragment detailsFragment = new CityDetailsFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragmentsContainer, detailsFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            AboutFragment detailsFragment = new AboutFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragmentsContainer, detailsFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }

        if (item.getItemId() == R.id.addPost) {

            AddCityFragment detailsFragment = new AddCityFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragmentsContainer, detailsFragment);
            transaction.addToBackStack(null);

            transaction.commit();

            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}
