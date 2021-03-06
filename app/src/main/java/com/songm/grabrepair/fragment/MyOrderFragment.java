package com.songm.grabrepair.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songm.grabrepair.R;
import com.songm.grabrepair.fragment.myorder.RepairCompleteFragment;
import com.songm.grabrepair.fragment.myorder.RepairFailFragment;
import com.songm.grabrepair.fragment.myorder.RepairFragment;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by SongM on 2017/9/19.
 * 我的报修单
 */

public class MyOrderFragment extends Fragment implements MaterialTabListener {

    private MaterialTabHost tabHost;
    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter = null;
    private String[] columns = {"正在维修", "维修完成", "维修失败"};
    private Fragment[] fragment = {new RepairFragment(), new RepairCompleteFragment(), new RepairFailFragment()};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_myorder, null);

        // MaterialTabHost
        tabHost = (MaterialTabHost) view.findViewById(R.id.tabHost);
        pager = (ViewPager) view.findViewById(R.id.pager);

        // init view pager
        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            //.setIcon(getIcon(i))
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }

        return view;
    }

    // 将intem设为首条
    public void revertPager() {
        if(pager != null) {
            pager.setCurrentItem(0);
        }
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {
    }

    @Override
    public void onTabUnselected(MaterialTab tab) {
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int num) {
            return fragment[num];
        }

        @Override
        public int getCount() {
            return fragment.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return columns[position];
        }

    }

}
