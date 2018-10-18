package crew4dev.ru.next24h.ui.interfaces;

import crew4dev.ru.next24h.ui.controllers.interfaces.MainControllerContract;

public interface MainActivityContract extends DefaultActivityContract {
    MainControllerContract getMainController();
}
