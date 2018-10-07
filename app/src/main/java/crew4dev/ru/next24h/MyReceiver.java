package crew4dev.ru.next24h;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import crew4dev.ru.next24h.ui.NotificationUtils;

import static crew4dev.ru.next24h.Constants.TASK_DESC;
import static crew4dev.ru.next24h.Constants.TASK_TITLE;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    public MyReceiver() {
        Log.d(TAG, "MyReceiver");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = null;
        String desc = null;
        if (intent.getExtras() != null && intent.getExtras().get(TASK_TITLE) != null) {
            Log.d(TAG, "onReceive: " + title);
            title = intent.getStringExtra(TASK_TITLE);
            if (intent.getExtras().get(TASK_DESC) != null)
                desc = intent.getStringExtra(TASK_DESC);
        }

        NotificationUtils n = NotificationUtils.getInstance(context);
        n.createInfoNotification(title, desc);
/*
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        if (title != null && !title.isEmpty()) {
            intent1.putExtra(Constants.TASK_TITLE, title);
            if (desc != null)
                intent1.putExtra(Constants.TASK_DESC, desc);
        }
        context.startService(intent1);
*/
    }
}
