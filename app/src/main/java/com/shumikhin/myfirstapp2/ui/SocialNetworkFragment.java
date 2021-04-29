package com.shumikhin.myfirstapp2.ui;

import android.content.Context;
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

import com.shumikhin.myfirstapp2.MainActivity;
import com.shumikhin.myfirstapp2.Navigation;
import com.shumikhin.myfirstapp2.R;
import com.shumikhin.myfirstapp2.data.CardData;
import com.shumikhin.myfirstapp2.data.CardSourceFirebaseImpl;
import com.shumikhin.myfirstapp2.data.CardsSource;
import com.shumikhin.myfirstapp2.data.CardsSourceResponse;
import com.shumikhin.myfirstapp2.observe.Observer;
import com.shumikhin.myfirstapp2.observe.Publisher;

public class SocialNetworkFragment extends Fragment {

    private static final int MY_DEFAULT_DURATION = 500;

    private CardsSource data;
    private SocialNetworkAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    // признак, что при повторном открытии фрагмента
    // (возврате из фрагмента, добавляющего запись)
    // надо прыгнуть на последнюю запись
    //private boolean moveToLastPosition;

    private boolean moveToFirstPosition;


    public static SocialNetworkFragment newInstance() {
        return new SocialNetworkFragment();
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Получим источник данных для списка
//        // Поскольку onCreateView запускается каждый раз
//        // при возврате в фрагмент, данные надо создавать один раз
//        data = new CardsSourceImpl(getResources()).init();
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_socialnetwork, container, false);

        //ищем наш ресайкл вью у фрагмента
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);

        // Получим источник данных для списка
        //data = new CardsSourceImpl(getResources()).init();

        //Инициализируем менеджер для recyclerView в отдельном методе, так удобней
        //initRecyclerView(recyclerView, data);
        initView(view);

        //У нас есть необязательное меню которое мы хотим увидеть в этом фрагменте.
        setHasOptionsMenu(true);

        data = new CardSourceFirebaseImpl().init(new CardsSourceResponse() {
            @Override
            public void initialized(CardsSource cardsData) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
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
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        //TODO в методичке 9 урока по созданию DatePicker и реализации паттерна наблюдатель
        // тут надо закоментить строку. data = new CardsSourceImpl(getResources()).init();

        // Получим источник данных для списка
        //data = new CardsSourceImpl(getResources()).init();
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
        adapter = new SocialNetworkAdapter(this);

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

        //надо перекрутить список в нсамый низ
//        if (moveToLastPosition){
//            recyclerView.smoothScrollToPosition(data.size() - 1);
//            moveToLastPosition = false;
//        }
        //Теперь крутим на первую позицию
        if (moveToFirstPosition && data.size() > 0){
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }

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
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private boolean onItemSelected(int menuItemId) {
        switch (menuItemId) {
            case R.id.action_add:
                navigation.addFragment(CardFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.addCardData(cardData);
                        adapter.notifyItemInserted(data.size() - 1);
                        // это сигнал, чтобы вызванный метод onCreateView
                        // перепрыгнул на начало списка
                        moveToFirstPosition = true;
                    }
                });
                return true;
            case R.id.action_update:
                final int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(CardFragment.newInstance(data.getCardData(updatePosition) ), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.updateCardData(updatePosition, cardData);
                        adapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;
            case R.id.action_delete:
                int deletePosition = adapter.getMenuPosition();
                data.deleteCardData(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }

}
