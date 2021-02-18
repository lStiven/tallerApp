package com.example.taller2;

import android.os.Bundle;

import com.example.taller2.menus.GlobalMenu;

public class LoginActivity extends GlobalMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

}