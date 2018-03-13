package net.solvetheriddle.sopoker.app.schedule;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PokeScheduler {

    private static final String TAG = PokeScheduler.class.getCanonicalName();

    private static final int POKE_JOB_ID = 46;

    private static final int THREE_TIMES_A_DAY = 8 * 60 * 60 * 1000;
    private static final int SIX_HOURS = 6 * 60 * 60 * 1000;
    private static final int EVERY_30_MINUTES = 30 * 60 * 1000;
    private static final int TEN_MINUTES = 10 * 60 * 1000;

    private Context mContext;
    private ComponentName mServiceComponent;

    public PokeScheduler(Context context) {
        mContext = context;
        mServiceComponent = new ComponentName(context, PokeService.class);
    }


    public void schedule() {
        JobInfo.Builder builder = new JobInfo.Builder(POKE_JOB_ID, mServiceComponent);

        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        final Date todaysNoon = calendar.getTime();
        final Date now = new Date();
        if (now.after(todaysNoon)) {
            // TODO run now
        } else {
            // TODO run by 9pm
        }
//            scheduleForTomorrow();

        builder.setPeriodic(THREE_TIMES_A_DAY, SIX_HOURS);
        builder.setPersisted(true);

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
        if (tm != null) {
            tm.schedule(builder.build());
            Log.i(TAG, "Poking scheduled to happen daily at 9pm");
        }
    }
}
