package com.zack.shop.mvp.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/6 下午4:19
 * @Package com.zack.shop.mvp.ui.adapter
 **/
public class ItemTitlePagerAdapter extends FragmentPagerAdapter {
    private String[] titleArray;
    private List<Fragment> fragmentList;

    public ItemTitlePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titleArray) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleArray = titleArray;
    }

    public void setFramentData(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void setTitleData(String[] titleArray) {
        this.titleArray = titleArray;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }

    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}

