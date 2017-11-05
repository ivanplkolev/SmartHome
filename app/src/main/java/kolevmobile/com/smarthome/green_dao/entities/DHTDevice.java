//package kolevmobile.com.smarthome.green_dao.entities;
//
//import org.greenrobot.greendao.annotation.Entity;
//import org.greenrobot.greendao.annotation.Id;
//import org.greenrobot.greendao.annotation.JoinProperty;
//import org.greenrobot.greendao.annotation.OrderBy;
//import org.greenrobot.greendao.annotation.ToMany;
//
//import java.util.List;
//import org.greenrobot.greendao.annotation.Generated;
//import org.greenrobot.greendao.DaoException;
//import org.greenrobot.greendao.annotation.ToOne;
//import org.greenrobot.greendao.annotation.NotNull;
//import org.greenrobot.greendao.annotation.Transient;
//
///**
// * Created by me on 29/10/2017.
// */
//@Entity(nameInDb = "sensor_data")
//public class DHTDevice {
//
//    @Id(autoincrement = true)
//    private Long id;
//
//    private String name;
//
//    private String description;
//
//    private String url;
//
//    private long actualSensorDataId;
//
//    @ToOne(joinProperty = "actualSensorDataId")
//    private DHTSensorData actualSensorData;
//
//    @ToMany(joinProperties = {
//            @JoinProperty(name = "id", referencedName = "deviceId")
//    })
//    @OrderBy("measuredAt ASC")
//    private List<DHTSensorData> measurements;
//
//    @Transient
//    boolean refreshing;
//
//    /** Used to resolve relations */
//    @Generated(hash = 2040040024)
//    private transient DaoSession daoSession;
//
//    /** Used for active entity operations. */
//    @Generated(hash = 1699809347)
//    private transient DHTDeviceDao myDao;
//
//    @Generated(hash = 829891424)
//    private transient Long actualSensorData__resolvedKey;
//
//
//    public DHTDevice(){}
//
//    public DHTDevice(String name, String description, String url){
//        this.name = name;
//        this.url = url;
//        this.url = url;
//    }
//
//    @Generated(hash = 331831064)
//    public DHTDevice(Long id, String name, String description, String url, long actualSensorDataId) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.url = url;
//        this.actualSensorDataId = actualSensorDataId;
//    }
//
//    /**
//     * To-many relationship, resolved on first access (and after reset).
//     * Changes to to-many relations are not persisted, make changes to the target entity.
//     */
//    @Generated(hash = 1045547351)
//    public List<DHTSensorData> getMeasurements() {
//        if (measurements == null) {
//            final DaoSession daoSession = this.daoSession;
//            if (daoSession == null) {
//                throw new DaoException("Entity is detached from DAO context");
//            }
//            DHTSensorDataDao targetDao = daoSession.getDHTSensorDataDao();
//            List<DHTSensorData> measurementsNew = targetDao
//                    ._queryDHTDevice_Measurements(id);
//            synchronized (this) {
//                if (measurements == null) {
//                    measurements = measurementsNew;
//                }
//            }
//        }
//        return measurements;
//    }
//
//    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
//    @Generated(hash = 743810406)
//    public synchronized void resetMeasurements() {
//        measurements = null;
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
//    public Long getId() {
//        return this.id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return this.description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getUrl() {
//        return this.url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public long getActualSensorDataId() {
//        return this.actualSensorDataId;
//    }
//
//    public void setActualSensorDataId(long actualSensorDataId) {
//        this.actualSensorDataId = actualSensorDataId;
//    }
//
//    /** To-one relationship, resolved on first access. */
//    @Generated(hash = 1458418490)
//    public DHTSensorData getActualSensorData() {
//        long __key = this.actualSensorDataId;
//        if (actualSensorData__resolvedKey == null || !actualSensorData__resolvedKey.equals(__key)) {
//            final DaoSession daoSession = this.daoSession;
//            if (daoSession == null) {
//                throw new DaoException("Entity is detached from DAO context");
//            }
//            DHTSensorDataDao targetDao = daoSession.getDHTSensorDataDao();
//            DHTSensorData actualSensorDataNew = targetDao.load(__key);
//            synchronized (this) {
//                actualSensorData = actualSensorDataNew;
//                actualSensorData__resolvedKey = __key;
//            }
//        }
//        return actualSensorData;
//    }
//
//    /** called by internal mechanisms, do not call yourself. */
//    @Generated(hash = 1045507406)
//    public void setActualSensorData(@NotNull DHTSensorData actualSensorData) {
//        if (actualSensorData == null) {
//            throw new DaoException(
//                    "To-one property 'actualSensorDataId' has not-null constraint; cannot set to-one to null");
//        }
//        synchronized (this) {
//            this.actualSensorData = actualSensorData;
//            actualSensorDataId = actualSensorData.getId();
//            actualSensorData__resolvedKey = actualSensorDataId;
//        }
//    }
//
//    /** called by internal mechanisms, do not call yourself. */
//    @Generated(hash = 1712713683)
//    public void __setDaoSession(DaoSession daoSession) {
//        this.daoSession = daoSession;
//        myDao = daoSession != null ? daoSession.getDHTDeviceDao() : null;
//    }
//
//
//}
