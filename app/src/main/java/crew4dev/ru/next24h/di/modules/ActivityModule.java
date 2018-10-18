package crew4dev.ru.next24h.di.modules;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;

import crew4dev.ru.next24h.di.components.ControllerScope;
import crew4dev.ru.next24h.ui.MainActivity;
import crew4dev.ru.next24h.ui.interfaces.MainActivityContract;
import crew4dev.ru.next24h.ui.models.CollectApiModelFactory;
import crew4dev.ru.next24h.ui.models.MainModel;
import dagger.Module;
import dagger.Provides;

@Module(/*includes = ApiModule.class*/)
public class ActivityModule {
    private LifecycleOwner lifecycleOwner;
    private MainActivity mainActivity;

    public ActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        lifecycleOwner = mainActivity;
    }

    @Provides
    @ControllerScope
    public LifecycleOwner provideLifecycleOwner() {
        return lifecycleOwner;
    }

    @Provides
    @ControllerScope
    public MainActivityContract provideMainActivity() {
        return mainActivity;
    }

    @Provides
    @ControllerScope
    public MainModel provideMainModel(/*CollectDbApiContract collectDbApi*/) {
        return ViewModelProviders.of(mainActivity, new CollectApiModelFactory(/*collectApi, collectDbApi*/)).get(MainModel.class);
    }
}
