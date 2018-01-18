package kolevmobile.com.smarthome.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToOne;

@Entity
public class Widget {

   public  final static int TYPE_SENSOR = 1;
   public final static int TYPE_RELAY = 2;


    @Id
    private Long id;

    @Index(unique=true)
    private Integer widgetiD;

    private Long deviceId;

    @ToOne(joinProperty = "deviceId")
    private Device device;

    private Long sensorModelId;

    @ToOne(joinProperty = "sensorModelId")
    private SensorModel sensorModel;

    private Long relayModelId;

    @ToOne(joinProperty = "relayModelId")
    private RelayModel relayModel;

    private Integer type;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 142730641)
    private transient WidgetDao myDao;



    @Generated(hash = 1073296571)
    public Widget(Long id, Integer widgetiD, Long deviceId, Long sensorModelId,
            Long relayModelId, Integer type) {
        this.id = id;
        this.widgetiD = widgetiD;
        this.deviceId = deviceId;
        this.sensorModelId = sensorModelId;
        this.relayModelId = relayModelId;
        this.type = type;
    }

    @Generated(hash = 938966889)
    public Widget() {
    }

    @Generated(hash = 708752895)
    private transient Long device__resolvedKey;
    @Generated(hash = 1078709724)
    private transient Long sensorModel__resolvedKey;
    @Generated(hash = 1052194195)
    private transient Long relayModel__resolvedKey;



    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Long getSensorModelId() {
        return this.sensorModelId;
    }

    public void setSensorModelId(Long sensorModelId) {
        this.sensorModelId = sensorModelId;
    }

    public Long getRelayModelId() {
        return this.relayModelId;
    }

    public void setRelayModelId(Long relayModelId) {
        this.relayModelId = relayModelId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1120785031)
    public Device getDevice() {
        Long __key = this.deviceId;
        if (device__resolvedKey == null || !device__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DeviceDao targetDao = daoSession.getDeviceDao();
            Device deviceNew = targetDao.load(__key);
            synchronized (this) {
                device = deviceNew;
                device__resolvedKey = __key;
            }
        }
        return device;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 189959207)
    public void setDevice(Device device) {
        synchronized (this) {
            this.device = device;
            deviceId = device == null ? null : device.getId();
            device__resolvedKey = deviceId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 531236245)
    public SensorModel getSensorModel() {
        Long __key = this.sensorModelId;
        if (sensorModel__resolvedKey == null
                || !sensorModel__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SensorModelDao targetDao = daoSession.getSensorModelDao();
            SensorModel sensorModelNew = targetDao.load(__key);
            synchronized (this) {
                sensorModel = sensorModelNew;
                sensorModel__resolvedKey = __key;
            }
        }
        return sensorModel;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2076534078)
    public void setSensorModel(SensorModel sensorModel) {
        synchronized (this) {
            this.sensorModel = sensorModel;
            sensorModelId = sensorModel == null ? null : sensorModel.getId();
            sensorModel__resolvedKey = sensorModelId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 132275092)
    public RelayModel getRelayModel() {
        Long __key = this.relayModelId;
        if (relayModel__resolvedKey == null
                || !relayModel__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RelayModelDao targetDao = daoSession.getRelayModelDao();
            RelayModel relayModelNew = targetDao.load(__key);
            synchronized (this) {
                relayModel = relayModelNew;
                relayModel__resolvedKey = __key;
            }
        }
        return relayModel;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 39578693)
    public void setRelayModel(RelayModel relayModel) {
        synchronized (this) {
            this.relayModel = relayModel;
            relayModelId = relayModel == null ? null : relayModel.getId();
            relayModel__resolvedKey = relayModelId;
        }
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

    public Integer getWidgetiD() {
        return this.widgetiD;
    }

    public void setWidgetiD(Integer widgetiD) {
        this.widgetiD = widgetiD;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 403624969)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWidgetDao() : null;
    }

   
}
