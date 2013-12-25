package com.stibi.android.unixfortunewidget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.stibi.android.unixfortunewidget.api.Constants;

// TODO interface needed (?)

public class PagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

//    private ViewPager mPager;
 private FortunesViewPager mPager;
    boolean mNeedsRedraw = false;

    int currentPage;

    public PagerContainer(Context context) {
        super(context);
        init();
    }

    public PagerContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        //Disable clipping of children so non-selected pages are visible
        setClipChildren(false);

        //Child clipping doesn't work with hardware acceleration in Android 3.x/4.x
        //You need to set this value here if using hardware acceleration in an
        // application targeted at these releases.
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onFinishInflate() {
        try {
            mPager = (FortunesViewPager) getChildAt(0);
            mPager.setOnPageChangeListener(this);
            // TODO ty vole co to tu dela, to je bordel
            currentPage = mPager.getCurrentItem();
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    public FortunesViewPager getViewPager() {
        return mPager;
    }

    private Point mCenter = new Point();
    private Point mInitialTouch = new Point();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenter.x = w / 2;
        mCenter.y = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouch.x = (int) event.getX();
                mInitialTouch.y = (int) event.getY();
            default:
                event.offsetLocation(mCenter.x - mInitialTouch.x, mCenter.y - mInitialTouch.y);
                break;
        }
        return mPager.dispatchTouchEvent(event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//        Log.d(Constants.LOG, String.format("Page scrolled from %s to %s", currentPage, position));

        //Log.d(Constants.LOG,
        //        String.format("onPageScrolled: int postition=%s, float possitionOffset=%s, int positionOffsetPixels=%s",
        //                position, positionOffset, positionOffsetPixels));


        // TODO konstanty
//        if (position == 1 && positionOffset == 0) {
//            Log.d(Constants.LOG, "Kuch time!");
//            removeDatFortune();
//        }

//        if (positionOffset == 0) {
//            Log.d(Constants.LOG, "Swipe! Bude se kuchat!");
//        }

        if (mNeedsRedraw) invalidate();
    }

    @Override
    public void onPageSelected(int pagePosition) {
        Log.d(Constants.LOG, String.format("Page scrolled from %s to %s", currentPage, pagePosition));

//        int positionToBeRemoved = pagePosition - 1;
//        Log.d(Constants.LOG, "Budu mazat page na position=" + positionToBeRemoved);
//        ((FortunesPagerAdapter) mPager.getAdapter()).removeFortuneAtPosition(positionToBeRemoved);
//        mPager.setCurrentItem();
        //if (pagePosition > currentPage) {
        //    ((FortunesPagerAdapter) mPager.getAdapter()).removeFortuneAtPosition(currentPage);
        //}

        currentPage = pagePosition;
        //Log.d(Constants.LOG, String.format("onPageSelected i=%s", i));
        //mPager.setCurrentItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            Log.d(Constants.LOG, "idle..");
            int positionToBeDeleted = mPager.getCurrentItem() - 1;
            if (positionToBeDeleted >= 0) {
                ((FortunesPagerAdapter) mPager.getAdapter()).removeFortuneAtPosition(positionToBeDeleted);
                goToFirstFortune();
                ((FortunesPagerAdapter) mPager.getAdapter()).addFortuneToEnd();
            }
        }
    }

    // TODO DOC
    // TODO interface needed!
    public boolean removeDatFortune() {
        int position = mPager.getCurrentItem();
        String fortuneAtPositionText = ((FortunesPagerAdapter) mPager.getAdapter()).getFortuneTextAtPosition(position);

        Log.d(Constants.LOG, String.format("Kuchneme fortunku na position=%s text=%s", position, fortuneAtPositionText));

        return true;
    }

    public boolean removeFirstFortune() {
        return true;
    }

    private void goToFirstFortune() {
        Log.d(Constants.LOG, "Jdu na prvni fortunku");
        mPager.setCurrentItem(0, false);
    }
}
