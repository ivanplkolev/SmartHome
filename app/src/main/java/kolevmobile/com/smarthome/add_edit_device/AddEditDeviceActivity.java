package kolevmobile.com.smarthome.add_edit_device;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.R;

public class AddEditDeviceActivity extends AppCompatActivity {

    @Inject
    AddEditPresenter presenter;


    public static final String EDIT_DEVICE_ID_EXTRA = "EDIT_DEVICE_ID_EXTRA";

    private ViewPager viewPager;
    private DeviceFragmentPagerAdapter pagerAdapter;

    private DeviceGeneralFragment deviceGeneralFragment;
    private DeviceSenosrsFragment deviceSenosrsFragment;
    private DeviceRelaysFragment deviceRelaysFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ((App) getApplication()).getPresenterComponent().inject(this);

        prepareToolbar();

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.device)));
        viewPager = findViewById(R.id.pager);
        deviceGeneralFragment = new DeviceGeneralFragment();
        deviceSenosrsFragment = new DeviceSenosrsFragment();
        deviceRelaysFragment = new DeviceRelaysFragment();

        Long deviceId = getIntent().getLongExtra(AddEditDeviceActivity.EDIT_DEVICE_ID_EXTRA, 0L);
        presenter.initDevice(deviceId);

        if (presenter.getDevice() != null) {
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.sensors)));
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.relays)));
            pagerAdapter = new DeviceFragmentPagerAdapter(getSupportFragmentManager(), deviceGeneralFragment, deviceSenosrsFragment, deviceRelaysFragment);
        } else {
            pagerAdapter = new DeviceFragmentPagerAdapter(getSupportFragmentManager(), deviceGeneralFragment);
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }

    private void prepareToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle(getResources().getString(R.string.Add_edit_device));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }


    public void saveDevice(View v) {
        presenter.saveDevice(deviceGeneralFragment);

        if (pagerAdapter.getCount() == 1) {
            TabLayout tabLayout = findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.sensors)));
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.relays)));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            viewPager = findViewById(R.id.pager);
            pagerAdapter = new DeviceFragmentPagerAdapter(getSupportFragmentManager(), deviceGeneralFragment, deviceSenosrsFragment, deviceRelaysFragment);
            viewPager.setAdapter(pagerAdapter);
        }

        viewPager.setCurrentItem(1, true);
    }

}