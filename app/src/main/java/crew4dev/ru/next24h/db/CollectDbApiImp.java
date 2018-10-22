package crew4dev.ru.next24h.db;

import java.util.List;

import javax.inject.Inject;

import crew4dev.ru.next24h.data.LocalDatabase;
import crew4dev.ru.next24h.data.TaskItem;
import crew4dev.ru.next24h.db.interfaces.CollectDbApiContract;
import io.reactivex.Completable;
import io.reactivex.Single;

public class CollectDbApiImp implements CollectDbApiContract {
    private LocalDatabase collectDatabase;

    @Inject
    public CollectDbApiImp(LocalDatabase collectDatabase) {
        this.collectDatabase = collectDatabase;
    }

    @Override
    public Single<List<TaskItem>> getTasks() {
        return collectDatabase.collectDao().getTasks();
    }

    @Override
    public Completable update(final TaskItem item) {
        return Completable.create(emitter -> {
            try {
                collectDatabase.collectDao().update(item);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Completable delete(final TaskItem item) {
        return Completable.create(emitter -> {
            try {
                collectDatabase.collectDao().delete(item);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Single<Long> insert(final TaskItem item) {
        return Single.create(emitter -> {
            try {
                Long points = collectDatabase.collectDao().insert(item);
                emitter.onSuccess(points);
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}