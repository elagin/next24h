package crew4dev.ru.next24h.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

@Database(entities = {TaskItem.class, TaskGroup.class}, version = 4)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract TaskDao tasks();

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tasks ADD COLUMN taskGroupId INTEGER DEFAULT 0 NOT NULL");
            database.execSQL("CREATE TABLE IF NOT EXISTS `taskGroups` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `isVisible` INTEGER NOT NULL)");
        }
    };

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tasks ADD COLUMN time TEXT");
            database.execSQL("ALTER TABLE tasks ADD COLUMN isRemind INTEGER DEFAULT 0 NOT NULL");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tasks ADD COLUMN hour INTEGER DEFAULT NULL");
            database.execSQL("ALTER TABLE tasks ADD COLUMN minute INTEGER DEFAULT NULL");
        }
    };

    public abstract TaskGroupDao taskGroups();
}

