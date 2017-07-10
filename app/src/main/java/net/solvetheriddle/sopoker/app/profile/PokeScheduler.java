package net.solvetheriddle.sopoker.app.profile;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

public class PokeScheduler {

    private static final String TAG = PokeScheduler.class.getCanonicalName();

    private static final int POKE_JOB_ID = 46;
    private static final long DELAY_SEC = 10;
    private static final int DEADLINE_SEC = 20;

    private Context mContext;
    private ComponentName mServiceComponent;

    public PokeScheduler(Context context) {
        mContext = context;
        mServiceComponent = new ComponentName(context, PokeScheduler.class);
    }


    public void schedule() {
        JobInfo.Builder builder = new JobInfo.Builder(POKE_JOB_ID, mServiceComponent);

        builder.setMinimumLatency(DELAY_SEC * 1000);
        builder.setOverrideDeadline(DEADLINE_SEC * 1000);

//        builder.setRequiresDeviceIdle(mRequiresIdleCheckbox.isChecked());
//        builder.setRequiresCharging(mRequiresChargingCheckBox.isChecked());

//        boolean requiresUnmetered = mWiFiConnectivityRadioButton.isChecked();
//        if (requiresUnmetered) {
//            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
//        } else {
//            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY); // TODO needs any network
//        }

//        // Extras, work duration.
//        PersistableBundle extras = new PersistableBundle();
//        String workDuration = mDurationTimeEditText.getText().toString();
//        if (TextUtils.isEmpty(workDuration)) {
//            workDuration = "1";
//        }
//        extras.putLong(WORK_DURATION_KEY, Long.valueOf(workDuration) * 1000);
//
//        builder.setExtras(extras);

        // Schedule job
        Log.d(TAG, "Scheduling job");
        JobScheduler tm = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(builder.build());
    }
}
