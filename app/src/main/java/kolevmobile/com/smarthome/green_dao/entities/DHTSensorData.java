//package kolevmobile.com.smarthome.green_dao.entities;
//
//import org.greenrobot.greendao.annotation.Entity;
//import org.greenrobot.greendao.annotation.Id;
//import org.greenrobot.greendao.annotation.ToOne;
//
//import java.util.Date;
//import org.greenrobot.greendao.annotation.Generated;
//import org.greenrobot.greendao.DaoException;
//import org.greenrobot.greendao.annotation.NotNull;
//
//import static kolevmobile.com.smarthome.DetailsActivity.simpleDateFormat;
//
//
//@Entity(nameInDb = "sensor_data")
//public class DHTSensorData {
//
//    @Id(autoincrement = true)
//    private Long id;
//
//    private long deviceId;
//
//    @ToOne(joinProperty = "deviceId")
//    private DHTDevice device;
//
//    private float temperature;
//
//    private float humidity;
//
//    private Date measuredAt;
//
//    /** Used to resolve relations */
//    @Generated(hash = 2040040024)
//    private transient DaoSession daoSession;
//
//    /** Used for active entity operations. */
//    @Generated(hash = 437819807)
//    private transient DHTSensorDataDao myDao;
//
//    @Generated(hash = 708752895)
//    private transient Long device__resolvedKey;
//
//    public DHTSensorData() {
//    }
//
//    public DHTSensorData(DHTDevice device, float temperature, float humidity, Date measuredAt) {
//        this.deviceId = device.getId();
//        this.device = device;
//        this.temperature = temperature;
//        this.humidity = humidity;
//        this.measuredAt = measuredAt;
//    }
//
//    @Generated(hash = 2098095806)
//    public DHTSensorData(Long id, long deviceId, float temperature, float humidity, Date measuredAt) {
//        this.id = id;
//        this.deviceId = deviceId;
//        this.temperature = temperature;
//        this.humidity = humidity;
//        this.measuredAt = measuredAt;
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public long getDeviceId() {
//        return this.deviceId;
//    }
//
//    public void setDeviceId(long deviceId) {
//        this.deviceId = deviceId;
//    }
//
//    public float getTemperature() {
//        return this.temperature;
//    }
//
//    public void setTemperature(float temperature) {
//        this.temperature = temperature;
//    }
//
//    public float getHumidity() {
//        return this.humidity;
//    }
//
//    public void setHumidity(float humidity) {
//        this.humidity = humidity;
//    }
//
//    public Date getMeasuredAt() {
//        return this.measuredAt;
//    }
//
//    public void setMeasuredAt(Date measuredAt) {
//        this.measuredAt = measuredAt;
//    }
//
//    /** To-one relationship, resolved on first access. */
//    @Generated(hash = 2034443640)
//    public DHTDevice getDevice() {
//        long __key = this.deviceId;
//        if (device__resolvedKey == null || !device__resolvedKey.equals(__key)) {
//            final DaoSession daoSession = this.daoSession;
//            if (daoSession == null) {
//                throw new DaoException("Entity is detached from DAO context");
//            }
//            DHTDeviceDao targetDao = daoSession.getDHTDeviceDao();
//            DHTDevice deviceNew = targetDao.load(__key);
//            synchronized (this) {
//                device = deviceNew;
//                device__resolvedKey = __key;
//            }
//        }
//        return device;
//    }
//
//    /** called by internal mechanisms, do not call yourself. */
//    @Generated(hash = 1370613779)
//    public void setDevice(@NotNull DHTDevice device) {
//        if (device == null) {
//            throw new DaoException(
//                    "To-one property 'deviceId' has not-null constraint; cannot set to-one to null");
//        }
//        synchronized (this) {
//            this.device = device;
//            deviceId = device.getId();
//            device__resolvedKey = deviceId;
//        }
//    }
//
//    /**
//     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
//     * Entity must attached to an entity context.
//     */
//    @Generated(hash = 128553479)
//    public void delete() {
//        if (myDao == null) {
//            throw new DaoException("Entity is detached from DAO context");
//        }
//        myDao.delete(this);
//    }
//
//    /**
//     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
//     * Entity must attached to an entity context.
//     */
//    @Generated(hash = 1942392019)
//    public void refresh() {
//        if (myDao == null) {
//            throw new DaoException("Entity is detached from DAO context");
//        }
//        myDao.refresh(this);
//    }
//
//    /**
//     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
//     * Entity must attached to an entity context.
//     */
//    @Generated(hash = 713229351)
//    public void update() {
//        if (myDao == null) {
//            throw new DaoException("Entity is detached from DAO context");
//        }
//        myDao.update(this);
//    }
//
//    public String getTemperatureString() {
//        return String.valueOf(temperature) + "\u00B0C";
//    }
//
//    public String getHumidityString() {
//        return String.valueOf(humidity) + "%";
//    }
//
//    public String getMeasuredAtString() {
//        return simpleDateFormat.format(measuredAt);
//    }
//
//    /** called by internal mechanisms, do not call yourself. */
//    @Generated(hash = 657528598)
//    public void __setDaoSession(DaoSession daoSession) {
//        this.daoSession = daoSession;
//        myDao = daoSession != null ? daoSession.getDHTSensorDataDao() : null;
//    }
//
//
//}
