package crew4dev.ru.next24h.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import crew4dev.ru.next24h.data.LocalDatabase;
import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class RoomModule {
    private String dbName;

    public RoomModule(String dbName) {
        this.dbName = dbName;
    }

    @Provides
    @Singleton
    public LocalDatabase provideRoomDatabase(Context context) {
        return Room.databaseBuilder(context, LocalDatabase.class, dbName).fallbackToDestructiveMigration().build();
    }
}
