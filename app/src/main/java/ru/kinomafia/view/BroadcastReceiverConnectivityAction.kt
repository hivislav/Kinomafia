package ru.kinomafia.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class BroadcastReceiverConnectivityAction : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Изменилось соединение: ${intent?.action}", Toast.LENGTH_SHORT).show()
    }
}