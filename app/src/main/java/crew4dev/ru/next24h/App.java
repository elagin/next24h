package crew4dev.ru.next24h;

import android.app.Application;
import android.arch.persistence.room.Room;

import crew4dev.ru.next24h.data.LocalDatabase;
import crew4dev.ru.next24h.data.TaskGroup;

import static crew4dev.ru.next24h.data.LocalDatabase.MIGRATION_1_2;
import static crew4dev.ru.next24h.data.LocalDatabase.MIGRATION_2_3;
import static crew4dev.ru.next24h.data.LocalDatabase.MIGRATION_3_4;

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
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .build();
        if (App.db().taskGroups().getGroups().size() == 0) {
            TaskGroup firstGroup = new TaskGroup();
            firstGroup.setVisible(true);
            firstGroup.setName("Общее");
            App.db().taskGroups().insert(firstGroup);
        }
    }
}
