package kolevmobile.com.smarthome.details;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.SensorModelDao;
import kolevmobile.com.smarthome.model.SensorValue;


/**
 * Created by x on 23.10.2017 г..
 */

public class DetailsActivity extends AppCompatActivity {

    public static String DEVICE_FOR_DETAILS = "DEVICE_FOR_DETAILS";

    DeviceDao deviceDao;

    TextView detailsDeviceNameTextView;
    TextView detailsDeviceDescTextView;
    TextView detailsTextView;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Long deviceId = getIntent().getLongExtra(DEVICE_FOR_DETAILS, 0L);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        deviceDao = daoSession.getDeviceDao();

        Device device = deviceDao.load(deviceId);

        detailsDeviceNameTextView = findViewById(R.id.detailsDeviceNameTextView);
        detailsDeviceDescTextView = findViewById(R.id.detailsDeviceDescTextView);
        detailsTextView = findViewById(R.id.detailsTextView);

        detailsDeviceNameTextView.setText(device.getName());
        detailsDeviceDescTextView.setText(device.getDescription());

        StringBuilder sb = new StringBuilder();
        for (SensorModel sensorModel : device.getSensorModelList()) {
            for (SensorValue sensorValue : sensorModel.getSensroValueList()) {
                sb.append(sensorModel.getName())
                        .append(" ")
                        .append(sensorValue.getValue())
                        .append(" ")
                        .append(simpleDateFormat.format(sensorValue.getMeasuredAt()))
                        .append("\n");
            }
        }
        detailsTextView.setText(sb);
    }

}
