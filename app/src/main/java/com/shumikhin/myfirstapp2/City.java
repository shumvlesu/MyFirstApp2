package com.shumikhin.myfirstapp2;

import android.os.Parcel;
import android.os.Parcelable;

class City implements Parcelable {
    private int imageIndex;
    private String cityName;

    public City(int imageIndex, String cityName) {
        this.imageIndex = imageIndex;
        this.cityName = cityName;
    }

    protected City(Parcel in) {
        imageIndex = in.readInt();
        cityName = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getImageIndex());
        parcel.writeString(getCityName());
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public String getCityName() {
        return cityName;
    }
}
