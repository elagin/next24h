package crew4dev.ru.next24h.db.interfaces;

import java.util.List;

import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.data.TaskItem;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface CollectDbApiContract {
    Single<List<TaskItem>> getTasks();
    Single insert(TaskItem item);
    Completable update(TaskItem item);
    Completable delete(TaskItem item);

    Completable update(TaskGroup group);

    Single<List<TaskGroup>> getGroups();
}