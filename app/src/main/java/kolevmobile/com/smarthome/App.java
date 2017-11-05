package kolevmobile.com.smarthome;

import android.app.Application;
import org.greenrobot.greendao.database.Database;

import kolevmobile.com.smarthome.model.DaoMaster;
import kolevmobile.com.smarthome.model.DaoSession;

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
