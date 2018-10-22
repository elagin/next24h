package crew4dev.ru.next24h.ui.models;

import java.util.List;

import crew4dev.ru.next24h.SingleEventLiveData;
import crew4dev.ru.next24h.data.TaskItem;
import crew4dev.ru.next24h.db.interfaces.CollectDbApiContract;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainModel extends DefaultModel {

    private SingleEventLiveData<List<TaskItem>> tasksData = new SingleEventLiveData();

    public MainModel(CollectDbApiContract dbApi) {
        super(dbApi);
    }

    public SingleEventLiveData<List<TaskItem>> getTasks() { return tasksData; }

    public void reloadTaskList() {
        dbApi.getTasks().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<TaskItem>>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onSuccess(List<TaskItem> taskList) {
                tasksData.postValue(taskList);
            }

            @Override
            public void onError(Throwable e) {
                tasksData.postValue(null);
            }
        });
    }
}