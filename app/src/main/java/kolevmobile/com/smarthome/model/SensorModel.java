package kolevmobile.com.smarthome.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

@Entity
public class SensorModel {
    @Id
    private Long id;

    private Long deviceId;

    private Integer position;

    private String name;

    private String description;

    private String key;

    private String units;

    private Float scaleFactor;

    private Integer precision;

    @ToMany(referencedJoinProperty = "sensorModelId")
    private List<SensorValue> sensroValueList;

    private Long actualValueId;

    @ToOne(joinProperty = "actualValueId")
    private SensorValue actualValue;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1191326351)
    private transient SensorModelDao myDao;

    @Generated(hash = 955858925)
    private transient Long actualValue__resolvedKey;

    @Generated(hash = 1472445992)
    public SensorModel(Long id, Long deviceId, Integer position, String name, String description,
            String key, String units, Float scaleFactor, Integer precision, Long actualValueId) {
        this.id = id;
        this.deviceId = deviceId;
        this.position = position;
        this.name = name;
        this.description = description;
        this.key = key;
        this.units = units;
        this.scaleFactor = scaleFactor;
        this.precision = precision;
        this.actualValueId = actualValueId;
    }

    @Generated(hash = 1303457506)
    public SensorModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUnits() {
        return this.units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Float getScaleFactor() {
        return this.scaleFactor;
    }

    public void setScaleFactor(Float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 795844918)
    public List<SensorValue> getSensroValueList() {
        if (sensroValueList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SensorValueDao targetDao = daoSession.getSensorValueDao();
            List<SensorValue> sensroValueListNew = targetDao._querySensorModel_SensroValueList(id);
            synchronized (this) {
                if (sensroValueList == null) {
                    sensroValueList = sensroValueListNew;
                }
            }
        }
        return sensroValueList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 522075861)
    public synchronized void resetSensroValueList() {
        sensroValueList = null;
    }

    public Long getActualValueId() {
        return this.actualValueId;
    }

    public void setActualValueId(Long actualValueId) {
        this.actualValueId = actualValueId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 746159924)
    public SensorValue getActualValue() {
        Long __key = this.actualValueId;
        if (actualValue__resolvedKey == null || !actualValue__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SensorValueDao targetDao = daoSession.getSensorValueDao();
            SensorValue actualValueNew = targetDao.load(__key);
            synchronized (this) {
                actualValue = actualValueNew;
                actualValue__resolvedKey = __key;
            }
        }
        return actualValue;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 66886588)
    public void setActualValue(SensorValue actualValue) {
        synchronized (this) {
            this.actualValue = actualValue;
            actualValueId = actualValue == null ? null : actualValue.getId();
            actualValue__resolvedKey = actualValueId;
        }
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 660156352)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSensorModelDao() : null;
    }

}