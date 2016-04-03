package com.dino.ncsu.dinorunner;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 *
 */
public class RunningService extends Service {
    /**
     * Constructor for the Running service to run
     * in the background when the app is inactive.
     */
    public RunningService() {
    }

    /**
     * Returns the communication channel to the service.
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * TODO: Give notifications when activity is done (such as sound playing, etc..)
     */
}
