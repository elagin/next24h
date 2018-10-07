package crew4dev.ru.next24h.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.HashMap;

import crew4dev.ru.next24h.R;

public class NotificationUtils {
    private static final String TAG = NotificationUtils.class.getSimpleName();

    private static NotificationUtils instance;

    private static Context context;
    private NotificationManager manager; // Системная утилита, упарляющая уведомлениями
    private int lastId = 0; //постоянно увеличивающееся поле, уникальный номер каждого уведомления
    //private HashMap<Integer, Notification> notifications; //массив ключ-значение на все отображаемые пользователю уведомления
    private HashMap<String, Integer> notifications; //массив ключ-значение на все отображаемые пользователю уведомления


    //приватный контструктор для Singleton
    private NotificationUtils(Context context) {
        this.context = context;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //notifications = new HashMap<Integer, Notification>();
        notifications = new HashMap<String, Integer>();
    }

    /**
     * Получение ссылки на синглтон
     */
    public static NotificationUtils getInstance(Context context) {
        instance = new NotificationUtils(context);
        return instance;
    }

    //@mipmap/ic_launcher
    public int createInfoNotification(String title, String desc) {
        Intent notificationIntent = new Intent(context, MainActivity.class); // по клику на уведомлении откроется HomeActivity
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
//NotificationCompat.Builder builder = new NotificationBuilder(context) //для версии Android > 3.0
                .setSmallIcon(R.drawable.ic_launcher_foreground) //иконка уведомления
                .setAutoCancel(true) //уведомление закроется по клику на него
                .setTicker(title) //текст, который отобразится вверху статус-бара при создании уведомления
                .setContentText(desc) // Основной текст уведомления
                .setContentIntent(PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT))
                .setWhen(System.currentTimeMillis()) //отображаемое время уведомления
                .setContentTitle(title) //заголовок уведомления
                .setDefaults(Notification.DEFAULT_ALL); // звук, вибро и диодный индикатор выставляются по умолчанию

        Notification notification = builder.build(); //генерируем уведомление
        manager.notify(lastId, notification); // отображаем его пользователю.
        notifications.put(title, lastId); //теперь мы можем обращаться к нему по id
        return lastId++;
    }

    public void cancelNotification(String title) {
        Integer id = notifications.get(title);
        if (id != null)
            manager.cancel(id);
    }
}
