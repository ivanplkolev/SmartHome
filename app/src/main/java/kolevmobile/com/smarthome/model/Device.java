package kolevmobile.com.smarthome.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import java.util.List;

/**
 * Created by me on 02/11/2017.
 */

@Entity
public class Device {
    @Id
    private Long id;

    private Integer position;

    private String name;

    private String description;

    private String urlAddress;

    private Integer port;

    private Date actualizationDate;

    @ToMany(referencedJoinProperty = "deviceId")
    private List<SensorModel> sensorModelList;

    @ToMany(referencedJoinProperty = "deviceId")
    private List<RelayModel> relayModelList;

    @Transient
    private Error error;

    @Transient
    private boolean refreshing;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 371273952)
    private transient DeviceDao myDao;

    @Generated(hash = 1761097086)
    public Device(Long id, Integer position, String name, String description, String urlAddress,
                  Integer port, Date actualizationDate) {
        this.id = id;
        this.position = position;
        this.name = name;
        this.description = description;
        this.urlAddress = urlAddress;
        this.port = port;
        this.actualizationDate = actualizationDate;
    }

    @Keep
    public Device() {
        setName("");
        setDescription("");
        setPosition(0);
        setUrlAddress("");
        setPort(0);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUrlAddress() {
        return this.urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1531796290)
    public List<SensorModel> getSensorModelList() {
        if (sensorModelList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SensorModelDao targetDao = daoSession.getSensorModelDao();
            List<SensorModel> sensorModelListNew = targetDao
                    ._queryDevice_SensorModelList(id);
            synchronized (this) {
                if (sensorModelList == null) {
                    sensorModelList = sensorModelListNew;
                }
            }
        }
        return sensorModelList;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 763065939)
    public synchronized void resetSensorModelList() {
        sensorModelList = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 499425003)
    public List<RelayModel> getRelayModelList() {
        if (relayModelList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RelayModelDao targetDao = daoSession.getRelayModelDao();
            List<RelayModel> relayModelListNew = targetDao
                    ._queryDevice_RelayModelList(id);
            synchronized (this) {
                if (relayModelList == null) {
                    relayModelList = relayModelListNew;
                }
            }
        }
        return relayModelList;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 609925632)
    public synchronized void resetRelayModelList() {
        relayModelList = null;
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

    public Date getActualizationDate() {
        return this.actualizationDate;
    }

    public void setActualizationDate(Date actualizationDate) {
        this.actualizationDate = actualizationDate;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public boolean isRefreshing() {
        return refreshing;
    }

    public void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1755220927)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDeviceDao() : null;
    }

}