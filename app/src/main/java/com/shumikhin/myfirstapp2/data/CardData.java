package com.shumikhin.myfirstapp2.data;

//класс данных (он же pogo объект) — содержимое карточки
public class CardData {
    private final String title;       // заголовок
    private final String description; // описание
    private final int picture;        // изображение
    private final boolean like;       // флажок

    public CardData(String title, String description, int picture, boolean like) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.like = like;
    }

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
}
