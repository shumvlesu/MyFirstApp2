package com.shumikhin.myfirstapp2.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

//класс данных (он же pogo объект) — содержимое карточки
public class CardData implements Parcelable {
    private final String title;       // заголовок
    private final String description; // описание
    private final int picture;        // изображение
    private final boolean like;       // флажок
    private Date date;                // дата

    public CardData(String title, String description, int picture, boolean like, Date date) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.like = like;
        this.date = date;
    }

    protected CardData(Parcel in) {
        title = in.readString();
        description = in.readString();
        picture = in.readInt();
        like = in.readByte() != 0;
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(picture);
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }
        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }

    public boolean isLike() {
        return like;
    }

    public Date getDate() {
        return date;
    }

}
