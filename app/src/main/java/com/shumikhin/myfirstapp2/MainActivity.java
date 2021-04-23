package com.shumikhin.myfirstapp2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.shumikhin.myfirstapp2.observe.Publisher;
import com.shumikhin.myfirstapp2.ui.SocialNetworkFragment;

public class MainActivity extends AppCompatActivity {

    private Navigation navigation;
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Создаем навигатор
        navigation = new Navigation(getSupportFragmentManager());
        initToolbar();
        //закидываем фрагмент на активити
        //addFragment(SocialNetworkFragment.newInstance());

        //Кидаем SocialNetworkFragment на активити (теперь по паттерну наблюдатель)
        //Первый фрагмент не будем записывать в стек обратного вызова (useBackStack), чтобы по кнопке «Назад» просто выйти из приложения.
        getNavigation().addFragment(SocialNetworkFragment.newInstance(), false);

    }

    private void initToolbar() {
//      Toolbar toolbar = findViewById(R.id.toolbar);
//      setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    //Устарело, теперь пользуемся классом навигатор
    //  private void addFragment(Fragment fragment) {
    //  //Получить менеджер фрагментов
    //  FragmentManager fragmentManager = getSupportFragmentManager();
    //  // Открыть транзакцию
    //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    //  fragmentTransaction.replace(R.id.my_fragment_container, fragment);
    //  fragmentTransaction.addToBackStack(null);
    //  // Закрыть транзакцию
    //  fragmentTransaction.commit();
    //}

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public Navigation getNavigation() {
        return navigation;
    }
    public Publisher getPublisher() {
        return publisher;
    }

}