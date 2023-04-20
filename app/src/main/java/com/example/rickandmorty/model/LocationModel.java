package com.example.rickandmorty.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class LocationModel implements Parcelable {
    int id;
    String name;
    String type;
    String dimension;
    List<String> residents;
    String url;
    String created;

    public LocationModel(int id, String name, String type, String dimension, List<String> residents, String url, String created) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.dimension = dimension;
        this.residents = residents;
        this.url = url;
        this.created = created;
    }

    protected LocationModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readString();
        dimension = in.readString();
        residents = in.createStringArrayList();
        url = in.readString();
        created = in.readString();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDimension() {
        return dimension;
    }

    public List<String> getResidents() {
        return residents;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(dimension);
        parcel.writeStringList(residents);
        parcel.writeString(url);
        parcel.writeString(created);
    }
}
