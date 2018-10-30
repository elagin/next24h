package crew4dev.ru.next24h.ui.models;

import java.util.List;

import crew4dev.ru.next24h.SingleEventLiveData;
import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.data.TaskItem;
import crew4dev.ru.next24h.db.interfaces.CollectDbApiContract;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainModel extends DefaultModel {

    public MainModel(CollectDbApiContract dbApi) {
        super(dbApi);
    }

    private SingleEventLiveData<List<TaskItem>> tasksData = new SingleEventLiveData();
    public SingleEventLiveData<List<TaskItem>> getTasks() { return tasksData; }

    private SingleEventLiveData<Boolean> taskGroupData = new SingleEventLiveData();

    private SingleEventLiveData<List<TaskGroup>> groupsData = new SingleEventLiveData();
    public SingleEventLiveData<List<TaskGroup>> getGroups() { return groupsData; }

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
                groupsData.postValue(null);
            }
        });
    }

    public void updateGroup(TaskGroup group) {
        dbApi.update(group).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onComplete() {
                taskGroupData.postValue(true);
            }

            @Override
            public void onError(Throwable e) {
                taskGroupData.postValue(false);
            }
        });
    }

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