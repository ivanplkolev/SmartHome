package kolevmobile.com.smarthome.details;

import java.util.List;

import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.SensorValue;

public class DetailModel {

    private final String name;
    private final String description;
    private final Long sensorModelId;
    private List<SensorValue> sensorValueList;
    private DetailsPresenter.DetailsPeriod detailsPeriod;


    public DetailModel(SensorModel sensorModel) {
        this.sensorModelId = sensorModel.getId();
        this.name = sensorModel.getName();
        this.description = sensorModel.getDescription();
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<SensorValue> getSensorValueList() {
        return sensorValueList;
    }

    public void setSensorValueList(List<SensorValue> sensorValueList) {
        this.sensorValueList = sensorValueList;
    }

    public DetailsPresenter.DetailsPeriod getDetailsPeriod() {
        return detailsPeriod;
    }

    public void setDetailsPeriod(DetailsPresenter.DetailsPeriod detailsPeriod) {
        this.detailsPeriod = detailsPeriod;
    }

    public Long getSensorModelId() {
        return sensorModelId;
    }
}
