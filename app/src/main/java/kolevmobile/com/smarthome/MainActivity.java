package kolevmobile.com.smarthome;

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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;

//import kolevmobile.com.smarthome.green_dao.entities.DHTDevice;
//import kolevmobile.com.smarthome.old_new_connector.Looper;


public class MainActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static List<Device> devices = new ArrayList<>();

    DeviceDao deviceDao;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    //    DHTSensorDataDao dhtSensorDataDao;

//    private Looper looper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Main activity");
//        setSupportActionBar(toolbar_layout);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navHeader = navigationView.getHeaderView(0);

        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        deviceDao = daoSession.getDeviceDao();


        adapter = new DisplayAdapter(devices, this);
        ((DisplayAdapter) adapter).setOnItemViewClickListener(new ItemButtonObserver() {
            @Override
            public void onClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.refreshButton:
                        refreshDevice(position);
                        break;
                    case R.id.detailsButton:
//                        Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
//                        intent.putExtra(DetailsActivity.DEVICE_FOR_DETAILS, devices.get(position).getId());
//                        startActivity(intent);
                        break;
                    case R.id.editButton:
                        editDevice(position);
                        break;
                    case R.id.deleteButton:
                        Device removedDevice = devices.remove(position);
                        adapter.notifyItemRemoved(position);
                        deviceDao.delete(removedDevice);
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
//        looper = new Looper();
//        looper.start();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.password_toggle_content_description, R.string.abc_action_bar_home_description);
        setUpNavigationView();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        super.onBackPressed();
    }



    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
//                    case R.id.nav_home:
//                        break;
                    case R.id.nav_fonts:
                        startActivity(new Intent(MainActivity.this, AddEditDeviceActivity.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_backgrounds:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_settings:
//                        startActivity(new Intent(MainActivity.this, ImePreferences.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_about_us:
//                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_feedback:
//                        startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                        drawer.closeDrawers();
                        break;
                }
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        devices.clear();
        devices.addAll(deviceDao.loadAll());
        adapter.notifyDataSetChanged();
    }

    void refreshDevice(int pos) {
//        DHTDevice refreshingDevice = devices.get(pos);
//        refreshingDevice.setRefreshing(true);
//        adapter.notifyItemChanged(pos);
//        looper.addTask(new ReadDHTSensorTask(refreshingDevice, myHandler ));
//        ArduinoWifiCommunicator communicator = new ArduinoWifiCommunicator();
//        new Thread(() -> {
////            Exception exception = null;
//            try {
//                SensorData sensorData = communicator.getData(refreshingDevice);
//                if (sensorData != null) {
//                    refreshingDevice.setActualSensorData(sensorData);
//                }
//                refreshingDevice.setRefreshing(false);
//                adapter.notifyItemRemoved(pos);
//
//                if (sensorData != null) {
////                    int sensordataUid = (int) DataBaseSisngleton.getDataBase(MainActivity.this).sensorDataDao().insert(sensorData);
////                    sensorData.setUid(sensordataUid);
//                    dhtSensorDataDao.insert(sensorData);
//                    refreshingDevice.setActualSensorDataUid(sensorData.getUid());
////                    DataBaseSisngleton.editDevice(refreshingDevice, MainActivity.this);
//                    dhtDeviceDao.update(refreshingDevice);
//                }
//            } catch (WrongSensorDataException e) {
////                exception = e;
//                refreshingDevice.setErrorMessage("Problem reading sensor data");
//                refreshingDevice.setRefreshing(false);
//                adapter.notifyItemChanged(pos);
//            } catch (NoDeviceException e) {
////                exception = e;
//                refreshingDevice.setErrorMessage("Problem connecting with the device");
//                refreshingDevice.setRefreshing(false);
//                adapter.notifyItemChanged(pos);
//            }
//        }).start();
    }

    private final static int DO_UPDATE_VIEWS = 0;
    private final static int DO_UPDATE_LIST = 1;
    private final Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            final int what = msg.what;
            switch (what) {
                case DO_UPDATE_VIEWS:
//                    refreshViews();
                    break;
                case DO_UPDATE_LIST:
//                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//        if (item.getItemId() == R.id.add_item) {
////            addDevice();
//        }
//        return true;
//    }

    public void editDevice(final int position) {

        Intent intent = new Intent(getBaseContext(), AddEditDeviceActivity.class);
        intent.putExtra(AddEditDeviceActivity.EDIT_DEVICE_ID_EXTRA, devices.get(position).getId());
        startActivity(intent);

//        final DHTDevice device = devices.get(position);
//        LayoutInflater li = LayoutInflater.from(this);
//        View promptsView = li.inflate(R.layout.activity_add_edit, null);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//
//        // set prompts.xml to alertdialog builder
//        alertDialogBuilder.setView(promptsView);
//
//        final EditText deviceName = promptsView.findViewById(R.id.newDeviceName);
//        final EditText deviceDescription = promptsView.findViewById(R.id.newDeviceDescriprtion);
//        final EditText deviceUrl = promptsView.findViewById(R.id.newDeviceUrl);
//
//        deviceName.setText(device.getName());
//        deviceDescription.setText(device.getDescription());
//        deviceUrl.setText(device.getUrl());
//
//        // set dialog message
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                device.setName(deviceName.getText().toString());
//                                device.setDescription(deviceDescription.getText().toString());
//                                device.setUrl(deviceUrl.getText().toString());
//
////                                SensorData sd = new SensorData(33, 33, new Date());
//
//                                adapter.notifyItemChanged(position);
////                                new EditDeviceInDb(device).execute();
//                                new Thread(new Runnable() {
//                                    public void run() {
////                                        DataBaseSisngleton.editDevice(device, MainActivity.this.getApplicationContext());
//                                        dhtDeviceDao.update(device);
//                                    }
//                                }).start();
//
//
//                            }
//                        })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        // create alert dialog
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // show it
//        alertDialog.show();
    }


    public void addDevice(View v) {
        Intent intent = new Intent(getBaseContext(), AddEditDeviceActivity.class);
        startActivity(intent);
    }


}
