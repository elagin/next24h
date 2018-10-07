package crew4dev.ru.next24h;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import crew4dev.ru.next24h.ui.MainActivity;

import static crew4dev.ru.next24h.Constants.TASK_DESC;
import static crew4dev.ru.next24h.Constants.TASK_TITLE;

public class MyNewIntentService extends IntentService {

    private static final String TAG = "MainActivity";

    private static final int NOTIFICATION_ID = 3;

    public MyNewIntentService() {
        super("MyNewIntentService");
        Log.d(TAG, "MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String title = intent.getStringExtra(TASK_TITLE);
        String desc = intent.getStringExtra(TASK_DESC);

        Log.d(TAG, "onHandleIntent: " + title);

        Notification.Builder builder = new Notification.Builder(this);
        if (title != null && !title.isEmpty()) {
            builder.setContentTitle(title);
        }
        builder.setContentText(desc);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);

        //managerCompat.cancelAll();
    }
}