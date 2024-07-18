package com.orion.service_playground.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// service class have its own life cycle
class CounterAppService : Service() {
    var counter = 0
    var job: Job?= null
    var isRunning = false
    override fun onCreate() {
        super.onCreate()
        //service will be created it well be called
    }
    //when we implement on bond that time it will be called
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRunning = true
        CoroutineScope(Dispatchers.Default).launch {
            while (isRunning)
            {
                counter++
                delay(1000)
                val intent = Intent("counter_screen").apply{
                    putExtra("counter", counter)
                }
                sendBroadcast(intent)
            }
        }

        return START_STICKY
    //if any reason service will be destroyed if we get back the resource it start from start automatically
    // START_REDELIVER_INTENT will preserve data. it will resume if resource will get back again
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        isRunning = false
    }

}