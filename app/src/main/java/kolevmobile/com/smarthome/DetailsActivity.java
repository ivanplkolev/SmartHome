//package kolevmobile.com.smarthome;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//
////import kolevmobile.com.smarthome.data_base.DataBaseSisngleton;
//import kolevmobile.com.smarthome.green_dao.entities.DHTDevice;
//import kolevmobile.com.smarthome.green_dao.entities.DHTDeviceDao;
//import kolevmobile.com.smarthome.green_dao.entities.DHTSensorData;
//import kolevmobile.com.smarthome.green_dao.entities.DHTSensorDataDao;
//import kolevmobile.com.smarthome.green_dao.entities.DaoSession;
//
///**
// * Created by x on 23.10.2017 г..
// */
//
//public class DetailsActivity extends AppCompatActivity {
//
//
//    public static String DEVICE_FOR_DETAILS = "DEVICE_FOR_DETAILS";
//
//    private DHTDevice device;
//
//    DHTDeviceDao dhtDeviceDao;
//    DHTSensorDataDao dhtSensorDataDao;
//
//
//    TextView detailsDeviceNameTextView;
//    TextView detailsDeviceDescTextView;
//    TextView detailsTextView;
//    Handler handler;
//
//    ArrayList<DHTSensorData> sensorDataArrayList = new ArrayList<>();
//
//    final static int DO_UPDATE_VIEWS = 0;
//
//    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM");
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_details);
//        Long deviceId = getIntent().getLongExtra(DEVICE_FOR_DETAILS, 0L);
//        DaoSession daoSession = ((App) getApplication()).getDaoSession();
//        dhtDeviceDao = daoSession.getDHTDeviceDao();
//        dhtSensorDataDao = daoSession.getDHTSensorDataDao();
//
//        device = dhtDeviceDao.load(deviceId);
//
//        detailsDeviceNameTextView = findViewById(R.id.detailsDeviceNameTextView);
//        detailsDeviceDescTextView = findViewById(R.id.detailsDeviceDescTextView);
//        detailsTextView = findViewById(R.id.detailsTextView);
//
//        detailsDeviceNameTextView.setText(device.getName());
//        detailsDeviceDescTextView.setText(device.getDescription());
//        handler = new Handler() {
//            public void handleMessage(Message msg) {
//                final int what = msg.what;
//                switch (what) {
//                    case DO_UPDATE_VIEWS:
//                        StringBuilder sb = new StringBuilder();
//                        for (DHTSensorData sd : sensorDataArrayList) {
//                            sb.append(sd.getTemperatureString())
//                                    .append(" ")
//                                    .append(sd.getHumidityString())
//                                    .append(" ")
//                                    .append(sd.getMeasuredAtString())
//                                    .append("\n");
//                        }
//                        break;
//                }
//            }
//        };
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                sensorDataArrayList.clear();
////                sensorDataArrayList.addAll(DataBaseSisngleton.getDataBase(DetailsActivity.this.getApplicationContext()).sensorDataDao().getAllForDevice(device.getUid()));
//                sensorDataArrayList.addAll(dhtSensorDataDao.loadAll());
//                handler.sendEmptyMessage(DO_UPDATE_VIEWS);
//            }
//        }).start();
//
//
//    }
//
//
//}
