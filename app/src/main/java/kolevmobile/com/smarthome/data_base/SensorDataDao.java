package kolevmobile.com.smarthome.data_base;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by x on 21.10.2017 г..
 */
@Dao
public interface SensorDataDao {

    @Query("SELECT * FROM sonsor_data")
    List<SensorData> getAll();

    @Query("SELECT * FROM sonsor_data WHERE device_uid = :deviceUid")
    List<SensorData> getAllForDevice(int deviceUid);

    @Query("SELECT * FROM sonsor_data WHERE uid = :uid LIMIT 1")
    SensorData findByID(int uid);

    @Insert
    void insertAll(SensorData... sensorDatas);

    @Insert
    long insert(SensorData sensorData);

    @Update
    void update(SensorData sensorData);

    @Delete
    void delete(SensorData sensorData);


//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    List<User> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
//            + "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);


}
