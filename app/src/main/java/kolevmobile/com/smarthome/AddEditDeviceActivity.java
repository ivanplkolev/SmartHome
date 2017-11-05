package kolevmobile.com.smarthome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;

/**
 * Created by me on 29/10/2017.
 */


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class AddEditDeviceActivity extends AppCompatActivity {

    Device device;
    DeviceDao deviceDao;
    static final String EDIT_DEVICE_ID_EXTRA = "EDIT_DEVICE_ID_EXTRA";

    ViewPager viewPager;
    PagerAdapter adapter;


    TabFragment1 fragment1;
    TabFragment2 fragment2;
    TabFragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        deviceDao = daoSession.getDeviceDao();
        Long deviceId = getIntent().getLongExtra(EDIT_DEVICE_ID_EXTRA, 0L);
        if (deviceId != 0L) {
            device = deviceDao.load(deviceId);
        }

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        if (device != null) {
            tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
            tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        fragment1 = new TabFragment1();
        fragment2 = new TabFragment2();
        fragment3 = new TabFragment3();

        viewPager = findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), fragment1, fragment2, fragment3);
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
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    public void saveDevice(View v) {

        if (device == null) {
            device = new Device();
            fragment1.copyfromFields();
            deviceDao.insert(device);
        } else {
            fragment1.copyfromFields();
            deviceDao.update(device);
        }

//        if (adapter.getCount() == 1) {
//            TabLayout tabLayout = findViewById(R.id.tab_layout);
//            tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
//            tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
//            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//
//            viewPager = (ViewPager) findViewById(R.id.pager);
//            adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//            viewPager.setAdapter(adapter);
//        }
//        viewPager.setCurrentItem(1, true);
//        onBackPressed();
    }

    public void cancelSaving(View v) {
        onBackPressed();
    }

    Device getDevice() {
        return device;
    }

}