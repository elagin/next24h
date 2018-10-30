package crew4dev.ru.next24h.ui.controllers;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import java.util.List;

import javax.inject.Inject;

import crew4dev.ru.next24h.SharedPrefApi;
import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.ui.controllers.interfaces.MainControllerContract;
import crew4dev.ru.next24h.ui.interfaces.MainActivityContract;
import crew4dev.ru.next24h.ui.models.MainModel;
import io.reactivex.annotations.Nullable;

public class MainControllerImp extends DefaultControllerImp implements MainControllerContract {
    private final MainActivityContract activity;
    private final MainModel model;

    @Inject
    public MainControllerImp(SharedPrefApi sharedPrefApi, MainActivityContract mainActivity, MainModel model, LifecycleOwner lifecycleOwner) {
        super(sharedPrefApi, mainActivity, model, lifecycleOwner);
        this.activity = mainActivity;
        this.model = model;
        subscribeToModel();
    }

    @Override
    public void subscribeToModel() {
        super.subscribeToModel();
        model.reloadGroupList();
        model.getTasks().observe(lifecycleOwner, activity::reloadItems);
        model.getGroups().observe(lifecycleOwner, new Observer<List<TaskGroup>>() {
            @Override
            public void onChanged(@Nullable List<TaskGroup> response) {
                activity.reloadGroups(response);
                getTaskList();
            }
        });
    }

    public void getTaskList() {
        model.reloadTaskList();
    }

    @Override
    public void updateGroup(TaskGroup group) {
        model.updateGroup(group);
    }

    @Override
    public void getGroups() {
        model.reloadGroupList();
    }
}
