package com.example.s17149fk

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyReceiver : BroadcastReceiver() {
    public lateinit var main: MainActivity;

    override fun onReceive(context: Context, intent: Intent) {
        recive(context,intent);
    }

    private lateinit var sp: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor
    var contextRecived: Context? = null
    var intentRecived: Intent? = null
    var UID: Long? = 0
    fun recive(contextIncoming: Context, intentIncoming: Intent){
        //extract uid and save vars
        UID = intentIncoming.getLongExtra("UID",-1);
        contextRecived=contextIncoming;
        intentRecived=intentIncoming;

        //create pending intent for redirect
        val pendingIntent = PendingIntent.getActivity(
            contextRecived,
             1,
            Intent(contextRecived,intentRecived!!::class.java),
            PendingIntent.FLAG_ONE_SHOT
        );

        //get unique id for notification
        sp = contextRecived!!.getSharedPreferences(AppCompatActivity.NOTIFICATION_SERVICE,AppCompatActivity.MODE_PRIVATE);
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

    }

}