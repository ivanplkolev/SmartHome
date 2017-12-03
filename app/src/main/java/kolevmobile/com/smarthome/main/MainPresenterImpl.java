package kolevmobile.com.smarthome.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

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

/**
 * Created by me on 30/11/2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private static List<Device> activeDevices;

    private DeviceDao deviceDao;


    Communicator communicator;

    Context context;
    Handler mainHandler;

    public MainPresenterImpl(Activity context, Handler mainHandler) {
        this.context = context;
        this.mainHandler = mainHandler;
    }


    @Override
    public void onCreate() {
        DaoSession daoSession = ((App) ((Activity) context).getApplication()).getDaoSession();
        deviceDao = daoSession.getDeviceDao();
        communicator = new CommunicatorImpl();
        activeDevices = new ArrayList<>();
    }

    @Override
    public void onResume() {
        refreshDevices();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void addDevice() {
        context.startActivity(new Intent(context, AddEditDeviceActivity.class));

    }

    @Override
    public void editDevice(int position) {
        Intent intent = new Intent(context, AddEditDeviceActivity.class);
        intent.putExtra(AddEditDeviceActivity.EDIT_DEVICE_ID_EXTRA, activeDevices.get(position).getId());
        context.startActivity(intent);
    }

    @Override
    public void updateDevice() {

    }

    @Override
    public void refreshDevices() {
        new Thread() {
            public void run() {
                activeDevices.clear();
                activeDevices.addAll(deviceDao.loadAll());
//                mainDisplayAdapter.notifyDataSetChanged();
                Message message = new Message();
                message.what = MainActivity.DO_UPDATE_ALL_VIEWS;
                mainHandler.sendMessage(message);
            }
        }.start();
    }

    @Override
    public void refreshDevice(int pos) {
        Device device = activeDevices.get(pos);
        device.setRefreshing(true);
        Message message = new Message();
        message.obj = device;
        message.what = MainActivity.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
        communicator.getDeviceStatus(device, (MainActivity) context);
    }

    @Override
    public void removeDevice(int position) {
        Device removedDevice = activeDevices.remove(position);
//        mainDisplayAdapter.notifyItemRemoved(position);
        Message message = new Message();
        message.obj = position;
        message.what = MainActivity.DO_REMOVE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
        deviceDao.delete(removedDevice);
    }

    @Override
    public void switchDeviceRelay(int position, int subPosition, boolean isChecked) {
        Device updatingDevice = activeDevices.get(position);
        updatingDevice.setRefreshing(true);
//        mainDisplayAdapter.notifyItemChanged(updatingDevice);
        Message message = new Message();
        message.obj = updatingDevice;
        message.what = MainActivity.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
        RelayModel updatingRelay = updatingDevice.getRelayModelList().get(subPosition);
        int newStatus = isChecked ? 1 : 0;
        updatingRelay.getActualStatus().setValue(newStatus);
        communicator.switchRelay(activeDevices.get(position), (MainActivity) context, updatingRelay);
    }

    @Override
    public void showDeviceDetails(int position) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(DetailsActivity.DEVICE_FOR_DETAILS, activeDevices.get(position).getId());
        context.startActivity(intent);
    }

//    @Override
//    public Device getDeviceAt(int pos) {
//        return activeDevices.get(pos);
//    }


}
