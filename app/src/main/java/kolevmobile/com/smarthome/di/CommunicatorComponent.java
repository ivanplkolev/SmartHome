package kolevmobile.com.smarthome.di;

import javax.inject.Singleton;

import dagger.Component;
import kolevmobile.com.smarthome.Presenter;
import kolevmobile.com.smarthome.connection.*;
import kolevmobile.com.smarthome.main.MainPresenterImpl;

@Singleton
@Component(modules = {CommunicatorModule.class})
public interface CommunicatorComponent {

    Communicator getCommunicator();
    DeviceResponceParser getParser();

    void inject(MainPresenterImpl mainPresenter);

    void inject(Presenter mainPresenter);

}
