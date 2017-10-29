package kolevmobile.com.smarthome.data_base;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by x on 21.10.2017 г..
 */

@Entity(tableName = "devices",
        foreignKeys = @ForeignKey(entity = SensorData.class,
                parentColumns = "uid",
                childColumns = "actual_sensor_data_uid"))
public class Device implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "url_adress")
    private String urlAdress;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @Ignore
    private String errorMessage;

    @Ignore
    private boolean refreshing;

    @ColumnInfo(name = "actual_sensor_data_uid")
    private Integer actualSensorDataUid;

    @Ignore
    private SensorData actualSensorData;

    public Device(String urlAdress, String name, String description) {
        this.urlAdress = urlAdress;
        this.name = name;
        this.description = description;
//        this.deviceType = deviceType;
    }

    public String getUrlAdress() {
        return urlAdress;
    }

    public void setUrlAdress(String urlAdress) {
        this.urlAdress = urlAdress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Integer getActualSensorDataUid() {
        return actualSensorDataUid;
    }

    public void setActualSensorDataUid(Integer actualSensorDataUid) {
        this.actualSensorDataUid = actualSensorDataUid;
    }

    public boolean isRefreshing() {
        return refreshing;
    }

    public void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
    }

    public SensorData getActualSensorData() {
        return actualSensorData;
    }

    public void setActualSensorData(SensorData actualSensorData) {
        this.actualSensorData = actualSensorData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
