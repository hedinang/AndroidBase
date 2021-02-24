package com.fashion.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fashion.NanoApplication;

public class NetworkConnectReceiver extends BroadcastReceiver {

    public static NetworkConnectionListener networkConnectionListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnect = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (networkConnectionListener != null){
            networkConnectionListener.onNetworkConnection(isConnect);
        }

    }

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) NanoApplication.getInstance()
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface NetworkConnectionListener{
        void onNetworkConnection(boolean isConnected);
    }

    public void setNetworkConnectionListener(NetworkConnectionListener listener){
        networkConnectionListener = listener;
    }
}