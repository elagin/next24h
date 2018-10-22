package crew4dev.ru.next24h;

import android.app.Application;
import android.arch.persistence.room.Room;

import crew4dev.ru.next24h.data.LocalDatabase;
import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.di.components.DaggerUtilsComponent;
import crew4dev.ru.next24h.di.components.UtilsComponent;
import crew4dev.ru.next24h.di.modules.ContextModule;
import crew4dev.ru.next24h.di.modules.RoomModule;

import static crew4dev.ru.next24h.Constants.DbName;
import static crew4dev.ru.next24h.data.LocalDatabase.MIGRATION_1_2;
import static crew4dev.ru.next24h.data.LocalDatabase.MIGRATION_2_3;
import static crew4dev.ru.next24h.data.LocalDatabase.MIGRATION_3_4;

public class App extends Application {

    private static App instance;
    private static LocalDatabase mDb;
    private UtilsComponent utilsComponent;

    public static LocalDatabase db() {
        return instance.mDb;
    }

    public static App getApplication() {
        return instance;
    }

    public UtilsComponent getUtilsComponent() {
        return utilsComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        utilsComponent = DaggerUtilsComponent.builder().roomModule(new RoomModule(DbName)).contextModule(new ContextModule(getApplicationContext())).build();
        mDb = Room.databaseBuilder(this, LocalDatabase.class, "local_db")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .build();
        if (App.db().collectDao().groupCount() == 0) {
            TaskGroup firstGroup = new TaskGroup();
            firstGroup.setVisible(true);
            firstGroup.setName("Общее");
            App.db().collectDao().insert(firstGroup);
        }
    }
}
