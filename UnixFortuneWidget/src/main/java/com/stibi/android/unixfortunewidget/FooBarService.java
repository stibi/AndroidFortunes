package com.stibi.android.unixfortunewidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class FooBarService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);

        Log.d(this.getPackageName(), "Startuje sluzba..");
        registBroadcastReceiver();

        return Service.START_NOT_STICKY;
    }

    private BroadcastReceiver broadcastReceiver = null;

    private void registBroadcastReceiver() {

        Log.d("com.stibi.android.unixfortunewidget", "weee registruju receiver");

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Intent.ACTION_SCREEN_ON)) {
                    Log.d("com.stibi.android.unixfortunewidget", "Svetlo :)");
//                    Intent initialUpdateIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//                    initialUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
//                    sendBroadcast(initialUpdateIntent);
                    Intent initialUpdateIntent = new Intent(UnixFortuneWidgetProvider.FORTUNE_UPDATE);
                    initialUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
                    sendBroadcast(initialUpdateIntent);
                } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                    Log.d("com.stibi.android.unixfortunewidget", "Tma :(");
                    Intent initialUpdateIntent = new Intent(UnixFortuneWidgetProvider.FORTUNE_UPDATE);
                    initialUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
                    sendBroadcast(initialUpdateIntent);
                }
            }
        };

        getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
