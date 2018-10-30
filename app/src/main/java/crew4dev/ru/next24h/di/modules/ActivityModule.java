package crew4dev.ru.next24h.di.modules;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;

import crew4dev.ru.next24h.db.interfaces.CollectDbApiContract;
import crew4dev.ru.next24h.di.components.ControllerScope;
import crew4dev.ru.next24h.ui.MainActivity;
import crew4dev.ru.next24h.ui.TaskDetailsActivity;
import crew4dev.ru.next24h.ui.interfaces.MainActivityContract;
import crew4dev.ru.next24h.ui.interfaces.TaskDetailsActivityContract;
import crew4dev.ru.next24h.ui.models.CollectApiModelFactory;
import crew4dev.ru.next24h.ui.models.MainModel;
import crew4dev.ru.next24h.ui.models.TaskDetailsModel;
import dagger.Module;
import dagger.Provides;

@Module(/*includes = ApiModule.class*/)
public class ActivityModule {
    private LifecycleOwner lifecycleOwner;
    private MainActivity mainActivity;
    private TaskDetailsActivity taskDetailsActivity;

    public ActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        lifecycleOwner = mainActivity;
    }

    public ActivityModule(TaskDetailsActivity taskDetailsActivity) {
        this.taskDetailsActivity = taskDetailsActivity;
        lifecycleOwner = taskDetailsActivity;
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
    public MainModel provideMainModel(CollectDbApiContract collectDbApi) {
        return ViewModelProviders.of(mainActivity, new CollectApiModelFactory(collectDbApi)).get(MainModel.class);
    }

    @Provides
    @ControllerScope
    public TaskDetailsActivityContract provideTaskDetailsActivity() {
        return taskDetailsActivity;
    }

    @Provides
    @ControllerScope
    public TaskDetailsModel provideTaskDetailsModel(CollectDbApiContract collectDbApi) {
        return ViewModelProviders.of(taskDetailsActivity, new CollectApiModelFactory(collectDbApi)).get(TaskDetailsModel.class);
    }
}
