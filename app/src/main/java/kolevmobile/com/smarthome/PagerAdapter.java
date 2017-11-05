package kolevmobile.com.smarthome;


import android.content.Context;
import android.support.v4.app.*;


import	android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    private Context context;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new TabFragment1();
        }
        else if (position == 1)
        {
            fragment = new TabFragment2();
        }
        else if (position == 2)
        {
            fragment = new TabFragment3();
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}