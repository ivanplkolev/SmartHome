package kolevmobile.com.smarthome.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.about.AboutActivity;
import kolevmobile.com.smarthome.add_edit_device.AddEditDeviceActivity;
import kolevmobile.com.smarthome.connection.Communicator;
import kolevmobile.com.smarthome.connection.CommunicatorImpl;
import kolevmobile.com.smarthome.details.DetailsActivity;
import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.RelayModel;


public class MainActivity extends AppCompatActivity {

    private MainDisplayAdapter mainDisplayAdapter;
    private NavigationView mainNavigationView;
    private DrawerLayout mainDrawer;
    private Toolbar mainToolbar;

    private static List<Device> activeDevices;

    private DeviceDao deviceDao;

    public final static int DO_UPDATE_ALL_VIEWS = 0;
    public final static int DO_UPDATE_DEVICE_VIEW = 1;
    private Handler mainHandler;

    Communicator communicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainToolbar = findViewById(R.id.main_toolbar);
        mainToolbar.setTitle("Main activity");

        mainDrawer = findViewById(R.id.main_drawer);
        mainNavigationView = findViewById(R.id.main_navigation_view);

        RecyclerView mainRecyclerView = findViewById(R.id.main_recycler_view);
        RecyclerView.LayoutManager mainLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mainLayoutManager);
        mainRecyclerView.setItemAnimator(new DefaultItemAnimator());

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        deviceDao = daoSession.getDeviceDao();

        communicator = new CommunicatorImpl();

        activeDevices = new ArrayList<>();
        mainDisplayAdapter = new MainDisplayAdapter(activeDevices, this);
        mainDisplayAdapter.setOnItemViewClickListener((view, position, subPosition) -> {
            switch (view.getId()) {
                case R.id.refreshButton:
                    refreshDevice(activeDevices.get(position));
                    break;
                case R.id.detailsButton:
                    showDeviceDetails(position);
                    break;
                case R.id.editButton:
                    editDevice(position);
                    break;
                case R.id.deleteButton:
                    removeDevice(position);
                    break;
                case R.id.relay_toggler:
                    switchDeviceRelay(position, subPosition, ((SwitchCompat) view).isChecked());
                    break;
            }
        });
        mainHandler = new Handler() {
            public void handleMessage(Message msg) {
                final int what = msg.what;
                switch (what) {
                    case DO_UPDATE_ALL_VIEWS:
                        mainDisplayAdapter.notifyDataSetChanged();
                        break;
                    case DO_UPDATE_DEVICE_VIEW:
                        mainDisplayAdapter.notifyItemChanged(msg.obj);
                        break;
                }
            }
        };
        mainRecyclerView.setAdapter(mainDisplayAdapter);
        setUpNavigationView();
    }


    @Override
    public void onBackPressed() {
        if (mainDrawer.isDrawerOpen(GravityCompat.START)) {
            mainDrawer.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    private void setUpNavigationView() {
        mainNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add_device:
                        addDevice();
                        mainDrawer.closeDrawers();
                        break;
                    case R.id.about_page:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        mainDrawer.closeDrawers();
                        break;
                }
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainDrawer, mainToolbar, R.string.open_drawer, R.string.close_drawer);
        mainDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshDevices();
    }

    private void refreshDevices() {
        new Thread() {
            public void run() {
                activeDevices.clear();
                activeDevices.addAll(deviceDao.loadAll());
                mainDisplayAdapter.notifyDataSetChanged();
            }
        }.start();
    }

    private void addDevice() {
        startActivity(new Intent(MainActivity.this, AddEditDeviceActivity.class));
    }

    private void editDevice(final int position) {
        Intent intent = new Intent(getBaseContext(), AddEditDeviceActivity.class);
        intent.putExtra(AddEditDeviceActivity.EDIT_DEVICE_ID_EXTRA, activeDevices.get(position).getId());
        startActivity(intent);
    }

    private void removeDevice(int position) {
        Device removedDevice = activeDevices.remove(position);
        mainDisplayAdapter.notifyItemRemoved(position);
        deviceDao.delete(removedDevice);
    }

    private void refreshDevice(Device device) {
        device.setRefreshing(true);
        mainDisplayAdapter.notifyItemChanged(device);
        communicator.getDeviceStatus(device, MainActivity.this);
    }

    private void showDeviceDetails(int position) {
        Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.DEVICE_FOR_DETAILS, activeDevices.get(position).getId());
        startActivity(intent);
    }


    private void switchDeviceRelay(int position, int subPosition, boolean isChecked) {
        Device updatingDevice = activeDevices.get(position);
        updatingDevice.setRefreshing(true);
        mainDisplayAdapter.notifyItemChanged(updatingDevice);
        RelayModel updatingRelay = updatingDevice.getRelayModelList().get(subPosition);
        int newStatus = isChecked ? 1 : 0;
        updatingRelay.getActualStatus().setValue(newStatus);
        communicator.switchRelay(activeDevices.get(position), this, updatingRelay);
    }

    public Handler getMyHandler() {
        return mainHandler;
    }
}
