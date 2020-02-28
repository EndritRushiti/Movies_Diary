package com.rushiti.endrit.moviesdiary.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rushiti.endrit.moviesdiary.ui.list.ToWatchFragment;
import com.rushiti.endrit.moviesdiary.ui.list.WatchedFragment;

public class PageTabAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[] { "To watch", "Watched"};

    PageTabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ToWatchFragment.newInstance();
        }
        return WatchedFragment.newInstance();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}