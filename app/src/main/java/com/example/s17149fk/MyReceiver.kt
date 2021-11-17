package com.example.s17149fk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    public lateinit var main: MainActivity;

    override fun onReceive(context: Context, intent: Intent) {
        main.recive(context,intent);
    }
}