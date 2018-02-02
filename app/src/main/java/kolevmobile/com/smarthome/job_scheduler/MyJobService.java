package kolevmobile.com.smarthome.job_scheduler;


import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.util.Log;

import javax.inject.Inject;

import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.BuildConfig;


public class MyJobService extends JobService {

    private static final String TAG = MyJobService.class.getSimpleName();

    public static final long LOOP_INTERVAL = 15 * 1000;

    public static final String MESSENGER_INTENT_KEY = BuildConfig.APPLICATION_ID + ".MESSENGER_INTENT_KEY";
    public static final String WORK_DURATION_KEY = BuildConfig.APPLICATION_ID + ".WORK_DURATION_IVAN_KEY";


    @Inject
    JobPresenter presenter;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");

        ((App) getApplication()).getPresenterComponent().inject(this);
        presenter.setContext(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
    }

    /**
     * When the app's MainActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        mActivityMessenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY);

        Log.i(TAG, "OnStart command");

        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        // The work that this service "does" is simply wait for a certain duration and finish
        // the job (on another thread).
//        System.out.println("IVAN IN THE SERVICE EXCECUTION !!!!!!!!!!!! -1");

        cancelAllJobs(this);

        scheduleJob(this);
//        System.out.println("IVAN IN THE SERVICE EXCECUTION !!!!!!!!!!!!-2");
        //todo
        presenter.updateDevices();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // Stop tracking these job parameters, as we've 'finished' executing.
//        sendMessage(MSG_COLOR_STOP, params.getJobId());
        Log.i(TAG, "on stop job: " + params.getJobId());

        // Return false to drop the job.
        return false;
    }

    public static void cancelAllJobs(Context context) {
        JobScheduler tm = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancelAll();
//        Toast.makeText(context, "All canceleed !!!! IVAN !", Toast.LENGTH_SHORT).show();
    }


    public static void scheduleJob(Context context) {
        ComponentName mServiceComponent = new ComponentName(context, MyJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(1, mServiceComponent);
        builder.setMinimumLatency(LOOP_INTERVAL);
        builder.setOverrideDeadline(LOOP_INTERVAL * 2);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);

        // Extras, work duration.
        PersistableBundle extras = new PersistableBundle();
        extras.putLong(WORK_DURATION_KEY, 1 * 1000);
        builder.setExtras(extras);

        // Schedule job
        Log.d(TAG, "Scheduling job");
        JobScheduler tm = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(builder.build());
    }


}
