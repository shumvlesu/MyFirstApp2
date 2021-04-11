package com.shumikhin.myfirstapp2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readSettings();
        initView();
    }

    private void initView() {
        //initToolbar();
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

        initButtonMain();
        initButtonFavorite();
        initButtonSettings();
        initButtonBack();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    // регистрация drawer
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_settings:
//                addFragment(new SettignsFragment());
//                return true;
//            case R.id.action_main:
//                addFragment(new MainFragment());
//                return true;
//            case R.id.action_favorite:
//                addFragment(new FavoriteFragment());
//                return true;
        if (navigateFragment(id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                addFragment(new SettignsFragment());
                return true;
            case R.id.action_main:
                addFragment(new MainFragment());
                return true;
            case R.id.action_favorite:
                addFragment(new FavoriteFragment());
                return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Здесь определяем меню приложения (активити)
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }

    private void initButtonMain() {
        Button buttonMain = findViewById(R.id.buttonMain);
        buttonMain.setOnClickListener(v -> addFragment(new MainFragment()));
    }

    private void initButtonFavorite() {
        Button buttonFavorite = findViewById(R.id.buttonFavorite);
        buttonFavorite.setOnClickListener(v -> addFragment(new FavoriteFragment()));
    }

    private void initButtonSettings() {
        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(v -> addFragment(new SettignsFragment()));
    }

    private void initButtonBack() {
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (Settings.isBackAsRemove) {
                Fragment fragment = getVisibleFragment(fragmentManager);
                if (fragment != null) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                }
            } else {
                fragmentManager.popBackStack(); //вытолкним то что есть
            }
        });
    }

    //getVisibleFragment получение видимого фрагмента.
    // Пробегается по стеку фрагментов и тот который сверху стека тот фрагмент и видимый
    private Fragment getVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        int countFragments = fragments.size();
        //от последнего к первому в обратном порядке
        for (int i = countFragments - 1; i >= 0; i--) {
            Fragment fragment = fragments.get(i);
            if (fragment.isVisible())
                return fragment;
        }
        return null;
    }


    //Метод addFragment() добавляет фрагмент на экран. Первым действием получаем менеджер
    //фрагментов FragmentManager fragmentManager = getSupportFragmentManager(). Затем
    //создаётся транзакция FragmentTransaction fragmentTransaction =
    //fragmentManager.beginTransaction(), внутри которой происходят действия
    //fragmentTransaction.add(), fragmentTransaction.replace(), которые можно отправить в
    //стек обратного вызова fragmentTransaction.addToBackStack(null). В конце операции пишем
    //завершение транзакции fragmentTransaction.commit().
    //При сохранении в стек обратного вызова можно написать имя операции действия с фрагментами
    //fragmentTransaction.addToBackStack(name), чтобы в дальнейшем достать из стека
    //именованную операцию. То есть при вызове метода fragmentManager.popBackStack(name,
    //flags) мы сразу перепрыгиваем на именованную транзакцию с определённым фрагментом.
    //Name — ранее заданное имя, flags — признак, что мы будем включать именованную транзакцию или
    //не будем её включать. Если сюда поставить 0, то на экране окажется фрагмент с именованной
    //транзакцией. Если 1 или POP_BACK_STACK_INCLUSIVE, то на экране будет предыдущее состояние
    //относительно именованной транзакции.
    //При возврате в предыдущее состояние можно удалить фрагмент. Или, если мы сохраняем состояние
    //фрагментов в стек, можно вытащить из стека предыдущее состояние.

    private void addFragment(Fragment fragment) {
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Удалить видимый фрагмент
        if (Settings.isDeleteBeforeAdd) {
            Fragment fragmentToRemove = getVisibleFragment(fragmentManager);
            if (fragmentToRemove != null) {
                fragmentTransaction.remove(fragmentToRemove);
            }
        }
        // Добавить фрагмент
        if (Settings.isAddFragment) {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }
        // Добавить транзакцию в бэкстек
        if (Settings.isBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }

    // Чтение настроек
    private void readSettings() {
        // Специальный класс для хранения настроек
        SharedPreferences sharedPref = getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        // Считываем значения настроек
        Settings.isBackStack = sharedPref.getBoolean(Settings.IS_BACK_STACK_USED, false); //если значения еще не было установлено то указываем значение в по умолчанию - false.
        Settings.isAddFragment = sharedPref.getBoolean(Settings.IS_ADD_FRAGMENT_USED, true);
        Settings.isBackAsRemove = sharedPref.getBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, true);
        Settings.isDeleteBeforeAdd = sharedPref.getBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, false);
    }
}
