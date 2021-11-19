package com.example.s17149fk

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.*
import android.os.Binder
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

public class MyService : Service() {
    public inner class MyBinder:Binder(){
        public fun getService(): MyService = this@MyService;
    }
    private lateinit var myBinder: MyBinder;
    init{
        myBinder = MyBinder();
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId);

        return Service.START_NOT_STICKY;

    }

    override fun onBind(intent: Intent): IBinder {

        return myBinder;
    }


    private lateinit var sp: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor
    var contextRecived: Context? = null
    var intentRecived: Intent? = null
    var UID: Long? = 0
    fun recive(contextIncoming: Context, intentIncoming: Intent): Int{
        //extract uid and save vars
        UID = intentIncoming.getLongExtra("UID",-1);
        contextRecived=contextIncoming;
        intentRecived=intentIncoming;

        val pendingIntent = PendingIntent.getActivity(
            contextRecived,
            1,
            Intent().setComponent(
                ComponentName("com.example.s17149","com.example.s17149.AddOrEditActivity")
            ).also {
                it.putExtra("UID",UID)
                it.setAction((2+2).toString())
            },
            PendingIntent.FLAG_ONE_SHOT
        );

        //get unique id for notification
        sp = contextRecived!!.getSharedPreferences(
            AppCompatActivity.NOTIFICATION_SERVICE,
            AppCompatActivity.MODE_PRIVATE);
        spEditor = sp.edit();
        var notidicationId = sp.getInt("notidicationId",1);
        spEditor.putInt("notidicationId",notidicationId+1);
        spEditor.commit();

        //create notification
        val notification = NotificationCompat
            .Builder(contextRecived!!,"channelId1")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("New Item was added to SMB_PK!")
            .setContentTitle("Item with UID: ${UID} was added! check it out! Id = ${notidicationId}")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build();

        //channel
        val channel = NotificationChannel(
            "channelId1",
            "channelName1",
            NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManagerCompat.from(contextRecived!!)
            .createNotificationChannel(channel)

        //send notification
        NotificationManagerCompat
            .from(contextRecived!!)
            .notify(notidicationId,notification);

        return 0;
    }
}