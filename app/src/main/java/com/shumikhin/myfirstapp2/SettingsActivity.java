package com.shumikhin.myfirstapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    //public static final  String YOUR_NAME = "YOUR_NAME";
    public static final String YOUR_ACCOUNT = "YOUR_ACCOUNT";
    private EditText editName;
    private EditText editSurname;
    private EditText editAge;
    private EditText editEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initView();
    }

    private void initView() {

        editName = findViewById(R.id.editName);
        editSurname = findViewById(R.id.editSurname);
        editAge = findViewById(R.id.editAge);
        editEmail = findViewById(R.id.editEmail);


        //EditText editName = findViewById(R.id.editName);

        // получить данные из Intent по имени нашей константы YOUR_NAME
        //String text = getIntent().getExtras().getString(YOUR_NAME);

        Account account = getIntent().getExtras().getParcelable(YOUR_ACCOUNT);

        // Сохранить их в поле на экране
        //editName.setText(text);
        populateView(account);

        Button btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(v -> {

            //возвращаем первой активити резултат в виде нового объекта класса аккаунт.
            Intent intentResult = new Intent();
            intentResult.putExtra(YOUR_ACCOUNT, createAccount());
            setResult(RESULT_OK, intentResult);

            // Метод finish() завершает активити
            finish();
        });
    }

    private void populateView(Account account) {
        editName.setText(account.getName());
        editSurname.setText(account.getSurName());
        editAge.setText(String.format(Locale.getDefault(), "%d", account.getAge()));
        editEmail.setText(account.getEmail());
    }

    private Account createAccount(){
        Account account = new Account(
                editName.getText().toString(),
                editSurname.getText().toString(),
                Integer.parseInt(editAge.getText().toString()),
                editEmail.getText().toString());
        return account;
    }


}
