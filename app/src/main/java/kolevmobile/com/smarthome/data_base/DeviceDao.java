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
public interface DeviceDao {


    @Query("SELECT * FROM devices")
    List<Device> getAll();

//    @Query("SELECT * FROM devices WHERE name LIKE :name LIMIT 1")
//    Device findByName(String name);

    @Insert
    void insertAll(Device... devices);

    @Update
    void update(Device device);

    @Delete
    void delete(Device device);


//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    List<User> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
//            + "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);



}
