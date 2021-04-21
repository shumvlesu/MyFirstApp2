package com.shumikhin.myfirstapp2.observe;

import com.shumikhin.myfirstapp2.data.CardData;

import java.util.ArrayList;
import java.util.List;

//У нас будет разовый наблюдатель, и мы каждый раз будем его регистрировать, поскольку фрагмент
//будет создаваться каждый раз при редактировании записи. После отсылки сообщения будем
//отписывать наблюдатель. Паттерн «Наблюдатель» мы рассматривали в факультативе шестого урока.

public class Publisher {

    private List<Observer> observers; // Все обозреватели

    public Publisher() {
        observers = new ArrayList<>();
    }

    // Подписать
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие
    public void notifySingle(CardData cardData) {
        for (Observer observer : observers) {
            observer.updateCardData(cardData);
            unsubscribe(observer);
        }
    }

}

