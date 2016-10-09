package com.devdrunk.bicycle.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devdrunk.bicycle.R;
import com.devdrunk.bicycle.adapter.PagerMenuAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MainFragment extends Fragment {

    ViewPager viewPager;
    PagerMenuAdapter adapter;
    private NavigationTabStrip slideTabLayout;

    public MainFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        adapter = new PagerMenuAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        slideTabLayout = (NavigationTabStrip) rootView.findViewById(R.id.slideTabLayout);
        slideTabLayout.setTitles("MAP","RECORD","HISTORY");
        slideTabLayout.setTabIndex(0, true);
        slideTabLayout.setTitleSize(15);
        slideTabLayout.setStripColor(Color.GREEN);
        slideTabLayout.setStripWeight(6);
        slideTabLayout.setStripFactor(2);
        slideTabLayout.setStripType(NavigationTabStrip.StripType.LINE);
        slideTabLayout.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        slideTabLayout.setTypeface("fonts/typeface.ttf");
        slideTabLayout.setCornersRadius(3);
        slideTabLayout.setAnimationDuration(300);
        slideTabLayout.setInactiveColor(Color.WHITE);
        slideTabLayout.setActiveColor(Color.GREEN);

        slideTabLayout.setViewPager(viewPager);



    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

}
