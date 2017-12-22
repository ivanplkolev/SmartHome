package kolevmobile.com.smarthome.connection;

import com.google.gson.Gson;

import java.io.IOException;

import kolevmobile.com.smarthome.main.MainPresenter;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.Error;
import kolevmobile.com.smarthome.model.RelayModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by me on 10/11/2017.
 */

public class CommunicatorImpl implements Communicator {

    private static OkHttpClient client = new OkHttpClient();

    private MainPresenter presenter;

    public void getDeviceStatus(Device device) {
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



                presenter.updateDevice(device,Error.COMUNICATING_ERROR );

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handleDeviceResponce(response, device);
            }
        });
    }


    public void switchRelay(Device device, RelayModel relayModel) {


        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(device.getUrlAddress())
                .addQueryParameter("command", "switchrelay")
                .addQueryParameter("key", relayModel.getKey())
                .addQueryParameter("status", String.valueOf(relayModel.getActualStatus().getValue()))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                presenter.updateDevice(device,Error.COMUNICATING_ERROR );

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handleDeviceResponce(response, device);
            }

        });

    }

    private void handleDeviceResponce(Response response, Device device) throws IOException {
        kolevmobile.com.smarthome.connection.model.Device connectorDevice = null;
        if (!response.isSuccessful()) {
            presenter.updateDevice(device, Error.COMUNICATING_ERROR);
        } else {
            try {
                String res = response.body().string();
                connectorDevice = new Gson().fromJson(res, kolevmobile.com.smarthome.connection.model.Device.class);
            } catch (Exception e) {
                presenter.updateDevice(device, Error.COMUNICATING_ERROR);
            }
        }
            if(connectorDevice != null) {
                presenter.updateDevice(device, connectorDevice);
            }
//            JsonElement jelement = new JsonParser().parse(res);
//            JsonObject jobject = jelement.getAsJsonObject();
//            String error = jobject.get("Error").getAsString();
//            if (false&& error != null && error.length() != 0) {
//                device.setError(Error.SCHEMA_ERROR);
//            } else {
//                for (SensorModel sensorModel : device.getSensorModelList()) {
//                    float result = jobject.get(sensorModel.getKey()).getAsFloat();
//                    SensorValue sensorValue = new SensorValue();
//                    sensorValue.setMeasuredAt(new Date());
//                    sensorValue.setValue(result);
//                    sensorValue.setSensorModelId(sensorModel.getId());
//                    sensorValueDao.insert(sensorValue);
//
//                    device.setActualizationDate(new Date());
//                    sensorModel.setActualValue(sensorValue);
//                    sensorModel.setActualValueId(sensorValue.getId());
//                    sensorModel.getSensroValueList().add(sensorValue);
//
//                    sensorModelDao.update(sensorModel);
//
//                }
//
//                for (RelayModel relayModel :  device.getRelayModelList()) {
//                    int result = jobject.get(relayModel.getKey()).getAsInt();
//                    RelayStatus relayStatus = new RelayStatus();
//                    relayStatus.setRelayModelId(relayModel.getId());
//                    relayStatus.setValue(result);
//                    relayStatus.setSentAt(new Date());
//                    relayStatusDao.insert(relayStatus);
//
//
//                    relayModel.setActualStatus(relayStatus);
//                    relayModel.setActualStatusId(relayStatus.getId());
//                    relayModelDao.update(relayModel);
//                    device.setActualizationDate(new Date());
//                }
//            }
//        }
//        device.setRefreshing(false);
//        Message message = new Message();
//        message.obj=device;
//        message.what = MainActivity.DO_UPDATE_DEVICE_VIEW;
//        activity.getMainHandler().sendMessage(message);
//        deviceDao.update(device);
        }


    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }
}
