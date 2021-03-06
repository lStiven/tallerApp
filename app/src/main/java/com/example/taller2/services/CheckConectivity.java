package com.example.taller2.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.taller2.activity.LoginActivity;

public class CheckConectivity extends Service {
    Handler handler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(periodicUpdate);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean isOnline(Context c) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;

        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }

        return isWifiConn || isMobileConn;

        /*if (isWifiConn || isMobileConn) {
            Toast.makeText(this, "Conectado a la red", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No está conectado a la red", Toast.LENGTH_SHORT).show();
        }*/
    }

    private final Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 1000 - SystemClock.elapsedRealtime() % 1000);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(LoginActivity.BroadcastStringForAction);
            broadcastIntent.putExtra("online_status",""+isOnline(CheckConectivity.this));
            //Toast.makeText(null, "Conectado a la red:" + isOnline(CheckConectivity.this), Toast.LENGTH_SHORT).show();
            //System.out.println(isOnline(CheckConectivity.this));

            sendBroadcast(broadcastIntent);

        }
    };
}