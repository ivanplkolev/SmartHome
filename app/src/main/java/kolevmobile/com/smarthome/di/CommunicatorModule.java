package kolevmobile.com.smarthome.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kolevmobile.com.smarthome.Presenter;
import kolevmobile.com.smarthome.connection.Communicator;
import kolevmobile.com.smarthome.connection.CommunicatorImpl;
import kolevmobile.com.smarthome.connection.DeviceResponceParser;
import kolevmobile.com.smarthome.connection.DeviceResponceParserImpl;
import kolevmobile.com.smarthome.main.MainPresenter;
import kolevmobile.com.smarthome.widget.WidgetPresenter;


@Module
public class CommunicatorModule {

    private Presenter mainPresenter;

    public CommunicatorModule(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
    }

    public CommunicatorModule(Presenter mainPresenter){
        this.mainPresenter = (MainPresenter) mainPresenter;
    }

    public CommunicatorModule(WidgetPresenter mainPresenter){
        this.mainPresenter = (MainPresenter) mainPresenter;
    }

    @Provides
    @Singleton
    Communicator getCommunicator() {
        return new CommunicatorImpl(mainPresenter);
    }


    @Provides
    @Singleton
    DeviceResponceParser getParser() {
        return new DeviceResponceParserImpl(mainPresenter.getDaoSession());
    }

}
