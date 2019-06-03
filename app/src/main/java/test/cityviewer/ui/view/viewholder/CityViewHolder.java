package test.cityviewer.ui.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import test.cityviewer.R;
import test.cityviewer.service.repository.storge.model.City;
import test.cityviewer.ui.listeners.ItemClickListener;

public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private City city;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView thumbnailImageView;
    private ItemClickListener itemClickListener;

    public CityViewHolder(View view, ItemClickListener itemClickListener) {
        super(view);
        this.titleTextView = view.findViewById(R.id.title);
        this.descriptionTextView = view.findViewById(R.id.description);
        this.thumbnailImageView = view.findViewById(R.id.thumbnail);
        this.itemClickListener = itemClickListener;

        view.setOnClickListener(this);
    }

    public void bindTo(City city) {
        this.city = city;
        titleTextView.setText(city.getCapital());
        descriptionTextView.setText(String.format("%s", city.getCountry()));
        if (city.getPosterPath() != null) {
            String poster = city.getPosterPath();
            if (poster != null) {
                try {
                    Picasso.get()
                            .load(poster)
                            .centerCrop()
                            .fit()
                            .into(thumbnailImageView)
                    ;
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClick(city);
        }
    }

}
