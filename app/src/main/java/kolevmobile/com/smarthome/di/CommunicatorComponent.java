package kolevmobile.com.smarthome.di;

import javax.inject.Singleton;

import dagger.Component;
import kolevmobile.com.smarthome.connection.Communicator;
import kolevmobile.com.smarthome.main.MainPresenterImpl;

@Singleton
@Component(modules = {CommunicatorModule.class})
public interface CommunicatorComponent {

    Communicator getCommunicator();

    void inject(MainPresenterImpl mainPresenter);

}
