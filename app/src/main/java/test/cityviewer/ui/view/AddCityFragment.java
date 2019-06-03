package test.cityviewer.ui.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import test.cityviewer.R;
import test.cityviewer.service.repository.storge.model.City;

public class AddCityFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_city, container, false);

        City city = new City();
        city.setCountry("Italy");
        city.setCapital("Rome");
        city.setDescription("Hot country");

        List<String> images = new ArrayList<>();
        images.add("https://afar-production.imgix.net/uploads/syndication/holland_americas/images/s3YsgKtkvF/original_Rome.AGE.RM.crop.Y9R-2304587.jpg?w=1440&h=523&fit=crop");
        city.setBackdropPath(images);

        //view.findViewById()

        return view;
    }
}