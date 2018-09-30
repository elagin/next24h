package crew4dev.ru.next24h;

import android.app.Application;
import android.arch.persistence.room.Room;

import crew4dev.ru.next24h.data.LocalDatabase;

public class App extends Application {

    private static App instance;
    private static LocalDatabase mDb;

    public static LocalDatabase db() {
        return instance.mDb;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        mDb = Room.databaseBuilder(this, LocalDatabase.class, "local_db")
                .allowMainThreadQueries()
                .build();

    }
}
