package com.worldline.techforum.obfuscation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Francois Lolom on 14/05/2016.
 */
public class AboutFragment extends BottomBarFragment {

    public static final String TAG = "AboutFragment";

    @Bind(R.id.about_scrollview) ScrollView scrollView;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void scrollToTop() {
        scrollView.scrollTo(0, 0);
    }
}
