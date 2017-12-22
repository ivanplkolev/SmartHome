package kolevmobile.com.smarthome.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kolevmobile.com.smarthome.connection.Communicator;
import kolevmobile.com.smarthome.connection.CommunicatorImpl;

/**
 * Created by x on 10.12.2017 г..
 */

@Module
public class CommunicatorModule {

    @Provides
    @Singleton
    Communicator getCommunicator() {
        return new CommunicatorImpl();
    }

}
