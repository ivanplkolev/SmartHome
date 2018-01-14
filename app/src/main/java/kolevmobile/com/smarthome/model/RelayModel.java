package kolevmobile.com.smarthome.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

@Entity
public class RelayModel {
    @Id
    private Long id;

    private Long deviceId;

    private Integer position;

    private String name;

    private String description;

    private String key;

    @ToMany(referencedJoinProperty = "relayModelId")
    private List<RelayStatus> relayStatusListlList;

    private Long actualStatusId;

    @ToOne(joinProperty = "actualStatusId")
    private RelayStatus actualStatus;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1231157592)
    private transient RelayModelDao myDao;

    @Generated(hash = 1876098250)
    private transient Long actualStatus__resolvedKey;

    @Generated(hash = 987338136)
    public RelayModel(Long id, Long deviceId, Integer position, String name, String description,
            String key, Long actualStatusId) {
        this.id = id;
        this.deviceId = deviceId;
        this.position = position;
        this.name = name;
        this.description = description;
        this.key = key;
        this.actualStatusId = actualStatusId;
    }

    @Generated(hash = 161011584)
    public RelayModel() {
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1046494478)
    public List<RelayStatus> getRelayStatusListlList() {
        if (relayStatusListlList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RelayStatusDao targetDao = daoSession.getRelayStatusDao();
            List<RelayStatus> relayStatusListlListNew = targetDao
                    ._queryRelayModel_RelayStatusListlList(id);
            synchronized (this) {
                if (relayStatusListlList == null) {
                    relayStatusListlList = relayStatusListlListNew;
                }
            }
        }
        return relayStatusListlList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 448484918)
    public synchronized void resetRelayStatusListlList() {
        relayStatusListlList = null;
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

    public Long getActualStatusId() {
        return this.actualStatusId;
    }

    public void setActualStatusId(Long actualStatusId) {
        this.actualStatusId = actualStatusId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 401773974)
    public RelayStatus getActualStatus() {
        Long __key = this.actualStatusId;
        if (actualStatus__resolvedKey == null || !actualStatus__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RelayStatusDao targetDao = daoSession.getRelayStatusDao();
            RelayStatus actualStatusNew = targetDao.load(__key);
            synchronized (this) {
                actualStatus = actualStatusNew;
                actualStatus__resolvedKey = __key;
            }
        }
        return actualStatus;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 558723468)
    public void setActualStatus(RelayStatus actualStatus) {
        synchronized (this) {
            this.actualStatus = actualStatus;
            actualStatusId = actualStatus == null ? null : actualStatus.getId();
            actualStatus__resolvedKey = actualStatusId;
        }
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1710191845)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRelayModelDao() : null;
    }




}