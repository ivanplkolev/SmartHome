package kolevmobile.com.smarthome.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kolevmobile.com.smarthome.add_edit_device.AddEditPresenter;
import kolevmobile.com.smarthome.add_edit_device.AddEditPresenterImpl;
import kolevmobile.com.smarthome.details.DetailsPresenter;
import kolevmobile.com.smarthome.details.DetailsPresenterImpl;
import kolevmobile.com.smarthome.main.MainPresenter;
import kolevmobile.com.smarthome.main.MainPresenterImpl;
import kolevmobile.com.smarthome.model.DaoSession;


@Module
public class PresenterModule {

    private DaoSession daoSession;

    public PresenterModule(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Provides
    @Singleton
    MainPresenter getMainPresenter() {
        return new MainPresenterImpl(daoSession);
    }

    @Provides
    @Singleton
    AddEditPresenter getAddEditPresenter() {
        return new AddEditPresenterImpl(daoSession);
    }

    @Provides
    @Singleton
    DetailsPresenter getDetailsPresenter() {
        return new DetailsPresenterImpl(daoSession);
    }
}
