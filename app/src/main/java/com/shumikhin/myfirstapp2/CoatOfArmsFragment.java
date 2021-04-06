package com.shumikhin.myfirstapp2;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

public class CoatOfArmsFragment extends Fragment {

    //public static final String ARG_INDEX = "index";
    //private int index;
    public static final String ARG_CITY = "city";
    private City city;

    //Этот фрагмент примечателен тем, что у него есть фабричный метод newInstance. То есть метод,
    //который умеет создавать объект. Этот метод получает на входе параметр — индекс элемента массива
    //с городами, который затем передаётся дальше. По этому индексу приложение вытаскивает
    //изображение герба из массива coat_of_arms_imgs. Метод onCreateView должен вернуть
    //наследника от класса View. Это всегда должен быть макет экрана, который вы хотите отобразить.
    //Метод onCreateView во фрагменте — аналог метода setContentView в активити.

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    //public static CoatOfArmsFragment newInstance(int index) {
    public static CoatOfArmsFragment newInstance(City city) {
        CoatOfArmsFragment fragment = new CoatOfArmsFragment(); // создание
        // Передача параметра
        Bundle args = new Bundle();
        //args.putInt(ARG_INDEX, index);
        args.putParcelable(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //index = getArguments().getInt(ARG_INDEX);
            city = getArguments().getParcelable(ARG_CITY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_coat_of_arms, container, false);
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_coat_of_arms, container, false);

        // найти в контейнере элемент-изображение
        AppCompatImageView imageCoatOfArms = view.findViewById(R.id.coat_of_arms);

        // Получить из ресурсов массив указателей на изображения гербов
        TypedArray images = getResources().obtainTypedArray(R.array.coat_of_arms_imgs);

        // Выбрать по индексу подходящий
        //imageCoatOfArms.setImageResource(images.getResourceId(index, -1));
        imageCoatOfArms.setImageResource(images.getResourceId(city.getImageIndex(),-1));

        // Установить название города
        TextView cityNameView = view.findViewById(R.id.textView);
        cityNameView.setText(city.getCityName());


        return view;
    }
}