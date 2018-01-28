package kolevmobile.com.smarthome.connection;

import java.io.IOException;

import kolevmobile.com.smarthome.Presenter;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.Error;
import kolevmobile.com.smarthome.model.RelayModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class CommunicatorImpl implements Communicator {

    private static OkHttpClient client = new OkHttpClient();

//    private Presenter presenter;

//    public CommunicatorImpl(Presenter presenter) {
//        this.presenter = presenter;
//    }

    public void getDeviceStatus(Presenter presenter, Device device) {
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
                handleCommunicationFailure(presenter, device);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handleDeviceResponce(response,presenter, device);
            }
        });
    }


    public void switchRelay(Presenter presenter, Device device, RelayModel relayModel) {
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
                handleCommunicationFailure(presenter, device);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handleDeviceResponce(response,presenter, device);
            }
        });
    }

    private void handleDeviceResponce(Response response,Presenter presenter,  Device device) throws IOException {
        String responseString = null;
        try {
            if (!response.isSuccessful()) {
                presenter.updateDevice(device, Error.COMUNICATING_ERROR);
                return;
            }
            responseString = response.body().string();
        } catch (Exception e) {
            presenter.updateDevice(device, Error.COMUNICATING_ERROR);
        }
        presenter.updateDevice(device, responseString);
    }

    private void handleCommunicationFailure(Presenter presenter, Device device) {
        presenter.updateDevice(device, Error.COMUNICATING_ERROR);
    }
}
