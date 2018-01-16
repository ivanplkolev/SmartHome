package kolevmobile.com.smarthome.details;

import android.os.Handler;
import android.os.Message;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.SensorValue;
import kolevmobile.com.smarthome.model.SensorValueDao;


public class DetailsPresenterImpl implements DetailsPresenter {

    private Handler handler;

    private Device device;

    private DetailsView view;

    private DeviceDao deviceDao;
    private SensorValueDao sensorValueDao;

    private List<DetailModel> detailModelList;

    public DetailsPresenterImpl(DaoSession daoSession) {
        deviceDao = daoSession.getDeviceDao();
        sensorValueDao = daoSession.getSensorValueDao();
    }

    @Override
    public void setView(DetailsView view) {
        this.view = view;
    }

    @Override
    public void setDetailsHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void initDevice(Long deviceId) {
        device = deviceDao.load(deviceId);

        Message message1 = new Message();
        message1.obj = device.getName();
        message1.what = DetailsActivity.DetailsHandler.DO_SET_DEVICE_NAME;
        handler.sendMessage(message1);

        Message message2 = new Message();
        message2.obj = device.getDescription();
        message2.what = DetailsActivity.DetailsHandler.DO_SET_DEVICE_DESCRIPTION;
        handler.sendMessage(message2);

        detailModelList = new ArrayList<>(device.getSensorModelList().size());
        for (SensorModel sensorModel : device.getSensorModelList()) {
            detailModelList.add(new DetailModel(sensorModel));
        }
        Message message3 = new Message();
        message3.obj = detailModelList;
        message3.what = DetailsActivity.DetailsHandler.DO_INIT_SENSORS;
        handler.sendMessage(message3);

        loadHistory(DetailsPeriod.WEEK);
    }

    @Override
    public void loadHistory(DetailsPeriod period) {
        Date end = new Date();
        Date start = getSartDate(end, period);
        loadHistory(start, end, period);
        handler.sendEmptyMessage(DetailsActivity.DetailsHandler.DO_UPDATE_ALL_VIEWS);
    }


    private Date getSartDate(Date end, DetailsPeriod period) {
        Calendar c = Calendar.getInstance();
        c.setTime(end);
        switch (period) {
            case DAY:
                c.add(Calendar.DAY_OF_MONTH, -1);
                break;
            case WEEK:
                c.add(Calendar.DAY_OF_MONTH, -7);
                break;
            case MONTH:
                c.add(Calendar.MONTH, -1);
                break;
            case YEAR:
                c.add(Calendar.YEAR, -1);
                break;
        }
        return c.getTime();
    }


    public void loadHistory(Date start, Date end, DetailsPeriod period) {
        for (DetailModel detailModel : detailModelList) {
            QueryBuilder<SensorValue> qb = sensorValueDao.queryBuilder();
            qb.where(SensorValueDao.Properties.SensorModelId.eq(detailModel.getSensorModelId()),
                    SensorValueDao.Properties.MeasuredAt.between(start, end));
            List<SensorValue> list = qb.list();
            detailModel.setSensorValueList(list);
            detailModel.setDetailsPeriod(period);
        }
    }

}
