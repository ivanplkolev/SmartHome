package kolevmobile.com.smarthome.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

@Entity
public class RelayStatus {
    @Id
    private Long id;

    private Long relayModelId;

    private Integer value;

    private Integer delay;

    private Integer duration;

    private Date sentAt;
    
    @Generated(hash = 432726482)
    public RelayStatus(Long id, Long relayModelId, Integer value, Integer delay,
            Integer duration, Date sentAt) {
        this.id = id;
        this.relayModelId = relayModelId;
        this.value = value;
        this.delay = delay;
        this.duration = duration;
        this.sentAt = sentAt;
    }

    @Generated(hash = 1810610780)
    public RelayStatus() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelayModelId() {
        return this.relayModelId;
    }

    public void setRelayModelId(Long relayModelId) {
        this.relayModelId = relayModelId;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getDelay() {
        return this.delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getSentAt() {
        return this.sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }




}