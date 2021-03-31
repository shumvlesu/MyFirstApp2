package com.shumikhin.myfirstapp2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText txtName;
    private Account account;
    private static final int REQUEST_CODE_SETTING_ACTIVITY = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = new Account();
        initView();
    }

    private void initView() {
        Button btnGreetings = findViewById(R.id.btnGreetings);
        //final потому что не сразу может быть нажата кнопка и к этому моменту объекты уже могут кем то измениться
        //final EditText txtName = findViewById(R.id.textName);
        txtName = findViewById(R.id.textName);
        final TextView txtGreetings = findViewById(R.id.textHello);
        btnGreetings.setOnClickListener(v -> {
            String name = txtName.getText().toString();
            String sayHello = getString(R.string.say_hello) + name;
            txtGreetings.setText(sayHello);
        });

        Button btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            // Чтобы стартовать активити, надо подготовить интент
            // В данном случае это будет явный интент, поскольку здесь передаётся класс активити
            Intent runSettings = new Intent(MainActivity.this, SettingsActivity.class);
            // Передача данных через интент
            //по сути мы как бы пихаем наши данные в bundle
            //из SettingsActivity берем константу YOUR_NAME
            //runSettings.putExtra(SettingsActivity.YOUR_NAME, txtName.getText().toString());

            populateAccount();
            runSettings.putExtra(SettingsActivity.YOUR_ACCOUNT, account);
            // Метод стартует активити, указанную в интенте
            //startActivity(runSettings);

            //стартуем активити с возвратом результата
            startActivityForResult(runSettings, REQUEST_CODE_SETTING_ACTIVITY);


        });


    }

    private void populateAccount() {
        account.setName(txtName.getText().toString());
    }

    //переопределяем метод для получения ответа от второй активити
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode != REQUEST_CODE_SETTING_ACTIVITY) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == RESULT_OK) {
            account = data.getParcelableExtra(SettingsActivity.YOUR_ACCOUNT);
            populateView();
        }
    }

    private void populateView() {
        txtName.setText(account.getName());
    }


}