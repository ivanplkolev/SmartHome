package kolevmobile.com.smarthome.data_base;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


/**
 * Created by x on 22.10.2017 г..
 */

@Entity(tableName = "sonsor_data",
        foreignKeys = @ForeignKey(entity = Device.class,
                parentColumns = "uid",
                childColumns = "device_uid"))
public class SensorData {


    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "temperature")
    private float temperature;

    @ColumnInfo(name = "humidity")
    private float humidity;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "device_uid")
    private int deviceUid;

    public SensorData(float temperature, float humidity, Date date, int deviceUid) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.date = date;
        this.deviceUid = deviceUid;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public String getTemperatureString() {
        return String.valueOf(temperature) + "\u00B0C";
    }

    public String getHumidityString() {
        return String.valueOf(humidity) + "%";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public String getDateString() {
//        return simpleDateFormat.format(getDate());
//    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getDeviceUid() {
        return deviceUid;
    }

    public void setDeviceUid(int deviceUid) {
        this.deviceUid = deviceUid;
    }
}
