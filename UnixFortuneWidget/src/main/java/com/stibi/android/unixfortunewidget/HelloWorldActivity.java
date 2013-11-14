package com.stibi.android.unixfortunewidget;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;

public class HelloWorldActivity extends Activity {

//    private BroadcastReceiver broadcastReceiver = null;

//    public HelloWorldActivity() {
//        super();
//    }


    @Override
    protected void onResume() {
        super.onResume();
        //registBroadcastReceiver();
    }

//    private void registBroadcastReceiver() {
//        final IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
//        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
//
//        broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String action = intent.getAction();
//                if (action.equals(Intent.ACTION_SCREEN_ON) || action.equals(Intent.ACTION_SCREEN_OFF)) {
//                    Log.d("com.stibi.android.unixfortunewidget", "tyyyyy vole");
//                    Intent initialUpdateIntent=new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//                    initialUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
//                    sendBroadcast(initialUpdateIntent);
//                }
//            }
//        };
//
//        getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello_world, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        Button startServiceButton;

//        public void addStartServiceButtonListener() {
//            startServiceButton = (Button) findViewById(R.id.sluzba_buttonek);
//            startServiceButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //Intent startServiceIntent = new Intent(getApplicationContext(), FooBarService.class);
//                    //getApplicationContext().startService(startServiceIntent);
//                    Log.d(getPackageName(), "klik!");
//                }
//            });
//        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Button startServiceButton = (Button) rootView.findViewById(R.id.sluzba_buttonek);
            startServiceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("com.stibi.android.unixfortunewidget", "klik spusteni sluzby");
                    Intent startServiceIntent = new Intent(rootView.getContext(), FooBarService.class);
                    rootView.getContext().startService(startServiceIntent);
                }
            });

            return rootView;
        }
    }

}
