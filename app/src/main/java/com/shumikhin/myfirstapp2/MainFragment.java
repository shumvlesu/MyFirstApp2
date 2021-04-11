package com.shumikhin.myfirstapp2;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Добавляем в меню пункт именно для этого фрагмента
        setHasOptionsMenu(true);//Этим методом мы как бы перехватываем управление существуещего меню (main.xml)

        //создаем попап меню
        initPopupMenu(view);
        return view;
    }

    //Для создания меню во фрагменте необходимо установить настройку setHasOptionsMenu(true). И
    //переопределить методы, которые очень похожи, но не идентичны, в активити
    //onCreateOptionsMenu() и onOptionsItemSelected().

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Toast.makeText(getContext(), "Chosen add", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Все что связано с попап меню++++++++++++++++++++++++++++++
    private void initPopupMenu(View view) {
        //попап меню будет вываливаться у текста Main screen
        TextView text = view.findViewById(R.id.textView);
        text.setOnClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, v);
            activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
            Menu menu = popupMenu.getMenu(); //получили меню

            //скрыли пункт попап меню
            menu.findItem(R.id.item2_popup).setVisible(false);
            //а тут добавили новый пункт
            menu.add(0, 123456, 12, R.string.new_menu_item_added);

            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item1_popup:
                        Toast.makeText(getContext(), "Chosen popup item 1", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item2_popup:
                        Toast.makeText(getContext(), "Chosen popup item 2", Toast.LENGTH_SHORT).show();
                        return true;
                    case 123456:
                        Toast.makeText(getContext(), "Chosen new item added", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            });
            popupMenu.show();
        });
    }
    //Все что связано с попап меню---------------------------------------------

}
