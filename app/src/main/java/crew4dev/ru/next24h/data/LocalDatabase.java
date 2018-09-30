package crew4dev.ru.next24h.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {TaskItem.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract TaskDao tasks();
}
