package com.shumikhin.myfirstapp2.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.shumikhin.myfirstapp2.MainActivity;
import com.shumikhin.myfirstapp2.R;
import com.shumikhin.myfirstapp2.data.CardData;
import com.shumikhin.myfirstapp2.data.PictureIndexConverter;
import com.shumikhin.myfirstapp2.observe.Publisher;

import java.util.Calendar;
import java.util.Date;

//Это фрагмент со стандартной обработкой. При вызове метода onAttach фрагмент цепляется к
//активити. Поскольку у нас одна активити, то мы получаем из неё паблишер, при помощи которого
//будем посылать сигнал при завершении фрагмента. В работе с датой есть нюансы: очень много
//методов устарело, поскольку эти методы не учитывали тип календаря. Например, в Иране и
//Афганистане используется иранский календарь, в Китае, Вьетнаме и ещё ряде восточных стран —
//китайский календарь и т. п. Современный подход работы с датой — это использование календарей.
//Его демонстрируют методы getDateFromDatePicker() и initDatePicker().


public class CardFragment extends Fragment {
    private static final String ARG_CARD_DATA = "Param_CardData";
    private CardData cardData; // Данные по карточке
    private Publisher publisher; // Паблишер, с его помощью обмениваемся данными
    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;

    // Для редактирования данных
    public static CardFragment newInstance(CardData cardData) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    // Для добавления новых данных
    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    //При вызове метода onAttach фрагмент цепляется к активити. Поскольку у нас одна активити, то мы получаем из неё паблишер,
    // при помощи которого будем посылать сигнал при завершении фрагмента
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        initView(view);
        // если cardData пустая, то это добавление
        if (cardData != null) {
            populateView();
        }
        return view;
    }

    // Здесь соберём данные из views
    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();
    }

    // Здесь передадим данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(cardData);
    }

    private CardData collectCardData() {
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        Date date = getDateFromDatePicker();
//        int picture;
//        boolean like;
        if (cardData != null) {
//            picture = cardData.getPicture();
//            like = cardData.isLike();
            CardData answer;
            answer = new CardData(title, description, cardData.getPicture(), cardData.isLike(), date);
            answer.setId(cardData.getId());
            return answer;
        } else {
//            picture = R.drawable.cat1;
//            like = false;
            int picture = PictureIndexConverter.getPictureByIndex(PictureIndexConverter.randomPictureIndex());
            return new CardData(title, description, picture, false, date);
        }
        //return new CardData(title, description, picture, like, date);
    }

    // Получение даты из DatePicker
    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        description = view.findViewById(R.id.inputDescription);
        datePicker = view.findViewById(R.id.inputDate);
    }

    private void populateView() {
        title.setText(cardData.getTitle());
        description.setText(cardData.getDescription());
        // Установка даты в DatePicker
        initDatePicker(cardData.getDate());
    }

    // Установка даты в DatePicker
    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }
}
