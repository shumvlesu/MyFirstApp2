package com.shumikhin.myfirstapp2.observe;

import com.shumikhin.myfirstapp2.data.CardData;

//Это интерфейс реализующий паттерн наблюдателя
public interface Observer {
    void updateCardData(CardData cardData);
}
