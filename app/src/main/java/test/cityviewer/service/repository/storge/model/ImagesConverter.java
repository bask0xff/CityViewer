package test.cityviewer.service.repository.storge.model;

import android.arch.persistence.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImagesConverter {
    @TypeConverter
    public String fromImages(List<String> images) {
        return images.stream().collect(Collectors.joining(","));
    }

    @TypeConverter
    public List<String> toImages(String data) {
        return Arrays.asList(data.split(","));
    }

}
