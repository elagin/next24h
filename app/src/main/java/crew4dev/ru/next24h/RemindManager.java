package crew4dev.ru.next24h;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import crew4dev.ru.next24h.data.TaskItem;

public class RemindManager {

    static final Object lock = new Object();
    private static final String TAG = "TasksAdapter";

    public static void setAllNotifes(Context context) {
        //List<TaskItem> tasks = App.db().tasks().getTasks();
        //zz
        /*
        List<TaskItem> tasks = App.db().collectDao().getTasks();
        for (TaskItem item : tasks) {
            if (!item.isComplete() && item.isRemind() && item.getTime() != null) {
                RemindManager.setNotify(context, item);
            }
        }
        */
    }

    public static void setNotify(Context context, TaskItem item) {
        final Calendar how = Calendar.getInstance();
        Calendar taskTime = (Calendar) how.clone();
        //synchronized (lock) {
        if (!item.isComplete() && item.isRemind() && item.getTime() != null) {
            String[] data = item.getTime().split(":");
            Integer hours;
            Integer minutes;
            if (data.length == 2) {
                hours = Integer.valueOf(data[0]);
                minutes = Integer.valueOf(data[1]);
                taskTime.set(Calendar.HOUR_OF_DAY, hours);
                taskTime.set(Calendar.MINUTE, minutes);
                //todo Надо ли taskTime.set(Calendar.SECOND, 0);
                if (taskTime.after(how)) {
                    Log.d(TAG, item.getTitle() + " cегодня в " + String.format("%1$tA %1$tb %1$td %1$tY at %1$tI:%1$tM %1$Tp", taskTime));
                } else {
                    taskTime.add(Calendar.DAY_OF_MONTH, 1);
                    Log.d(TAG, item.getTitle() + " завтра в " + String.format("%1$tA %1$tb %1$td %1$tY at %1$tI:%1$tM %1$Tp", taskTime));
                }
                Intent notifyIntent = new Intent(context, MyReceiver.class);
                ///notifyIntent.putExtra(Constants.COMMAND_CREATE_NOTIF, item.getTitle());
                notifyIntent.putExtra(Constants.TASK_TITLE, item.getTitle());
                notifyIntent.putExtra(Constants.TASK_DESC, item.getDescr());
                PendingIntent pendingIntent = PendingIntent.getBroadcast
                        (context, (int) item.getId(), notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                //alarmManager.notify();
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, taskTime.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
            }
        }
//            lock.notify();
//            try {
//                lock.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        //}
    }

    public static void cancelNotify(Context context, long itemId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent notifyIntent = new Intent(context.getApplicationContext(), MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), (int) (itemId), notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
