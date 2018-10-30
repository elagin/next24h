package crew4dev.ru.next24h.di.modules;

import crew4dev.ru.next24h.di.components.ControllerScope;
import crew4dev.ru.next24h.ui.controllers.MainControllerImp;
import crew4dev.ru.next24h.ui.controllers.TaskDetailsControllerImp;
import crew4dev.ru.next24h.ui.controllers.interfaces.MainControllerContract;
import crew4dev.ru.next24h.ui.controllers.interfaces.TaskDetailsControllerContract;
import dagger.Binds;
import dagger.Module;

@Module(includes = ActivityModule.class)
public abstract class ControllerModule {
    @Binds
    @ControllerScope
    abstract MainControllerContract provideMainController(MainControllerImp mainController);

    @Binds
    @ControllerScope
    abstract TaskDetailsControllerContract provideDetailsController(TaskDetailsControllerImp detailsController);
}
