package crew4dev.ru.next24h.ui.models;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import crew4dev.ru.next24h.SingleEventLiveData;
import crew4dev.ru.next24h.data.TaskItem;
import crew4dev.ru.next24h.db.interfaces.CollectDbApiContract;
import crew4dev.ru.next24h.ui.models.interfaces.DefaultModelContract;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DefaultModel extends ViewModel implements DefaultModelContract {
    protected CollectDbApiContract dbApi;

    private SingleEventLiveData<List<TaskItem>> TasksData = new SingleEventLiveData();

    public DefaultModel(CollectDbApiContract dbApi) {
        this.dbApi = dbApi;
    }

    @Override
    public void reloadTaskList() {
        dbApi.getTasks().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<TaskItem>>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onSuccess(List<TaskItem> taskList) {
                TasksData.postValue(taskList);
            }

            @Override
            public void onError(Throwable e) {
                TasksData.postValue(null);
            }
        });
    }
}