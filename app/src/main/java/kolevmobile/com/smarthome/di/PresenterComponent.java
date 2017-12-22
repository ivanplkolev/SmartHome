package kolevmobile.com.smarthome.di;

import javax.inject.Singleton;

import dagger.Component;
import kolevmobile.com.smarthome.add_edit_device.AddEditDeviceActivity;
import kolevmobile.com.smarthome.add_edit_device.AddEditPresenter;
import kolevmobile.com.smarthome.add_edit_device.DeviceGeneralFragment;
import kolevmobile.com.smarthome.add_edit_device.DeviceRelaysFragment;
import kolevmobile.com.smarthome.add_edit_device.DeviceSenosrsFragment;
import kolevmobile.com.smarthome.main.MainActivity;
import kolevmobile.com.smarthome.main.MainPresenter;

/**
 * Created by x on 10.12.2017 г..
 */

@Singleton
@Component(modules = {PresenterModule.class})
public interface PresenterComponent {


    MainPresenter getMainPresenter();

    AddEditPresenter getAddEditPresenter();


    void inject(MainActivity mainActivity);

    void inject(AddEditDeviceActivity addEditDeviceActivity);

    void inject(DeviceGeneralFragment addEditDeviceActivity);

    void inject(DeviceRelaysFragment addEditDeviceActivity);

    void inject(DeviceSenosrsFragment addEditDeviceActivity);
}
