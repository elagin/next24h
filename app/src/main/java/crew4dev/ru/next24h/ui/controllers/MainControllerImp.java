package crew4dev.ru.next24h.ui.controllers;

import android.arch.lifecycle.LifecycleOwner;
import crew4dev.ru.next24h.SharedPrefApi;
import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.ui.controllers.interfaces.MainControllerContract;
import crew4dev.ru.next24h.ui.interfaces.MainActivityContract;
import crew4dev.ru.next24h.ui.models.MainModel;

import javax.inject.Inject;

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
        model.getGroups().observe(lifecycleOwner, response -> {
            activity.reloadGroups(response);
            getTaskList();
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
