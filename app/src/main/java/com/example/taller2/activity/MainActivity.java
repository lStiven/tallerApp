package com.example.taller2.activity;

import android.os.Bundle;

import com.example.taller2.R;
import com.example.taller2.menus.GlobalMenu;

public class MainActivity extends GlobalMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}