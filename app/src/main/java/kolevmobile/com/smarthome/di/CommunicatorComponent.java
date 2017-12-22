package kolevmobile.com.smarthome.di;

import javax.inject.Singleton;

import dagger.Component;
import kolevmobile.com.smarthome.connection.Communicator;
import kolevmobile.com.smarthome.main.MainPresenterImpl;

/**
 * Created by x on 10.12.2017 г..
 */
@Singleton
@Component(modules = {CommunicatorModule.class})
public interface CommunicatorComponent {

    Communicator getCommunicator();

    void inject(MainPresenterImpl mainPresenter);

}
