package crew4dev.ru.next24h.ui.controllers.interfaces;

import crew4dev.ru.next24h.data.TaskGroup;

public interface MainControllerContract {
    void getTaskList();

    void updateGroup(TaskGroup group);

    void getGroups();
}
