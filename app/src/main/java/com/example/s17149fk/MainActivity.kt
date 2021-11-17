package com.example.s17149fk

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.s17149fk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var biding: ActivityMainBinding;
    private lateinit var reciver: MyReceiver;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        biding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(biding.root);

        reciver = MyReceiver();
        reciver.main=this;
        createChannel(this,"channelId1","channelName1");
    }
    fun createChannel(context:Context, Id: String, name: String){

    }
    override fun onStart() {
        super.onStart()
        registerReceiver(reciver, IntentFilter())
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(reciver);
    }

    fun goToAppButton(view: android.view.View) {}
}