package crew4dev.ru.next24h.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

@Database(entities = {TaskItem.class}, version = 2)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract TaskDao tasks();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tasks ADD COLUMN time TEXT");
            database.execSQL("ALTER TABLE tasks ADD COLUMN isRemind INTEGER DEFAULT 0 NOT NULL");
        }
    };
}
