package kolevmobile.com.smarthome.job_scheduler;

import android.content.Context;

import kolevmobile.com.smarthome.Presenter;

/**
 * Created by x on 30.1.2018 г..
 */

public interface JobPresenter extends Presenter{

    void updateDevices();

    void setContext(Context context);
}
