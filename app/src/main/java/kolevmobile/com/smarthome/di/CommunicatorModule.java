package kolevmobile.com.smarthome.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kolevmobile.com.smarthome.connection.Communicator;
import kolevmobile.com.smarthome.connection.CommunicatorImpl;
import kolevmobile.com.smarthome.main.MainPresenter;

/**
 * Created by x on 10.12.2017 г..
 */

@Module
public class CommunicatorModule {

    private MainPresenter mainPresenter;

    public CommunicatorModule(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
    }

    @Provides
    @Singleton
    Communicator getCommunicator() {
        return new CommunicatorImpl(mainPresenter);
    }

}
