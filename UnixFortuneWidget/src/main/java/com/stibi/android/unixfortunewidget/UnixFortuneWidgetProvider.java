package com.stibi.android.unixfortunewidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UnixFortuneWidgetProvider extends AppWidgetProvider {

//    public UnixFortuneWidgetProvider() {
//        super();
//        Log.d("com.stibi.android.unixfortunewidget", "fortunky init man!");
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

//        final int N = appWidgetIds.length;
//
//        for (int i = 0; i < N; i++) {
//            int appWidgetId = appWidgetIds[i];
//
//            Intent intent = new Intent(context, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//            //views.setOnClickPendingIntent(R.id.button, pendingIntent);
//
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//        }
    }

    public static final String FORTUNE_UPDATE = "com.stibi.android.unixfortunewidget.FORTUNE_UPDATE";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (FORTUNE_UPDATE.equals(action)) {
            Log.d("com.stibi.android.unixfortunewidget", "Whoa! budeme apdejtovat fortunku!");
        }
    }
}
