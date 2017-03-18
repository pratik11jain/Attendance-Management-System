package com.iub.mobilecomputing.attendancemanagementsystem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Pratik on 03/16/2017.
 */

public class NewService extends Service {
    private static Timer timer = new Timer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android
        Log.i(getClass().getSimpleName(), "yoyoyoyo");
        _startService();
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 100, restartServicePI);

    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        _startService();
        //start a separate thread and start listening to your network object
    }

    private void _startService() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                long UPDATE_INTERVAL = new Random().nextInt(20) * 1000;
                timer.scheduleAtFixedRate(
                        new TimerTask() {
                            public void run() {
                                doServiceWork();
                            }
                        }, 1000, UPDATE_INTERVAL);
                Log.i("yoyoyoyoyo", "NewService Timer started....");
            }
        };
        new Thread(r).start();
    }

    private void doServiceWork() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;
        //while(true){
        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            String bssid = wifiInfo.getBSSID();
            int frequency = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                frequency = wifiInfo.getFrequency();
            }
            //Toast.makeText(getApplicationContext(), bssid + " " + frequency, Toast.LENGTH_SHORT).show();
            Log.i("yoyoyoyoyo", bssid + " " + frequency);
        }
    }
    /*
    private void _shutdownService()
    {
        if (timer != null) timer.cancel();
        Log.i(getClass().getSimpleName(), "Timer stopped...");
    }
    */

    @Override
    public void onDestroy() {
        _startService();
        super.onDestroy();
    }
}
