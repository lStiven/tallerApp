package com.example.taller2.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.taller2.R;
import com.example.taller2.databinding.ActivityLoginBinding;
import com.example.taller2.menus.GlobalMenu;
import com.example.taller2.services.CheckConectivity;

public class LoginActivity extends GlobalMenu {
    private IntentFilter mIntentFilter;
    @NonNull
    ActivityLoginBinding binding;
    public static final String BroadcastStringForAction = "checkinternet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, CheckConectivity.class));
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BroadcastStringForAction);

        Intent i = new Intent(getApplicationContext(), CheckConectivity.class);
        startService(i);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BroadcastStringForAction)) {
                if (!intent.getStringExtra("online_status").equals("true")) {

                    ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
                    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

                    if(!("class " + cn.getClassName()).equals(LoginActivity.class.toString())){
                        startActivity(new Intent(context, LoginActivity.class));
                    }
                }
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(myReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mIntentFilter);
    }
}