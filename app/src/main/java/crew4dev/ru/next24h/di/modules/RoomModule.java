package crew4dev.ru.next24h.di.modules;

import dagger.Module;

@Module(includes = ContextModule.class)
public class RoomModule {
    private String dbName;

    public RoomModule(String dbName) {
        this.dbName = dbName;
    }

//    @Provides
//    @Singleton
//    public CollectDatabase provideRoomDatabase(Context context){
//        return Room.databaseBuilder(context, CollectDatabase.class, dbName).fallbackToDestructiveMigration().build();
//    }
}
