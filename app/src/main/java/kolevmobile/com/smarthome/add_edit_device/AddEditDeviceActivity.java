package kolevmobile.com.smarthome.add_edit_device;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;

/**
 * Created by me on 29/10/2017.
 */

public class AddEditDeviceActivity extends AppCompatActivity {



    public static final String EDIT_DEVICE_ID_EXTRA = "EDIT_DEVICE_ID_EXTRA";

    ViewPager viewPager;
    DeviceFragmentPagerAdapter adapter;

    private DeviceGeneralFragment deviceGeneralFragment;
    private DeviceSenosrsFragment deviceSenosrsFragment;
    private DeviceRelaysFragment deviceRelaysFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("Add edit device");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        Long deviceId = getIntent().getLongExtra(EDIT_DEVICE_ID_EXTRA, 0L);
        if (deviceId != 0L) {
            device = deviceDao.load(deviceId);
        }

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Device"));
        viewPager = findViewById(R.id.pager);
        deviceGeneralFragment = new DeviceGeneralFragment();
        deviceSenosrsFragment = new DeviceSenosrsFragment();
        deviceRelaysFragment = new DeviceRelaysFragment();
        if (device != null) {
            tabLayout.addTab(tabLayout.newTab().setText("Sensors"));
            tabLayout.addTab(tabLayout.newTab().setText("Relays"));
            adapter = new DeviceFragmentPagerAdapter(getSupportFragmentManager(), deviceGeneralFragment, deviceSenosrsFragment, deviceRelaysFragment);
        } else {
            adapter = new DeviceFragmentPagerAdapter(getSupportFragmentManager(), deviceGeneralFragment);
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (device != null) {
            deviceGeneralFragment.copyToFields(device);
        }
    }



    public void cancelSaving(View v) {
        onBackPressed();
    }

    Device getDevice() {
        return device;
    }

}