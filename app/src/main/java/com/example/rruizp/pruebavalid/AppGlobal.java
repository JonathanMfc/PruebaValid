package com.example.rruizp.pruebavalid;

import android.app.Application;

import com.example.rruizp.pruebavalid.broadcast.ConnectivityReceiver;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class AppGlobal extends Application {

    private static  AppGlobal mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppGlobal getInstance(){
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener){
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
