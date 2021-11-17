package com.example.s17149fk

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
    }
    override fun onStart() {
        super.onStart()
        registerReceiver(reciver, IntentFilter())
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(reciver);
    }

    var contextRecived: Context? = null
    var intentRecived: Intent? = null
    public fun recive(contextIncoming: Context, intentIncoming: Intent){
        biding.button.setText("UID: ${intentIncoming.getLongExtra("UID",-1)}");
        contextRecived=contextIncoming;
        intentRecived=intentIncoming;
    }
    fun goToApp(view: android.view.View) {

    }

}