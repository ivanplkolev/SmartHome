package kolevmobile.com.smarthome.data_base;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by x on 21.10.2017 г..
 */

@Database(entities = {Device.class, SensorData.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract DeviceDao deviceDao();
    public abstract SensorDataDao sensorDataDao();
}


