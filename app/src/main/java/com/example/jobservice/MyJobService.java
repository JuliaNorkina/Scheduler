package com.example.jobservice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class MyJobService extends JobService {

    private static final String NUMBER = "number";
    private static final String ACTION = "com.example.jobservice.RESULT";

    @Override
    public boolean onStartJob(JobParameters params) {
        int number = MathUtil.calculateFactorial(params.getExtras().getInt(NUMBER));
        Intent intent = new Intent().putExtra(NUMBER, number).setAction(ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//        Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
