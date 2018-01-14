package kolevmobile.com.smarthome.add_edit_device;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DeviceFragmentPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private int count;

    public DeviceFragmentPagerAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm);
        this.fragments = fragments;
        this.count = fragments.length;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }
}