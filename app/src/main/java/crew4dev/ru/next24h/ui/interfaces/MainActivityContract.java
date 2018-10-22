package crew4dev.ru.next24h.ui.interfaces;

import java.util.List;

import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.data.TaskItem;

public interface MainActivityContract extends DefaultActivityContract {
    void reloadItems(List<TaskItem> tasks);

    void reloadGroups(List<TaskGroup> groups);
}
