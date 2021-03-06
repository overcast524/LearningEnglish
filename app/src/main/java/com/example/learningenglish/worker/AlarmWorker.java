package com.example.learningenglish.worker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.learningenglish.R;
import com.example.learningenglish.config.ConstantData;
import com.example.learningenglish.until.MyApplication;
import com.example.learningenglish.until.NumberController;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmWorker extends  Worker {

    private static final String TAG = "AlarmWorker";

    public AlarmWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {

        NotificationManager manager = (NotificationManager) MyApplication.getContext().getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel(ConstantData.channelId, ConstantData.channelName, NotificationManager.IMPORTANCE_HIGH));
        }

        Notification notification = new NotificationCompat.Builder(MyApplication.getContext(), ConstantData.channelId)
                .setContentTitle("开始背单词啦")
                .setContentText(ConstantData.phrases[NumberController.getRandomNumber(0, ConstantData.phrases.length - 1)])
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icon_tip)
                .setLargeIcon(BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.ic_launcher))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        manager.notify(1, notification);

        Log.d(TAG, "doWork: ");
        return Result.success();
    }
}

