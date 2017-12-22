package kolevmobile.com.smarthome.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kolevmobile.com.smarthome.add_edit_device.AddEditPresenter;
import kolevmobile.com.smarthome.add_edit_device.AddEditPresenterImpl;
import kolevmobile.com.smarthome.main.MainPresenter;
import kolevmobile.com.smarthome.main.MainPresenterImpl;
import kolevmobile.com.smarthome.model.DaoSession;

/**
 * Created by x on 10.12.2017 г..
 */

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
}
