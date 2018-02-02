package kolevmobile.com.smarthome.di;

import javax.inject.Singleton;

import dagger.Component;
import kolevmobile.com.smarthome.add_edit_device.AddEditDeviceActivity;
import kolevmobile.com.smarthome.add_edit_device.AddEditPresenter;
import kolevmobile.com.smarthome.add_edit_device.DeviceGeneralFragment;
import kolevmobile.com.smarthome.add_edit_device.DeviceRelaysFragment;
import kolevmobile.com.smarthome.add_edit_device.DeviceSenosrsFragment;
import kolevmobile.com.smarthome.details.DetailsActivity;
import kolevmobile.com.smarthome.details.DetailsPresenter;
import kolevmobile.com.smarthome.job_scheduler.MyJobService;
import kolevmobile.com.smarthome.main.MainActivity;
import kolevmobile.com.smarthome.main.MainPresenter;
import kolevmobile.com.smarthome.widget.WidgetPresenter;
import kolevmobile.com.smarthome.widget.WidgetService;
import kolevmobile.com.smarthome.widget.WidgetSettingsActivity;


@Singleton
@Component(modules = {PresenterModule.class})
public interface PresenterComponent {


    MainPresenter getMainPresenter();

    AddEditPresenter getAddEditPresenter();

    DetailsPresenter getDetailsPresenter();

    WidgetPresenter getWidgetPresenter();

    void inject(MainActivity mainActivity);

    void inject(AddEditDeviceActivity addEditDeviceActivity);

    void inject(DeviceGeneralFragment addEditDeviceActivity);

    void inject(DeviceRelaysFragment addEditDeviceActivity);

    void inject(DeviceSenosrsFragment addEditDeviceActivity);

    void inject(DetailsActivity detailsActivity);

    void inject(WidgetSettingsActivity widgetSettingsActivity);

    void inject(WidgetService widgetService);

    void inject(MyJobService myJobService);

}
