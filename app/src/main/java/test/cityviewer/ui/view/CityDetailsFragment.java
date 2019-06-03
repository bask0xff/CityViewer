package test.cityviewer.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import test.cityviewer.R;
import test.cityviewer.databinding.FragmentDetailsBinding;
import test.cityviewer.ui.viewmodel.CityDetailsViewModel;

public class CityDetailsFragment extends Fragment {

    private CityDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(CityDetailsViewModel.class);
        View view = binding.getRoot();
        viewModel.getCity().observe(this, binding::setCity);
        return view;
    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        if(url != null) {
            Picasso.get().load(url).into(view);
        }
    }
}
