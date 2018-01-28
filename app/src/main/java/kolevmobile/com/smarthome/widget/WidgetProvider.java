package kolevmobile.com.smarthome.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import kolevmobile.com.smarthome.R;


public abstract class WidgetProvider extends AppWidgetProvider {


    public static final int WIDGET_TYPE_SENSOR = 1;
    public static final int WIDGET_TYPE_RELAY = 2;


    private static final String TAG = "IVAN DEBUG !!!!! :----";
    public static final String ACTION_WidgetProvider_CLICKED = "ivan.pl.kolev.CLICKED_";


    @Override
    public void onEnabled(Context context) {
        Log.d(TAG, "onEnabled()");
    }

    @Override
    public void onDisabled(Context context) {
        Log.d(TAG, "onDisabled()");
    }


//    @Override
//    public void onDeleted(Context context, int[] appWidgetIds) {
//        final int N = appWidgetIds.length;
//        for (int i = 0; i < N; i++) {
//            int appWidgetId = appWidgetIds[i];
//            Log.d(TAG, "onDeleted()" + appWidgetId);
//
//
//            callServiceDeleted(appWidgetId, context, widgetType);
//        }
//    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate()");
        updateAppWidgets(context, getWidgetType());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "^^^^onReceive()^^^^^^^^^^ " + intent.getAction());
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (action != null && action.startsWith(ACTION_WidgetProvider_CLICKED)) {
            int widgetId = Integer.parseInt(action.substring(ACTION_WidgetProvider_CLICKED.length()));
            Log.d(TAG, "onReceive() ckicked " + widgetId);

            callServiceClicked(widgetId, context, getWidgetType());

        } else if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            Log.d(TAG, "onReceive() update but not my if!  action--" + intent.getAction());
        }
    }


    public void updateAppWidgets(final Context context, int widgetType) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widgetComponent = new ComponentName(context, getClass());
        int[] widgetIds = appWidgetManager.getAppWidgetIds(widgetComponent);
        for (int id : widgetIds) {
            updateAppWidget(context, appWidgetManager, id, widgetType);
            callServiceWidgetCreated(id, context, widgetType);
        }
    }


    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int widgetType) {
        Log.d(TAG, "onReceive() update widget- widgettype:" + widgetType);

        RemoteViews updateViews = new RemoteViews(context.getPackageName(), widgetType == WIDGET_TYPE_SENSOR ? R.layout.sensor_widget_layout : R.layout.relay_widget_layout);
        Intent intent = new Intent(context, getClass());
        intent.setAction(ACTION_WidgetProvider_CLICKED + String.valueOf(appWidgetId));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        updateViews.setOnClickPendingIntent(widgetType==WIDGET_TYPE_SENSOR ? R.id.wholeWidgetLayoutsensor : R.id.wholeWidgetLayoutrelay, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }


    private static void callServiceWidgetCreated(int id, Context context, int widgetType) {
        Intent serviceIntent = new Intent(context, WidgetService.class);
        serviceIntent.setAction(WidgetService.ACTION_ADD);
        serviceIntent.putExtra(WidgetService.WIDGET_ID, id);
        serviceIntent.putExtra(WidgetService.WIDGET_TYPE, widgetType);
        context.startService(serviceIntent);
    }


    private static void callServiceClicked(int widgetId, Context context, int widgetType) {
        Intent serviceIntent = new Intent(context, WidgetService.class);
        serviceIntent.setAction(WidgetService.ACTION_CLICK);
        serviceIntent.putExtra(WidgetService.WIDGET_ID, widgetId);
        serviceIntent.putExtra(WidgetService.WIDGET_TYPE, widgetType);
        context.startService(serviceIntent);

    }


    private static void callServiceDeleted(int appWidgetId, Context context, int widgetType) {
        Intent serviceIntent = new Intent(context, WidgetService.class);
        serviceIntent.setAction(WidgetService.ACTION_DELETE);
        serviceIntent.putExtra(WidgetService.WIDGET_ID, appWidgetId);
        serviceIntent.putExtra(WidgetService.WIDGET_TYPE, widgetType);
        context.startService(serviceIntent);

    }

    abstract int getWidgetType();
}