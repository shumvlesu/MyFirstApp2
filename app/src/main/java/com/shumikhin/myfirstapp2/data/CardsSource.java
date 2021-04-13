package com.shumikhin.myfirstapp2.data;

//добавим источник данных для списка таких карточек, но будем работать через интерфейс.
//Возможно, когда-нибудь мы поменяем реализацию интерфейса и будем доставать данные из базы
//данных, а не из ресурсов.
public interface CardsSource {
    CardData getCardData(int position);
    int size();
}

