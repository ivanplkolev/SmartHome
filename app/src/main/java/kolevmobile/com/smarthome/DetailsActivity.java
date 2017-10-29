package kolevmobile.com.smarthome;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kolevmobile.com.smarthome.data_base.DataBaseSisngleton;
import kolevmobile.com.smarthome.data_base.Device;
import kolevmobile.com.smarthome.data_base.SensorData;

/**
 * Created by x on 23.10.2017 г..
 */

public class DetailsActivity extends AppCompatActivity {


    public static String DEVICE_FOR_DETAILS = "DEVICE_FOR_DETAILS";

    private Device device;

    TextView detailsDeviceNameTextView;
    TextView detailsDeviceDescTextView;
    TextView detailsTextView;
    Handler handler;

    ArrayList<SensorData> sensorDataArrayList = new ArrayList<>();

    final static int DO_UPDATE_VIEWS = 0;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        device = (Device) getIntent().getSerializableExtra(DEVICE_FOR_DETAILS);

        detailsDeviceNameTextView = findViewById(R.id.detailsDeviceNameTextView);
        detailsDeviceDescTextView = findViewById(R.id.detailsDeviceDescTextView);
        detailsTextView = findViewById(R.id.detailsTextView);

        detailsDeviceNameTextView.setText(device.getName());
        detailsDeviceDescTextView.setText(device.getDescription());
        handler = new Handler() {
            public void handleMessage(Message msg) {
                final int what = msg.what;
                switch (what) {
                    case DO_UPDATE_VIEWS:
                        StringBuilder sb = new StringBuilder();
                        for (SensorData sd : sensorDataArrayList) {
                            sb.append(sd.getTemperatureString())
                                    .append(" ")
                                    .append(sd.getHumidityString())
                                    .append(" ")
                                    .append(sd.getDateString())
                                    .append("\n");
                        }
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                sensorDataArrayList.clear();
                sensorDataArrayList.addAll(DataBaseSisngleton.getDataBase(DetailsActivity.this.getApplicationContext()).sensorDataDao().getAllForDevice(device.getUid()));
                handler.sendEmptyMessage(DO_UPDATE_VIEWS);
            }
        }).start();


    }


}
