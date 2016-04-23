package com.dino.ncsu.dinorunner.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dino.ncsu.dinorunner.Fragment.EquipmentFragment;
import com.dino.ncsu.dinorunner.Fragment.ItemsFragment;
import com.dino.ncsu.dinorunner.Fragment.StatsFragment;
import com.dino.ncsu.dinorunner.R;
import com.viewpagerindicator.TabPageIndicator;

public class PlayerStatsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabPageIndicator tabs;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    //private variables to track for this activity
    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stats);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        tabs = (TabPageIndicator) findViewById(R.id.titles);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabs.setViewPager(mViewPager);

        if (savedInstanceState == null) {
            check = 0;
        } else {
            check = savedInstanceState.getInt("check", 0);
        }

        mViewPager.setCurrentItem(check);
    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        check = mViewPager.getCurrentItem();
        state.putInt("check", check);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        //All available fragments for this sections adapter
        private StatsFragment statsFragment;
        private EquipmentFragment equipmentFragment;
        private ItemsFragment itemsFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    if (statsFragment == null)
                        statsFragment = new StatsFragment();
                    return statsFragment;
                case 1:
                    if (equipmentFragment == null)
                        equipmentFragment = new EquipmentFragment();

                    return equipmentFragment;
                case 2:
                    if (itemsFragment == null)
                        itemsFragment = new ItemsFragment();

                    return itemsFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "STATS";
                case 1:
                    return "EQUIPMENT";
                case 2:
                    return "ITEMS";
            }
            return null;
        }
    }
}
