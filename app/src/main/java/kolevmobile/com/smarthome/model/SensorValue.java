package kolevmobile.com.smarthome.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

@Entity
public class SensorValue {
    @Id
    private Long id;

    private Long sensorModelId;

    private Float value;

    private Date measuredAt;

    @Generated(hash = 712999724)
    public SensorValue(Long id, Long sensorModelId, Float value, Date measuredAt) {
        this.id = id;
        this.sensorModelId = sensorModelId;
        this.value = value;
        this.measuredAt = measuredAt;
    }

    @Generated(hash = 1511460590)
    public SensorValue() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSensorModelId() {
        return this.sensorModelId;
    }

    public void setSensorModelId(Long sensorModelId) {
        this.sensorModelId = sensorModelId;
    }

    public Float getValue() {
        return this.value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Date getMeasuredAt() {
        return this.measuredAt;
    }

    public void setMeasuredAt(Date measuredAt) {
        this.measuredAt = measuredAt;
    }



}
