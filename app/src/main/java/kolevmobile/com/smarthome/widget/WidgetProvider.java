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


public class WidgetProvider extends AppWidgetProvider {

    private static final String TAG = "IVAN DEBUG " + WidgetProvider.class.getSimpleName();
    public static final String ACTION_WidgetProvider_CLICKED = "ivan.pl.kolev.CLICKED_";

    @Override
    public void onEnabled(Context context) {
        Log.d(TAG, "onEnabled()");
    }

    @Override
    public void onDisabled(Context context) {
        Log.d(TAG, "onDisabled()");
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            Log.d(TAG, "onDeleted()" + appWidgetId);


            callServiceDeleted(appWidgetId, context);
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate()");
        updateAppWidgets(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() " + intent.getAction());
        super.onReceive(context, intent);
        if (intent != null) {
            String action = intent.getAction();
            if (action.startsWith(ACTION_WidgetProvider_CLICKED)) {
                int widgetId = Integer.parseInt(action.substring(ACTION_WidgetProvider_CLICKED.length()));
                Log.d(TAG, "onReceive() ckicked " + widgetId);

                callServiceClicked(widgetId, context);

            } else if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
                Log.d(TAG, "onReceive() UPDATE ");
            }
        }
    }


    static public void updateAppWidgets(final Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widgetComponent = new ComponentName(context, WidgetProvider.class);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(widgetComponent);
        for (int id : widgetIds) {
            WidgetProvider.updateAppWidget(context, appWidgetManager, id);
            callServiceWidgetCreated(id, context);
        }
    }


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent intent = new Intent(context, WidgetProvider.class);
        intent.setAction(ACTION_WidgetProvider_CLICKED + String.valueOf(appWidgetId));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        updateViews.setOnClickPendingIntent(R.id.refreshButtonWidget, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }


    private static void callServiceWidgetCreated(int id, Context context) {
        Intent serviceIntent = new Intent(context, WidgetService.class);
        serviceIntent.setAction(WidgetService.ACTION_ADD);
        serviceIntent.putExtra(WidgetService.WIDGET_ID, id);
        context.startService(serviceIntent);
    }


    private void callServiceClicked(int widgetId, Context context) {
        Intent serviceIntent = new Intent(context, WidgetService.class);
        serviceIntent.setAction(WidgetService.ACTION_CLICK);
        serviceIntent.putExtra(WidgetService.WIDGET_ID, widgetId);
        context.startService(serviceIntent);

    }


    private void callServiceDeleted(int appWidgetId, Context context) {
        Intent serviceIntent = new Intent(context, WidgetService.class);
        serviceIntent.setAction(WidgetService.ACTION_DELETE);
        serviceIntent.putExtra(WidgetService.WIDGET_ID, appWidgetId);
        context.startService(serviceIntent);

    }
}