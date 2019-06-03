package test.cityviewer.service.repository.storge.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@Entity(tableName = "cities")
public class City extends BaseObservable {

    private static final String TAG = City.class.getSimpleName();
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "country")
    @SerializedName(value = "country")
    private String mCountry;
    @ColumnInfo(name = "capital")
    @SerializedName(value = "capital")
    private String mCapital;
    @TypeConverters({ImagesConverter.class})
    @ColumnInfo(name = "images")
    @SerializedName(value = "images")
    private List<String> mBackdropPath;
    @ColumnInfo(name = "description")
    @SerializedName(value = "description")
    private String mDescription;

    public static DiffUtil.ItemCallback<City> DIFF_CALLBACK = new DiffUtil.ItemCallback<City>() {
        @Override
        public boolean areItemsTheSame(@NonNull City oldItem, @NonNull City newItem) {
            return oldItem.getCountry().equals(newItem.getCountry());
        }

        @Override
        public boolean areContentsTheSame(@NonNull City oldItem, @NonNull City newItem) {
            return oldItem.getCountry().equals(newItem.getCountry());
        }
    };

    @Bindable
    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    @Bindable
    public String getCapital() {
        return mCapital;
    }

    public void setCapital(String mCapital) {
        this.mCapital = mCapital;
    }

    @Bindable
    public List<String> getBackdropPath() {
        return mBackdropPath;
    }

    @Bindable
    public String getPosterPath() {
        if (mBackdropPath == null || mBackdropPath.size() < 1) return null;

        //ignore urls like this: "https://en.wikipedia.org/wiki/Moscow#/media/File:MSK_Collage_2015.png"
        for(String path : mBackdropPath){
            if(!path.contains("#"))
                return path;
        }

        return null;
    }

    public void setBackdropPath(List<String> mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    @Bindable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

}