package crew4dev.ru.next24h.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import crew4dev.ru.next24h.SharedPrefApi;
import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class SharedPrefApiModule {
    @Provides
    @Singleton
    public SharedPrefApi provideSharedPrefApi(Context context) {
        return new SharedPrefApi(context);
    }
}
