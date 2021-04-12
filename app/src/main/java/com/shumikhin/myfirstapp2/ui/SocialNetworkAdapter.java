package com.shumikhin.myfirstapp2.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shumikhin.myfirstapp2.R;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.ViewHolder> {

    private String[] dataSource;

    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public SocialNetworkAdapter(String[] dataSource) {
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public SocialNetworkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Создаём новый элемент пользовательского интерфейса
        // Через Inflater
        //viewGroup это контейнер куда мы помещаем наш item
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        // Здесь можно установить всякие параметры
        return new ViewHolder(itemView);
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull SocialNetworkAdapter.ViewHolder viewHolder, int i) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder
        viewHolder.getTextView().setText(dataSource[i]); //заносим какой то текст в item. i это позиция в списке.
    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        public TextView getTextView() {
            return textView;
        }
    }

}
