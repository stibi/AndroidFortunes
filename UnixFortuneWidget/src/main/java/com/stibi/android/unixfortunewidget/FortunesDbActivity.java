package com.stibi.android.unixfortunewidget;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stibi.android.unixfortunewidget.api.Constants;
import com.stibi.android.unixfortunewidget.data.Fortune;
import com.stibi.android.unixfortunewidget.data.FortunesDataSource;
import com.stibi.android.unixfortunewidget.database.FortunesDbHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FortunesDbActivity extends Activity {

    private FortunesDataSource dataSource;
    private TextView trpaslikImportProgress;
    private TextView dbItemsCounter;
    private TextView dbStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.LOG, "FortunesDbActivity onCreate");
        setContentView(R.layout.fortunes_db_layout);

        dataSource = new FortunesDataSource(this);
        dataSource.open();

        trpaslikImportProgress = (TextView) findViewById(R.id.progressText);
        dbItemsCounter = (TextView) findViewById(R.id.fortunesInDbCount);
        dbStatus = (TextView) findViewById(R.id.dbStatus);

        updateFortunesCount();
        updateDbStatus();

        //List<Fortune> comments = dataSource.getAllComments();
        //ArrayAdapter<Fortune> adapter = new ArrayAdapter<Fortune>(this, android.R.layout.simple_list_item_1, comments);
        //setListAdapter(adapter);
    }

    private List<String> openTrpaslikFortunes() {
        InputStream is = getResources().openRawResource(R.raw.reddwarf);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        StringBuilder jednaFortunka = new StringBuilder();

        List<String> listFortunek = new ArrayList<String>();

        try {
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals("%")) {
                    jednaFortunka.append(line);
                } else {
                    Log.d("com.stibi.android.unixfortunewidget", "Mam fortunku!");
                    listFortunek.add(jednaFortunka.toString());
                    jednaFortunka = new StringBuilder();
                }
            }

            is.close();
            br.close();

        } catch (IOException e) {
            Log.d("com.stibi.android.unixfortunewidget", "ups");
            e.printStackTrace();
        }

        return listFortunek;
    }

    private class AsyncFortunesDbSave extends AsyncTask<List<String>, String, String> {
        @Override
        protected String doInBackground(List<String>... listFortunek) {
            List<String> fortunkyToImport = listFortunek[0];
            int x = 0;
            publishProgress(String.format("Importing %s/%s", x, fortunkyToImport.size()));
            for (String fortunka : fortunkyToImport) {
                dataSource.saveFortune("trpaslik", fortunka);
                x += 1;
                publishProgress(String.format("Importing %s/%s", x, fortunkyToImport.size()));
            }
            return "tadaaaa";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            trpaslikImportProgress.setText(values[0]);
            super.onProgressUpdate(values);

        }
    }

    public void trpaslikImport(View view) {
        AsyncFortunesDbSave fortunesDbSave = new AsyncFortunesDbSave();
        fortunesDbSave.execute(openTrpaslikFortunes());
        updateDbStatus();
        updateFortunesCount();
    }

    private void updateFortunesCount() {
        long fortunesCount = dataSource.getFortunesCount();
        Log.d(Constants.LOG, "updateFortunesCount() fortunesCount=" + fortunesCount);
        dbItemsCounter.setText(String.format("%d fortunes in DB", fortunesCount));
    }

    private boolean isFortuneDbExists() {
        File dbFile = getApplicationContext().getDatabasePath(dataSource.getDbName());
        return dbFile.exists();
    }

    private void updateDbStatus() {
        boolean fortuneDbExists = isFortuneDbExists();
        dbStatus.setText("DB exists: " + fortuneDbExists);
    }

    @Override
    protected void onResume() {
        Log.d(Constants.LOG, "FortunesDbActivity onResume");
        dataSource.open();
        updateDbStatus();
        updateFortunesCount();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(Constants.LOG, "FortunesDbActivity onPause");
        dataSource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.fortunes_db, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_db:
                break;
            case R.id.drop_db:
                dataSource.dropDb();
                updateDbStatus();
                updateFortunesCount();
        }
        return true;
    }
}
