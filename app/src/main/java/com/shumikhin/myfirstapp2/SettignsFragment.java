package com.shumikhin.myfirstapp2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class SettignsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settigns, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initSwitchBackStack(view);
        initRadioAdd(view);
        initRadioReplace(view);
        initSwitchBackAsRemove(view);
        initSwitchDeleteBeforeAdd(view);
    }

    private void initSwitchBackStack(View view) {
        // Элемент пользовательского интерфейса — переключатель
        // По функционалу очень похож на CheckBox, но имеет другой дизайн
        SwitchCompat switchUseBackStack = view.findViewById(R.id.switchBackStack);
        switchUseBackStack.setChecked(Settings.isBackStack);
        switchUseBackStack.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isBackStack = isChecked;
            writeSettings();
        });
    }

    private void initRadioAdd(View view) {
        RadioButton radioButtonAdd = view.findViewById(R.id.radioButtonAdd);
        radioButtonAdd.setChecked(Settings.isAddFragment);
        radioButtonAdd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isAddFragment = isChecked;
            writeSettings();
        });
    }

    private void initRadioReplace(View view) {
        RadioButton radioButtonReplace = view.findViewById(R.id.radioButtonReplace);
        radioButtonReplace.setChecked(!Settings.isAddFragment);
        radioButtonReplace.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isAddFragment = !isChecked;
            writeSettings();
        });
    }

    private void initSwitchBackAsRemove(View view) {
        SwitchCompat switchBackAsRemove = view.findViewById(R.id.switchBackAsRemove);
        switchBackAsRemove.setChecked(Settings.isBackAsRemove);
        switchBackAsRemove.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isBackAsRemove = isChecked;
            writeSettings();
        });
    }

    private void initSwitchDeleteBeforeAdd(View view) {
        SwitchCompat switchDeleteBeforeAdd = view.findViewById(R.id.switchDeleteBeforeAdd);
        switchDeleteBeforeAdd.setChecked(Settings.isDeleteBeforeAdd);
        switchDeleteBeforeAdd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isDeleteBeforeAdd = isChecked;
            writeSettings();
        });
    }

    //В методе writeSettings() мы сохраняем значения настроек, чтобы при повторном запуске их
    //восстановить. Подробнее об этом мы поговорим на следующем курсе. В двух словах: получаем
    //объект SharedPreferences, при помощи этого объекта создаём Editor, куда сохраняем наши
    //настройки.

    // Сохранение настроек приложения
    private void writeSettings() {
        // Специальный класс для хранения настроек
        SharedPreferences sharedPref = requireActivity().getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        // Настройки сохраняются посредством специального класса editor
        SharedPreferences.Editor editor = sharedPref.edit();
        // Задаём значения настроек
        editor.putBoolean(Settings.IS_BACK_STACK_USED, Settings.isBackStack);
        editor.putBoolean(Settings.IS_ADD_FRAGMENT_USED, Settings.isAddFragment);
        editor.putBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, Settings.isBackAsRemove);
        editor.putBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, Settings.isDeleteBeforeAdd);
        // Сохраняем значения настроек
        editor.apply();
    }

}