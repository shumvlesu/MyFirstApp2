package com.shumikhin.myfirstapp2;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CoatOfArmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coat_of_arms);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то надо эту activity закрыть
            finish();
            return;
        }

        //Здесь fragment мы программно в активити содаем а там только контейнер описываем в xml c id - fragment_container
        //Чтобы программно вставить фрагмент, надо получить «Менеджер фрагментов», затем открыть
        //транзакцию, вставить макет и закрыть транзакцию. Метод работы, как видим, транзакционный. Мы
        //можем сделать несколько действий в одной транзакции, например один макет удалить, второй
        //добавить. Чтобы получить менеджер фрагментов, из активити необходимо вызывать метод
        //getSupportFragmentManager(). Метод getFragmentManager() становится устаревшим, начиная
        //с API 28.

        if (savedInstanceState == null) {
            // Если эта activity запускается первый раз (с каждым новым гербом первый раз),
            // то перенаправим параметр фрагменту
            CoatOfArmsFragment details = new CoatOfArmsFragment();
            details.setArguments(getIntent().getExtras());
            // Добавим фрагмент на activity
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, details).commit();
        }


    }
}