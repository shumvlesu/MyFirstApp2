package com.shumikhin.myfirstapp2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//Выделяем отдельный класс навигации, чтобы разгрузить активити. Туда перенесём создание
//фрагмента. Первый фрагмент не будем записывать в стек обратного вызова, чтобы по кнопке
//«Назад» просто выйти из приложения.

public class Navigation {

    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment, boolean useBackStack) {
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.my_fragment_container, fragment);
        if (useBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }

}
