package com.shumikhin.myfirstapp2.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardsSourceFirebaseImpl implements CardsSource {

    //идентефикатор нашей коллекции карточек
    private static final String CARDS_COLLECTION = "cards";
    //для отладки
    private static final String TAG = "[CardsSourceFirebaseImpl]";
    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    // Коллекция наших карт, мы их получили по наименованию константы CARDS_COLLECTION - cards
    private CollectionReference collection = store.collection(CARDS_COLLECTION);
    // Загружаемый список карточек
    private List<CardData> cardsData = new ArrayList<CardData>();


    //Метод get класса коллекции получает данные. Если требуется отсортировать эти данные, то
    //вызываем метод orderBy. Мы получаем типизированный класс Task с типом возврата
    //QuerySnapshot, который является результатом запроса. На этот Task можно повесить слушателя при
    //успешной и неуспешной работе задачи. Если задача была выполнена успешно, это ещё не значит, что
    //у нас есть правильный результат. Поэтому необходимо проверить, что задача выполнилась успешно и
    //хранит в себе правильный результат, за это отвечает метод isSuccessful().
    //Результат — это коллекция документов, где каждый документ — словарь с ключом-значением
    //(Map<String, Object>). Само преобразование между словарём и объектом вынесено в отдельный
    //класс, чтобы не засорять логику класса работы с Firestore.

    @Override
    public CardsSource init(final CardsSourceResponse cardsSourceResponse) {
        // Получить всю коллекцию, отсортированную по полю «Дата»
        collection.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    // При удачном считывании данных загрузим список карточек
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cardsData = new ArrayList<CardData>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                CardData cardData = CardDataMapping.toCardData(id, doc);
                                cardsData.add(cardData);
                            }
                            Log.d(TAG, "success " + cardsData.size() + " qnt");
                            cardsSourceResponse.initialized(CardsSourceFirebaseImpl.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public CardData getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        if (cardsData == null) {
            return 0;
        }
        return cardsData.size();
    }

    @Override
    public void deleteCardData(int position) {
        // Удалить документ с определённым идентификатором
        collection.document(cardsData.get(position).getId()).delete();
        cardsData.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        String id = cardData.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(CardDataMapping.toDocument(cardData));
    }

    @Override
    public void addCardData(final CardData cardData) {
        // Добавить документ
        collection.add(CardDataMapping.toDocument(cardData))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        cardData.setId(documentReference.getId());
                    }
                });
    }

    @Override
    public void clearCardData() {
        for (CardData cardData : cardsData) {
            collection.document(cardData.getId()).delete();
        }
        cardsData = new ArrayList<CardData>();
    }

}
