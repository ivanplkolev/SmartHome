//package kolevmobile.com.smarthome.widget;
//
//import android.app.PendingIntent;
//import android.appwidget.AppWidgetManager;
//import android.appwidget.AppWidgetProvider;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.widget.RemoteViews;
//
//import kolevmobile.com.smarthome.R;
//import kolevmobile.com.smarthome.data_base.Device;
//import kolevmobile.com.smarthome.data_base.SensorData;
//import kolevmobile.com.smarthome.old_onnector.ArduinoWifiCommunicator;
//import kolevmobile.com.smarthome.old_onnector.InternetConnector;
//import kolevmobile.com.smarthome.old_onnector.NoDeviceException;
//import kolevmobile.com.smarthome.old_onnector.WrongSensorDataException;
//
///**
// * Created by x on 15.10.2017 г..
// */
//
//
//public class WidgetProvider extends AppWidgetProvider {
//
//    static private InternetConnector connector;
//
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        if (connector == null) {
//            connector = new ArduinoWifiCommunicator();
//        }
//
//
//        final int count = appWidgetIds.length;
//
//        for (int i = 0; i < count; i++) {
//            int widgetId = appWidgetIds[i];
//
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//            remoteViews.setTextViewText(R.id.errorTextView, "");
//
//            new GetSensorDataAsyncTask(remoteViews, appWidgetManager, widgetId).execute();
//
//            Intent intent = new Intent(context, WidgetProvider.class);
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.refreshButton, pendingIntent);
//            appWidgetManager.updateAppWidget(widgetId, remoteViews);
//        }
//    }
//
//    private class GetSensorDataAsyncTask extends AsyncTask<String, Void, SensorData> {
//        private RemoteViews remoteViews;
//        int widgetd;
//        AppWidgetManager appWidgetManager;
//
//        public GetSensorDataAsyncTask(RemoteViews remoteViews, AppWidgetManager appWidgetManager, int widgetd) {
//            this.remoteViews = remoteViews;
//            this.widgetd = widgetd;
//            this.appWidgetManager = appWidgetManager;
//        }
//
//        @Override
//        protected SensorData doInBackground(String... strings) {
//            SensorData sensorData = null;
//            ArduinoWifiCommunicator communicator = new ArduinoWifiCommunicator();
//            try {
//                sensorData = communicator.getData(new Device("http://192.168.1.11/", "", ""));
//
//            } catch (WrongSensorDataException e) {
//
//            } catch (NoDeviceException e) {
//
//            }
//            return sensorData;
//        }
//
//        public void onPostExecute(SensorData sensorData) {
//            if (sensorData != null) {
//                remoteViews.setTextViewText(R.id.temperatureTextView, sensorData.getTemperatureString());
//                remoteViews.setTextViewText(R.id.humidityTextView, sensorData.getHumidityString());
//                remoteViews.setTextViewText(R.id.lastMeasureDateTextView, sensorData.getDateString());
//            } else {
//                remoteViews.setTextViewText(R.id.errorTextView, "Problwm comunicating with device");
//            }
//            appWidgetManager.updateAppWidget(widgetd, remoteViews);
//        }
//    }
//
//}