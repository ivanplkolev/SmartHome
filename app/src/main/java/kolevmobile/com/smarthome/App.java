package kolevmobile.com.smarthome;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import dagger.Module;
import kolevmobile.com.smarthome.di.PresenterComponent;
import kolevmobile.com.smarthome.di.PresenterModule;
import kolevmobile.com.smarthome.di.DaggerPresenterComponent;
import kolevmobile.com.smarthome.model.DaoMaster;
import kolevmobile.com.smarthome.model.DaoSession;

@Module
public class App extends Application {

    private DaoSession daoSession;
    private PresenterComponent presenterComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        presenterComponent = initDagger(daoSession);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


    public PresenterComponent getPresenterComponent() {
        return presenterComponent;
    }

    protected PresenterComponent initDagger(DaoSession daoSession) {
        return DaggerPresenterComponent.builder()
                .presenterModule(new PresenterModule(daoSession))
                .build();
    }
}
