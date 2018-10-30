package crew4dev.ru.next24h.ui.interfaces;

import java.util.List;

import crew4dev.ru.next24h.data.TaskGroup;

public interface TaskDetailsActivityContract extends DefaultActivityContract {
    void reloadGroups(List<TaskGroup> groups);
}