package br.com.brunocardoso.desafionw.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoList {
    @SerializedName("photos")
    @Expose
    private List<String> photos;

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
