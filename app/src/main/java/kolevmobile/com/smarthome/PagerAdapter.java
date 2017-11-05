package kolevmobile.com.smarthome;
//
///**
// * Created by me on 04/11/2017.
// */
//
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//
//public class PagerAdapter extends FragmentStatePagerAdapter {
//    int mNumOfTabs;
//
//    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
//        super(fm);
//        this.mNumOfTabs = NumOfTabs;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//
//        switch (position) {
//            case 0:
//                TabFragment1 tab1 = new TabFragment1();
//                return tab1;
//            case 1:
//                TabFragment2 tab2 = new TabFragment2();
//                return tab2;
//            case 2:
//                TabFragment3 tab3 = new TabFragment3();
//                return tab3;
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return mNumOfTabs;
//    }
//}


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import kolevmobile.com.smarthome.TabFragment1;
import kolevmobile.com.smarthome.TabFragment2;
import kolevmobile.com.smarthome.TabFragment3;

public class PagerAdapter extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private int count;


    TabFragment1 fragment1;
    TabFragment2 fragment2;
    TabFragment3 fragment3;


    public PagerAdapter(FragmentManager fm, int count, TabFragment1 fragment1, TabFragment2 fragment2, TabFragment3 tabFragment3) {
        super(fm);
        this.fragment1 = fragment1;
        this.fragment2 = fragment2;
        this.fragment3 = fragment3;
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return fragment1;
            case 1:
                return fragment2;
            case 2:
                return fragment3;
            default:
                return null;
        }
    }

}