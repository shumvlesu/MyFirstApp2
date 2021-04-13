package com.shumikhin.myfirstapp2.ui;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shumikhin.myfirstapp2.R;
import com.shumikhin.myfirstapp2.data.CardsSource;
import com.shumikhin.myfirstapp2.data.CardsSourceImpl;

public class SocialNetworkFragment extends Fragment {

    public static SocialNetworkFragment newInstance() {
        return new SocialNetworkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_socialnetwork, container, false);

        //ищем наш ресайкл вью у фрагмента
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);

        //Заполняем массив из нашего arrays
        //String[] data = getResources().getStringArray(R.array.titles);
        // Получим источник данных для списка
        CardsSource data = new CardsSourceImpl(getResources()).init();

        //Инициализируем менеджер для recyclerView в отдельном методе, так удобней
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, CardsSource data) {
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        //берем не LinearLayoutManager а GridLayoutManager что бы показать как можно при поворте менять количество столбцов
        int span = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 1;
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(),span);

        recyclerView.setLayoutManager(layoutManager);
        //layoutManager можно задать и в макете строкой как внизу, но это неудобно
        //например - app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"


        // Установим адаптер
        // В адапторе (SocialNetworkAdapter) мы заполняем данные из массива
        SocialNetworkAdapter adapter = new SocialNetworkAdapter(data);

        //Устанавливаем адаптер для RecyclerView
        recyclerView.setAdapter(adapter);


        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),  LinearLayoutManager.VERTICAL);

//        final int version = Build.VERSION.SDK_INT;
//        if (version >= 21) {
//            itemDecoration.setDrawable(ResourcesCompat.getDrawable(R.drawable.separator, 0, null));
//        } else {
            itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
//        }
        recyclerView.addItemDecoration(itemDecoration);





        //Область текста ответственная на обработку нажатий+++++++
        // Установим слушателя
        adapter.SetOnItemClickListener(new SocialNetworkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getContext(), String.format("%s - %d", ((TextView) view).getText(), position), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), String.format("Нажали котика - %d", position+1), Toast.LENGTH_SHORT).show();
            }
        });

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    }
}
