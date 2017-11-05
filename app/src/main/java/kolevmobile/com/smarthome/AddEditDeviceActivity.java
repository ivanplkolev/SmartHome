package kolevmobile.com.smarthome;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;

/**
 * Created by me on 29/10/2017.
 */

public class AddEditDeviceActivity extends AppCompatActivity {

    Device device;
    DeviceDao deviceDao;
    static final String EDIT_DEVICE_ID_EXTRA = "EDIT_DEVICE_ID_EXTRA";

    ViewPager viewPager;
    PagerAdapter adapter;


    TabFragment1 fragment1;
    TabFragment2 fragment2;
    TabFragment3 fragment3;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add edit device");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_delete_black_24dp));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




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

//        fragment1 = new TabFragment1();
//        fragment2 = new TabFragment2();
//        fragment3 = new TabFragment3();

        viewPager = findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), this);
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



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public void saveDevice(View v) {

        if (device == null) {
            device = new Device();
            ((TabFragment1) adapter.instantiateItem(viewPager, 0)).copyfromFields();
            deviceDao.insert(device);
        } else {
            ((TabFragment1) adapter.instantiateItem(viewPager, 0)).copyfromFields();
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