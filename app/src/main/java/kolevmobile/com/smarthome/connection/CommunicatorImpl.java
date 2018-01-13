package kolevmobile.com.smarthome.connection;

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

    public CommunicatorImpl(MainPresenter presenter){
        this.presenter = presenter;
    }

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


                presenter.updateDevice(device, Error.COMUNICATING_ERROR);

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

                presenter.updateDevice(device, Error.COMUNICATING_ERROR);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handleDeviceResponce(response, device);
            }

        });

    }

    private void handleDeviceResponce(Response response, Device device) throws IOException {
        try {
            if (!response.isSuccessful()) {
                presenter.updateDevice(device, Error.COMUNICATING_ERROR);
                return;
            }
            String res = response.body().string();
            presenter.updateDevice(device, res);
        } catch (Exception e) {
            presenter.updateDevice(device, Error.COMUNICATING_ERROR);
        }
    }

}
