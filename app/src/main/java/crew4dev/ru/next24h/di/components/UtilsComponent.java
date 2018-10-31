package crew4dev.ru.next24h.di.components;

import crew4dev.ru.next24h.SharedPrefApi;
import crew4dev.ru.next24h.data.LocalDatabase;
import crew4dev.ru.next24h.di.modules.ContextModule;
import crew4dev.ru.next24h.di.modules.RoomModule;
import crew4dev.ru.next24h.di.modules.SharedPrefApiModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {SharedPrefApiModule.class, ContextModule.class, RoomModule.class,})
public interface UtilsComponent {
    SharedPrefApi getSharedPrefApi();// поднимаем  выше.
    LocalDatabase getDatabase();
}