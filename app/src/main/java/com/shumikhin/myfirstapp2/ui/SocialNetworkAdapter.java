package com.shumikhin.myfirstapp2.ui;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.shumikhin.myfirstapp2.R;
import com.shumikhin.myfirstapp2.data.CardData;
import com.shumikhin.myfirstapp2.data.CardsSource;


//это класс для управления адаптером
public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.ViewHolder> {

    private OnItemClickListener itemClickListener; // Слушатель будет устанавливаться извне
    private static final String TAG = "SocialNetworkAdapter";
    private CardsSource dataSource;

    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public SocialNetworkAdapter(CardsSource dataSource) {
        this.dataSource = dataSource;
        Log.d(TAG, "start");
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
        Log.d(TAG, "onCreateViewHolder");
        // Здесь можно установить всякие параметры
        return new ViewHolder(itemView);
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull SocialNetworkAdapter.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder");
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder
        //viewHolder.getTextView().setText(dataSource[i]); //заносим какой то текст в item. i это позиция в списке.
        viewHolder.setData(dataSource.getCardData(i));

//        if (i%3 == 0 ){
//            viewHolder.textView.setBackgroundColor(Color.BLUE);
//        }else {
//            viewHolder.textView.setBackgroundColor(Color.WHITE);
//        }
    }

    // Вернуть размер данных, вызывается менеджером
    //число итемов не должно быть больше числа строк массива
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        //return dataSource.length;
        return dataSource.size();
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        //private TextView textView;
        private TextView title;
        private TextView description;
        private AppCompatImageView image;
        private CheckBox like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //textView = (TextView) itemView;
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageView);
            like = itemView.findViewById(R.id.like);


            //Область текста ответственная на обработку нажатий+++++++
            // Обработчик нажатий на этом ViewHolder
            image.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        }

        public void setData(CardData cardData){
            title.setText(cardData.getTitle());
            description.setText(cardData.getDescription());
            like.setChecked(cardData.isLike());
            image.setImageResource(cardData.getPicture());
        }

        //public TextView getTextView() {
        //    return textView;
        //}

    }


    //Область текста ответственная на обработку нажатий+++++++

    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

}



