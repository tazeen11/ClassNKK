package com.example.DBsqlite;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tazeen.classnkk.Inspector_Tab;
import com.example.tazeen.classnkk.Vessels_Tab;

/**
 * Created by Tazeen on 20-07-2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new Vessels_Tab();
            case 1:
                // Games fragment activity
               return new Inspector_Tab();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}
