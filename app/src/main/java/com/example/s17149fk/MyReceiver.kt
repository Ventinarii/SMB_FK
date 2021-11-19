package com.example.s17149fk

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.*
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.jar.Manifest

class MyReceiver : BroadcastReceiver() {
    lateinit var main: MainActivity;

    lateinit var myService:MyService;
    val mcom = object: ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as MyService.MyBinder;
            myService = binder.getService();
        }
        override fun onServiceDisconnected(p0: ComponentName?) {
            TODO("Not yet implemented")
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        //recive(context,intent);

        val serviceIntent = Intent(context,MyService::class.java).putExtras(intent);

        //context.applicationContext.bindService(serviceIntent,mcom,Context.BIND_AUTO_CREATE);
        //myService.recive(context,intent).toString();
        //context.unbindService(mcom);

        context.startForegroundService(serviceIntent);

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