package com.stibi.android.unixfortunewidget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.stibi.android.unixfortunewidget.api.Constants;
import com.stibi.android.unixfortunewidget.data.Fortune;
import com.stibi.android.unixfortunewidget.data.FortunesDataSource;

import java.util.ArrayList;
import java.util.List;

public class FortunesPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private FortunesDataSource dataSource;
    private List<String> fortunesTexts = new ArrayList<String>();

    public FortunesPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        dataSource = new FortunesDataSource(context);
        dataSource.open();
        initializeWithSomeRandomFortunes();
    }

    private void initializeWithSomeRandomFortunes() {
        for (int i = 0; i < 3; i++) {
            Fortune randomFortune = dataSource.getRandomFortune();
            Log.d(Constants.LOG, "Init nahodnou fortunkou - " + randomFortune);
            fortunesTexts.add(randomFortune.getText());
        }
    }


    // Used by ViewPager.  "Object" represents the page; tell the ViewPager where the
    // page should be displayed, from left-to-right.  If the page no longer exists,
    // return POSITION_NONE.
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return FortuneFragment.newInstance(fortunesTexts.get(position));
    }

    // Used by ViewPager.  Called when ViewPager needs a page to display; it is our job
    // to add the page to the container, which is normally the ViewPager itself.  Since
    // all our pages are persistent, we simply retrieve it from our "views" ArrayList.
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
////        TextView textView = new TextView(context);
////        textView.setText("Item " + position);
////        textView.setGravity(Gravity.CENTER);
////        textView.setBackgroundColor(Color.argb(255, position * 50, position * 10, position * 50));
////
////        container.addView(textView);
////        return textView;
//        return FortuneFragment.newInstance(textProvider.getTextForPosition(position));
//    }

    // Used by ViewPager.  Called when ViewPager no longer needs a page to display; it
    // is our job to remove the page from the container, which is normally the
    // ViewPager itself.  Since all our pages are persistent, we do nothing to the
    // contents of our "views" ArrayList.
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//    }

    // Used by ViewPager; can be used by app as well.
    // Returns the total number of pages that the ViewPage can display.  This must
    // never be 0.
//    @Override
//    public int getCount() {
//        // TODO udelat si na toto konstantu s hezkym nazvem
//        return 5;
//    }

    @Override
    public int getCount() {
        return fortunesTexts.size();
    }

    public String getFortuneTextAtPosition(int position) {
        return fortunesTexts.get(position);
    }

    public void removeFortuneAtPosition(int position) {
        Log.d(Constants.LOG, "Mazu fortunku na position=" + position);
        fortunesTexts.remove(position);
        notifyDataSetChanged();
    }

    public void addFortuneToEnd() {
        String fortuneText = dataSource.getRandomFortune().getText();
        Log.d(Constants.LOG, "Adding new fortune with text=" + fortuneText);
        fortunesTexts.add(fortuneText);
//        counter += 1;
        notifyDataSetChanged();
    }


//    @Override
//    public boolean isViewFromObject(View view, Object o) {
//        return (view == o);
//    }

//    public int addFortuneView(View view, int position) {
//
//    }
//
//    public int removeFortuneView(View view, int position) {
//
//    }
}
