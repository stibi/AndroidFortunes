package com.stibi.android.unixfortunewidget;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onResume() {
        super.onResume();
    }

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
            case R.id.action_db:
                showFortuneDb(getCurrentFocus());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showFortuneDb(View view) {
        Intent intent = new Intent(this, FortunesDbActivity.class);
        startActivity(intent);
    }

    // TODO fragment do vlastni clasy? Co je best practices?
    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {

        View rootView;

        private PagerContainer fortunesContainer;
        private ViewPager fortunesViewPager;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);

//            fortunesViewPager = (ViewPager) rootView.findViewById(R.id.pager);
            fortunesContainer = (PagerContainer) rootView.findViewById(R.id.pager_container);
            fortunesViewPager = fortunesContainer.getViewPager();

            PagerAdapter pagerAdapter = new FortunesPagerAdapter(getSupportFragmentManager(), getApplicationContext());
            fortunesViewPager.setAdapter(pagerAdapter);
            fortunesViewPager.setOffscreenPageLimit(pagerAdapter.getCount());
            fortunesViewPager.setPageMargin(15);
            fortunesViewPager.setClipChildren(false);

            return rootView;
        }
    }
}
