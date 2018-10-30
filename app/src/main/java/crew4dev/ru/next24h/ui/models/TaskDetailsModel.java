package crew4dev.ru.next24h.ui.models;

import java.util.List;

import crew4dev.ru.next24h.SingleEventLiveData;
import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.db.interfaces.CollectDbApiContract;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskDetailsModel extends DefaultModel {

    private SingleEventLiveData<List<TaskGroup>> groupsData = new SingleEventLiveData();

    public TaskDetailsModel(CollectDbApiContract dbApi) {
        super(dbApi);
    }

    public SingleEventLiveData<List<TaskGroup>> getGroups() { return groupsData; }

    public void reloadGroupList() {
        dbApi.getGroups().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<TaskGroup>>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onSuccess(List<TaskGroup> taskList) {
                groupsData.postValue(taskList);
            }

            @Override
            public void onError(Throwable e) {
                groupsData.postValue(null);
            }
        });
    }
}