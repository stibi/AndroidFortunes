package com.stibi.android.unixfortunewidget;

import android.app.Application;

import com.stibi.android.unixfortunewidget.data.FortunesDataSource;

public class UnixFortuneApp extends Application {

    // TODO diky tomuto se k dataSource dostanu kdekoli z aplikace. Fakt to potrebuji? Co takhle DI?
    public FortunesDataSource dataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        dataSource = new FortunesDataSource(this.getApplicationContext());
    }
}
