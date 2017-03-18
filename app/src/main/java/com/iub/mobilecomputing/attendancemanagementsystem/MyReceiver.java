package com.iub.mobilecomputing.attendancemanagementsystem;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent serviceIntent = new Intent(context, NewService.class);
        context.startService(serviceIntent);
        throw new UnsupportedOperationException("Not yet implemented");
    }

}