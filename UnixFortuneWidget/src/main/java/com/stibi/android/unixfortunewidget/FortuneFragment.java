package com.stibi.android.unixfortunewidget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FortuneFragment extends Fragment {

    private final String fortuneText;

    public static FortuneFragment newInstance(String fortuneText) {
        FortuneFragment fortuneFragment = new FortuneFragment(fortuneText);
        return fortuneFragment;
    }

    public FortuneFragment(String fortuneText) {
        this.fortuneText = fortuneText;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fortune_fragment_layout, container, false);

        TextView fortuneTextView = (TextView) root.findViewById(R.id.fortune_text);
        fortuneTextView.setText(fortuneText);
        fortuneTextView.setMovementMethod(new ScrollingMovementMethod());

        return root;
    }
}
