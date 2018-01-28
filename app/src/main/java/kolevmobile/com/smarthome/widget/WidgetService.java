package kolevmobile.com.smarthome.widget;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import kolevmobile.com.smarthome.App;


public class WidgetService extends IntentService {

    static final String WIDGET_ID = "WIDGET_ID";
    static final String ACTION_ADD = "ACTION_ADD";
    static final String ACTION_CLICK = "ACTION_CLICK";
    static final String ACTION_DELETE = "ACTION_DELETE";

    static final String WIDGET_TYPE = "WIDGET_TYPE";

    @Inject
    WidgetPresenter presenter;

    public WidgetService() {
        super("Ivan service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ((App) getApplication()).getPresenterComponent().inject(this);

        presenter.setContext(this);

        int widgetId = intent.getIntExtra(WIDGET_ID, 0);
        int widgetType = intent.getIntExtra(WIDGET_TYPE, 0);
        String action = intent.getAction();


        switch (action) {
            case ACTION_ADD:
                presenter.createWidget(widgetId, widgetType);
                break;
            case ACTION_CLICK:
                presenter.onClick(widgetId, widgetType);
                break;
            case ACTION_DELETE:
                presenter.deleteWidget(widgetId, widgetType);
                break;
        }

    }
}
