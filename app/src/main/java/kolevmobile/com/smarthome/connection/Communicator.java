package kolevmobile.com.smarthome.connection;

import android.os.Message;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Date;

import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.main.MainActivity;
import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.Error;
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.RelayStatus;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.SensorModelDao;
import kolevmobile.com.smarthome.model.SensorValue;
import kolevmobile.com.smarthome.model.SensorValueDao;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by me on 10/11/2017.
 */

public class Communicator {

    private static OkHttpClient client = new OkHttpClient();
    private static DeviceDao deviceDao;
    private static SensorModelDao sensorModelDao;
    private static SensorValueDao sensorValueDao;


    public static void getDeviceStatus(Device device, MainActivity activity) {
        if (deviceDao == null) {
            DaoSession daoSession = ((App) activity.getApplication()).getDaoSession();
            deviceDao = daoSession.getDeviceDao();
            sensorModelDao = daoSession.getSensorModelDao();
            sensorValueDao = daoSession.getSensorValueDao();
        }

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(device.getUrlAddress())
                .addQueryParameter("command", "status")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                device.setError(Error.COMUNICATING_ERROR);
                Message message = new Message();
                message.obj = device;
                message.what = MainActivity.DO_UPDATE_DEVICE_VIEW;
                activity.getMyHandler().sendMessage(message);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handleDeviceResponce(response, device, activity);
            }
        });
    }


    public static void switchRelay(Device device, MainActivity activity, RelayModel relayModel) {


        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(device.getUrlAddress())
        .addQueryParameter("command", "switchrelay")
        .addQueryParameter("key", relayModel.getKey())
        .addQueryParameter("status", String.valueOf(relayModel.getActualStatus().getValue()))
                .build();


//        HttpUrl.Builder urlBuilder = HttpUrl.parse(device.getUrlAddress()).newBuilder();
//        urlBuilder.addQueryParameter("command", "switchrelay");
//        urlBuilder.addQueryParameter("key", relayModel.getKey());
//        urlBuilder.addQueryParameter("status", String.valueOf(relayModel.getActualStatus().getValue()));
//        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                device.setError(Error.COMUNICATING_ERROR);
                Message message = new Message();
                message.obj = device;
                message.what = MainActivity.DO_UPDATE_DEVICE_VIEW;
                activity.getMyHandler().sendMessage(message);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handleDeviceResponce(response, device, activity);
            }

        });

    }

    private static void handleDeviceResponce(Response response, Device device, MainActivity activity) throws IOException {

        if (!response.isSuccessful()) {
            device.setError(Error.COMUNICATING_ERROR);
            activity.getMyHandler().sendMessage(new Message());
//                    throw new IOException("Unexpected code " + response);
        } else {
            // do something wih the result

            String res = response.body().string();
            JsonElement jelement = new JsonParser().parse(res);
            JsonObject jobject = jelement.getAsJsonObject();
//                    jobject = jobject.getAsJsonObject("data");
//                    JsonArray jarray = jobject.getAsJsonArray("translations");
//                    jobject = jarray.get(0).getAsJsonObject();
            String error = jobject.get("Error").getAsString();
            if (false&& error != null && error.length() != 0) {
                device.setError(Error.SCHEMA_ERROR);
            } else {
                for (SensorModel sensorModel : device.getSensorModelList()) {
                    float result = jobject.get(sensorModel.getKey()).getAsFloat();
                    SensorValue sensorValue = new SensorValue();
                    sensorValue.setMeasuredAt(new Date());
                    sensorValue.setValue(result);
                    sensorValue.setSensorModelId(sensorModel.getId());
                    sensorValueDao.insert(sensorValue);

                    //persisit sensor val
                    device.setActualizationDate(new Date());
                    sensorModel.setActualValue(sensorValue);
                    sensorModel.setActualValueId(sensorValue.getId());
                    sensorModel.getSensroValueList().add(sensorValue);

                    // persisit sensor model
                    sensorModelDao.update(sensorModel);

                }

                for (RelayModel relayModel :  device.getRelayModelList()) {
//                    RelayModel relayModel = device.getRelayModelList().get(i);
                    int result = jobject.get(relayModel.getKey()).getAsInt();
                    RelayStatus relayStatus = new RelayStatus();
                    relayStatus.setValue(result);
                    relayStatus.setConfirmed(true);
                    relayStatus.setSentAt(new Date());

                    relayModel.setActualStatus(relayStatus);
                    device.setActualizationDate(new Date());
                }
            }
            Message message = new Message();
            message.obj=device;
            message.what = MainActivity.DO_UPDATE_DEVICE_VIEW;
            activity.getMyHandler().sendMessage(message);
        }
        deviceDao.update(device);
    }


}
