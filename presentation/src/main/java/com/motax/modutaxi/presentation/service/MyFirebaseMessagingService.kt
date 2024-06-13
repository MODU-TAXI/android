package com.motax.modutaxi.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.motax.modutaxi.presentation.R
import com.motax.modutaxi.presentation.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun handleIntent(intent: Intent?) {
        val new = intent?.apply {
            val temp = extras?.apply {
                remove(Constants.MessageNotificationKeys.ENABLE_NOTIFICATION)
            }
            replaceExtras(temp)
        }
        super.handleIntent(new)
    }

    override fun onMessageReceived(message: RemoteMessage) {

        val msg = message.data["message"]
        showNotification(msg)
        when (message.data["messageType"]) {


        }

    }

    private fun showNotification(message: String? = "") {

        val uniId = (System.currentTimeMillis() / 7).toInt()
        val intent = Intent(this, SplashActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            this,
            uniId,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "modutaxi"

        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            priority = NotificationCompat.PRIORITY_HIGH
            setContentTitle("모두의 택시")
            setContentText(message)
            setContentIntent(pIntent)
            setAutoCancel(true)
            color = Color.argb(1, 120, 63, 59)
            setColorized(true)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            setSmallIcon(R.mipmap.ic_launcher)
        }

        // Head up 알람 설정
        notificationBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(Notification.DEFAULT_ALL)
            .setFullScreenIntent(pIntent, true)

        getSystemService(NotificationManager::class.java).run {
            val channel = NotificationChannel(channelId, "알림", NotificationManager.IMPORTANCE_HIGH)
            createNotificationChannel(channel)

            notify(uniId, notificationBuilder.build())
        }
    }

    suspend fun getFirebaseToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                continuation.resume(token)
            } else {
                continuation.resumeWithException(
                    task.exception ?: RuntimeException("Error getting FCM token")
                )
            }
        }
    }

}