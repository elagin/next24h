package crew4dev.ru.next24h.di.modules;

import crew4dev.ru.next24h.db.CollectDbApiImp;
import crew4dev.ru.next24h.db.interfaces.CollectDbApiContract;
import crew4dev.ru.next24h.di.components.ControllerScope;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApiDbModule {
    @Binds
    @ControllerScope
    abstract CollectDbApiContract provideCollectDbApi(CollectDbApiImp collectDbApiImp);
}