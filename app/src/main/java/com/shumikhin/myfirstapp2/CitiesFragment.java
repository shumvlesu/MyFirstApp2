package com.shumikhin.myfirstapp2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class CitiesFragment extends Fragment {

    public static final String CURRENT_CITY = "CurrentCity";
    //private int currentPosition = 0; // Текущая позиция (выбранный город)
    private City currentCity;
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    //Вызывается, когда view фрагмента создан. В качестве
    //аргумента приходит сам view, у которого можно находить и инициализировать компоненты:
    //view.findViewById(...).

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Определение, можно ли будет расположить рядом герб в другом фрагменте
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        initView(view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            //currentPosition = savedInstanceState.getInt(CURRENT_CITY);
            currentCity = savedInstanceState.getParcelable(CURRENT_CITY);
        } else {
            // Если восстановить не удалось, то сделаем объект с первым индексом
            currentCity = new City(0, getResources().getStringArray(R.array.cities)[0]);
        }



        // Если можно нарисовать рядом герб, то сделаем это
        if (isLandscape) {
            //showLandCoatOfArms(currentPosition);
            showLandCoatOfArms(currentCity);
        }
    }


    private void initView(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] cities = getResources().getStringArray(R.array.cities);
        // В этом цикле создаём элемент TextView,
        // заполняем его значениями
        // и добавляем на экран.
        // Кроме того, создаём обработку касания на элемент
        for (int i = 0; i < cities.length; i++) {
            String city = cities[i];
            TextView tv = new TextView(getContext());
            tv.setText(city);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int index = i;
            tv.setOnClickListener(v -> {
//                // Откроем вторую activity
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), CoatOfArmsActivity.class);
//                // и передадим туда параметры
//                intent.putExtra(CoatOfArmsFragment.ARG_INDEX, index);
//                startActivity(intent);

                //currentPosition = index;
                //showCoatOfArms(currentPosition);

                currentCity = new City(index, getResources().getStringArray(R.array.cities)[index]);
                showCoatOfArms(currentCity);

            });
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //outState.putInt(CURRENT_CITY, currentPosition);

        outState.putParcelable(CURRENT_CITY, currentCity);
        super.onSaveInstanceState(outState);
    }


    //private void showCoatOfArms(int index) {
    private void showCoatOfArms(City currentCity) {
        if (isLandscape) {
            //showLandCoatOfArms(index);
            showLandCoatOfArms(currentCity);
        } else {
            //showPortCoatOfArms(index);
            showPortCoatOfArms(currentCity);
        }
    }

    // Показать герб в ландшафтной ориентации
    //private void showLandCoatOfArms(int index) {
    private void showLandCoatOfArms(City currentCity) {
        // Создаём новый фрагмент с текущей позицией для вывода герба с помощью фабричного метода (название паттерна).
        //Фабричый метод это метод который работает с объектом до его собственно создания.
        //тут я до создания объекта уже задаю для него индекс картинки герба. что бы при создании фрагмента он указал мне на нужный герб.
        //CoatOfArmsFragment detail = CoatOfArmsFragment.newInstance(index);
        CoatOfArmsFragment detail = CoatOfArmsFragment.newInstance(currentCity);

        // Выполняем транзакцию по замене фрагмента
        requireActivity().getSupportFragmentManager() //
                .beginTransaction() //фрагмент меняется в транзакции
                .replace(R.id.coat_of_arms, detail) // замена фрагмента
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //тип анимации замены транзакции
                .commit();

        //менее читаемый вид
        //FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.coat_of_arms, detail); // замена фрагмента
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //fragmentTransaction.commit();
    }

    // Показать герб в портретной ориентации.
    //private void showPortCoatOfArms(int index) {
    private void showPortCoatOfArms(City currentCity) {
        // Откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), CoatOfArmsActivity.class);
        // и передадим туда параметры
        //intent.putExtra(CoatOfArmsFragment.ARG_INDEX, index);
        intent.putExtra(CoatOfArmsFragment.ARG_CITY, currentCity);
        startActivity(intent);
    }


}