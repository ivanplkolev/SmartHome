package kolevmobile.com.smarthome.data_base;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

/**
 * Created by x on 21.10.2017 г..
 */

public class DataBaseSisngleton {
    static private final String DATABASE_NAME = "DB1";
    static private MyDatabase database;

    public static MyDatabase getDataBase(Context context) {
        if (database != null) {
            return database;
        }
        database = Room.databaseBuilder(context, MyDatabase.class, DATABASE_NAME).build();
        return database;
    }

    public static List<Device> getAllDevices(Context context) {
        List<Device> devices = getDataBase(context).deviceDao().getAll();
        for (Device d : devices) {
            if (d.getActualSensorDataUid() != null) {
                SensorData sd = getDataBase(context).sensorDataDao().findByID(d.getActualSensorDataUid());
                d.setActualSensorData(sd);
            }
        }
        return devices;
    }

    public static void addDevice(Device device, Context context) {
        getDataBase(context).deviceDao().insertAll(device);
    }

    public static void removeDevice(Device device, Context context) {
        getDataBase(context).deviceDao().delete(device);
    }

    public static void editDevice(Device device, Context context) {
        getDataBase(context).deviceDao().update(device);
    }

}
