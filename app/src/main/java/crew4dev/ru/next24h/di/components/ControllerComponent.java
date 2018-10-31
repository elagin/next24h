package crew4dev.ru.next24h.di.components;

import crew4dev.ru.next24h.di.modules.ActivityModule;
import crew4dev.ru.next24h.di.modules.ApiDbModule;
import crew4dev.ru.next24h.di.modules.ControllerModule;
import crew4dev.ru.next24h.ui.MainActivity;
import crew4dev.ru.next24h.ui.TaskDetailsActivity;
import dagger.Component;

@ControllerScope
@Component(dependencies = UtilsComponent.class, modules = {ControllerModule.class, ActivityModule.class, ApiDbModule.class})
public interface ControllerComponent {
    void inject(MainActivity mainActivity);
    void inject(TaskDetailsActivity taskDetailsActivity);
}