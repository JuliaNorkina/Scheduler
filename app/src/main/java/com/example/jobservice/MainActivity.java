package com.example.jobservice;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String NUMBER = "number";
    private static final String ACTION = "com.example.jobservice.RESULT";

    private EditText etNumber;
    private TextView tvResult;
    private static int sJobId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        tvResult = findViewById(R.id.tvResult);
        findViewById(R.id.bCalculate).setOnClickListener(this);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case ACTION:
                        int number = intent.getIntExtra(NUMBER,1);
                        tvResult.setText(String.valueOf(number));
                        break;
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onClick(View v) {
        ComponentName jobService = new ComponentName(this, MyJobService.class);
        JobInfo.Builder jobBuilder = new JobInfo.Builder(sJobId++, jobService);
        jobBuilder.setMinimumLatency(3000);
        jobBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt(NUMBER, Integer.parseInt(etNumber.getText().toString()));
        jobBuilder.setExtras(persistableBundle);
        JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobBuilder.build());
    }
}
