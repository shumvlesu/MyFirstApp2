package com.shumikhin.myfirstapp2.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shumikhin.myfirstapp2.R;
import com.shumikhin.myfirstapp2.data.CardData;
import com.shumikhin.myfirstapp2.data.CardsSource;
import com.shumikhin.myfirstapp2.data.CardsSourceImpl;

public class SocialNetworkFragment extends Fragment {

    public static SocialNetworkFragment newInstance() {
        return new SocialNetworkFragment();
    }

    private static final int MY_DEFAULT_DURATION = 500;
    private CardsSource data;
    private SocialNetworkAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_socialnetwork, container, false);

        //ищем наш ресайкл вью у фрагмента
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);

        // Получим источник данных для списка
        data = new CardsSourceImpl(getResources()).init();

        //Инициализируем менеджер для recyclerView в отдельном методе, так удобней
        //initRecyclerView(recyclerView, data);
        initView(view);

        //У нас есть необязательное меню которое мы хотим увидеть в этом фрагменте.
        setHasOptionsMenu(true);

        return view;
    }


    //Этими двумя переопределенными методами мы добавляем меню
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
    }

    //тут переопределяем элементы меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                data.addCardData(new CardData("Заголовок " + data.size(), "Описание " + data.size(), R.drawable.cat1, false));
                //оповещаем наш ресайклвью где добавился элемент (в конце)
                adapter.notifyItemInserted(data.size() - 1);

                //и скролим на эту позицию список
                //recyclerView.scrollToPosition(data.size() - 1);
                //скролим с анимацией
                recyclerView.smoothScrollToPosition(data.size() - 1);

                return true;
            case R.id.action_clear:

                //data.clearCardData();
                //оповещаем наш ресайклвью
                //adapter.notifyDataSetChanged();

                //Более интересный с точки зрения эстетики вариант
                while (data.size() != 0) {
                    data.deleteCardData(0);
                    adapter.notifyItemRemoved(0);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        // Получим источник данных для списка
        data = new CardsSourceImpl(getResources()).init();
        initRecyclerView();
    }


    //private void initRecyclerView(RecyclerView recyclerView, CardsSource data) {
    private void initRecyclerView() {
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        //берем не LinearLayoutManager а GridLayoutManager что бы показать как можно при поворте менять количество столбцов
        int span = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 1;
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), span);

        recyclerView.setLayoutManager(layoutManager);
        //layoutManager можно задать и в макете строкой как внизу, но это неудобно
        //например - app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"


        // Установим адаптер
        // В адапторе (SocialNetworkAdapter) мы заполняем данные из массива и пихаем наш фрагмент (this) в адаптер для привязки контекстного меню ему
        adapter = new SocialNetworkAdapter(data, this);

        //Устанавливаем адаптер для RecyclerView
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);

//        final int version = Build.VERSION.SDK_INT;
//        if (version >= 21) {
        itemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.separator));
//        } else {
        //itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null)); //устарело
//        }
        recyclerView.addItemDecoration(itemDecoration);


        //Анимация recyclerView
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Установим анимацию. А чтобы было хорошо заметно, сделаем анимацию долгой
        DefaultItemAnimator animator = new DefaultItemAnimator();
        //скорость анимации при добавлении
        animator.setAddDuration(MY_DEFAULT_DURATION);
        //скорость анимации при удалении
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++


        //Область текста ответственная на обработку нажатий+++++++
        // Установим слушателя
        adapter.SetOnItemClickListener(new SocialNetworkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getContext(), String.format("%s - %d", ((TextView) view).getText(), position), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), String.format("Нажали котика - %d", position + 1), Toast.LENGTH_SHORT).show();
            }
        });

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    }

    //переопределением этих методов мы добавляем на фрагмент контекстное меню
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                //по сути оставим все тоже самое но только поменяем заголовок карточки
                data.updateCardData(position,
                        new CardData("Кадр " + position,
                                data.getCardData(position).getDescription(),
                                data.getCardData(position).getPicture(),
                                false));
                //обновляем ресайкл
                adapter.notifyItemChanged(position);
                return true;
            case R.id.action_delete:
                data.deleteCardData(position);
                //обновляем ресайкл для обновления при удалении элемента
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++


}
