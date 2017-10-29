package kolevmobile.com.smarthome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import kolevmobile.com.smarthome.data_base.DataBaseSisngleton;
import kolevmobile.com.smarthome.data_base.Device;
import kolevmobile.com.smarthome.data_base.SensorData;
import kolevmobile.com.smarthome.onnector.ArduinoWifiCommunicator;
import kolevmobile.com.smarthome.onnector.NoDeviceException;
import kolevmobile.com.smarthome.onnector.WrongSensorDataException;


public class MainActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static List<Device> devices = new ArrayList<>();

    private FloatingActionButton addNewDeviceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewDeviceButton = findViewById(R.id.addNewDeviceButton);

        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new Thread(() -> {
            devices.clear();
            devices.addAll(DataBaseSisngleton.getAllDevices(MainActivity.this.getApplicationContext()));
            adapter.notifyDataSetChanged();
        }).start();

        adapter = new CustomAdapter(devices, this);
        ((CustomAdapter) adapter).setOnItemViewClickListener(new ItemButtonObserver() {
            @Override
            public void onClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.refreshButton:
                        refreshDevice(position);
                        break;
                    case R.id.detailsButton:
                        Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                        intent.putExtra(DetailsActivity.DEVICE_FOR_DETAILS, devices.get(position));
                        startActivity(intent);
                        break;
                    case R.id.editButton:
                        editDevice(position);
                        break;
                    case R.id.deleteButton:
                        Device removedDevice = devices.remove(position);
                        adapter.notifyItemRemoved(position);
                        new Thread(new Runnable() {
                            public void run() {
                                DataBaseSisngleton.removeDevice(removedDevice, MainActivity.this.getApplicationContext());
                            }
                        }).start();
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    void refreshDevice(int pos) {
        Device refreshingDevice = devices.get(pos);
        refreshingDevice.setRefreshing(true);
        adapter.notifyItemChanged(pos);
        ArduinoWifiCommunicator communicator = new ArduinoWifiCommunicator();
        new Thread(() -> {
//            Exception exception = null;
            try {
                SensorData sensorData = communicator.getData(refreshingDevice);
                if (sensorData != null) {
                    refreshingDevice.setActualSensorData(sensorData);
                }
                refreshingDevice.setRefreshing(false);
                adapter.notifyItemRemoved(pos);

                if (sensorData != null) {
                    int sensordataUid = (int) DataBaseSisngleton.getDataBase(MainActivity.this).sensorDataDao().insert(sensorData);
                    sensorData.setUid(sensordataUid);
                    refreshingDevice.setActualSensorDataUid(sensorData.getUid());
                    DataBaseSisngleton.editDevice(refreshingDevice, MainActivity.this);
                }
            } catch (WrongSensorDataException e) {
//                exception = e;
                refreshingDevice.setErrorMessage("Problem reading sensor data");
                refreshingDevice.setRefreshing(false);
                adapter.notifyItemChanged(pos);
            } catch (NoDeviceException e) {
//                exception = e;
                refreshingDevice.setErrorMessage("Problem connecting with the device");
                refreshingDevice.setRefreshing(false);
                adapter.notifyItemChanged(pos);
            }
        }).start();
    }

//    private final static int DO_UPDATE_VIEWS = 0;
//    private final static int DO_UPDATE_LIST = 1;
//    private final Handler myHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            final int what = msg.what;
//            switch (what) {
//                case DO_UPDATE_VIEWS:
////                    refreshViews();
//                    break;
//                case DO_UPDATE_LIST:
////                    adapter.notifyDataSetChanged();
//                    break;
//            }
//        }
//    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.add_item) {
//            addDevice();
        }
        return true;
    }

    public void editDevice(final int position) {

        final Device device = devices.get(position);
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText deviceName = promptsView.findViewById(R.id.newDeviceName);
        final EditText deviceDescription = promptsView.findViewById(R.id.newDeviceDescriprtion);
        final EditText deviceUrl = promptsView.findViewById(R.id.newDeviceUrl);

        deviceName.setText(device.getName());
        deviceDescription.setText(device.getDescription());
        deviceUrl.setText(device.getUrlAdress());

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                device.setName(deviceName.getText().toString());
                                device.setDescription(deviceDescription.getText().toString());
                                device.setUrlAdress(deviceUrl.getText().toString());

//                                SensorData sd = new SensorData(33, 33, new Date());

                                adapter.notifyItemChanged(position);
//                                new EditDeviceInDb(device).execute();
                                new Thread(new Runnable() {
                                    public void run() {
                                        DataBaseSisngleton.editDevice(device, MainActivity.this.getApplicationContext());
                                    }
                                }).start();


                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void addDevice(View v) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText deviceName = promptsView.findViewById(R.id.newDeviceName);
        final EditText deviceDescription = promptsView.findViewById(R.id.newDeviceDescriprtion);
        final EditText deviceUrl = promptsView.findViewById(R.id.newDeviceUrl);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Device newDevice = new Device(deviceUrl.getText().toString(), deviceName.getText().toString(), deviceDescription.getText().toString());
                                devices.add(newDevice);
                                adapter.notifyItemRangeChanged(devices.size() - 1, 1);
//                                new AddDeviceInDb(newDevice).execute();

                                new Thread(new Runnable() {
                                    public void run() {
                                        DataBaseSisngleton.addDevice(newDevice, MainActivity.this.getApplicationContext());
                                    }
                                }).start();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }


}
