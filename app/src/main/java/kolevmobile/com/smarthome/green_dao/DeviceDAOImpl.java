//package kolevmobile.com.smarthome.green_dao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import kolevmobile.com.smarthome.App;
//import kolevmobile.com.smarthome.green_dao.entities.DaoSession;
//import kolevmobile.com.smarthome.green_dao.entities.DeviceBaseEntity;
//import kolevmobile.com.smarthome.old_model.CustomSensorsDevice;
//import kolevmobile.com.smarthome.old_model.DHTDevice;
//import kolevmobile.com.smarthome.old_model.Device;
//import kolevmobile.com.smarthome.old_model.RelayDevice;
//
///**
// * Created by me on 01/11/2017.
// */
//
//public class DeviceDAOImpl implements DeviceDAO {
//
//    private DaoSession daoSession = ((App) getApplication()).getDaoSession();
//    private DeviceBaseEntityDao deviceBaseEntityDao = daoSession.getDeviceBaseEntityDao();
//
//    @Override
//    public List<Device> loadAll() {
//        List<DeviceBaseEntity> allDevicesBase = deviceBaseEntityDao.loadAll();
//        List<Device> devices = new ArrayList<>();
//        for (DeviceBaseEntity deviceBase : allDevicesBase) {
//            devices.add(createDeviceFromBase(deviceBase));
//        }
//        return devices;
//    }
//
//    @Override
//    public Device load(long id) {
//        DeviceBaseEntity devicesBase = deviceBaseEntityDao.load(id);
//        return createDeviceFromBase(devicesBase);
//    }
//
//    private Device createDeviceFromBase(DeviceBaseEntity deviceBase) {
//        Device device;
//        switch (deviceBase.getDeviceType()) {
//            case DeviceBaseEntity.DHT_DEVICE:
//                device = new DHTDevice(deviceBase.getId(), deviceBase.getName(), deviceBase.getDescription(), deviceBase.getUrlAdress(), deviceBase.getPort());
//                break;
//            case DeviceBaseEntity.CUSTOM_SENSOR_DEVICE:
//                device = new CustomSensorsDevice(deviceBase.getId(), deviceBase.getName(), deviceBase.getDescription(), deviceBase.getUrlAdress(), deviceBase.getPort());
//                //todo add data
//                break;
//            case DeviceBaseEntity.RELAY_DEVICE:
//                device = new RelayDevice(deviceBase.getId(), deviceBase.getName(), deviceBase.getDescription(), deviceBase.getUrlAdress(), deviceBase.getPort());
//                //todo add data
//                break;
//            default:
//                throw new IllegalArgumentException();
//        }
//        return device;
//    }
//
//    @Override
//    public long insert(Device device) {
//        if(device instanceof DHTDevice){
//
//        } else if(device instanceof CustomSensorsDevice){
//
//        } else if(device instanceof RelayDevice){
//
//        } else {
//            throw new IllegalArgumentException();
//        }
//    }
//
//    @Override
//    public void update(Device sensorData) {
//
//    }
//
//    @Override
//    public boolean delete(long id) {
//        return false;
//    }
//
//    @Override
//    public boolean delete(Device device) {
//        return false;
//    }
//}
