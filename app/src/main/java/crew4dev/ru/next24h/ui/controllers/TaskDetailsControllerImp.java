package crew4dev.ru.next24h.ui.controllers;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import java.util.List;

import javax.inject.Inject;

import crew4dev.ru.next24h.SharedPrefApi;
import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.ui.controllers.interfaces.TaskDetailsControllerContract;
import crew4dev.ru.next24h.ui.interfaces.TaskDetailsActivityContract;
import crew4dev.ru.next24h.ui.models.TaskDetailsModel;
import io.reactivex.annotations.Nullable;

public class TaskDetailsControllerImp extends DefaultControllerImp implements TaskDetailsControllerContract {
    private final TaskDetailsActivityContract activity;
    private final TaskDetailsModel model;

    @Inject
    public TaskDetailsControllerImp(SharedPrefApi sharedPrefApi, TaskDetailsActivityContract activity, TaskDetailsModel model, LifecycleOwner lifecycleOwner) {
        super(sharedPrefApi, activity, model, lifecycleOwner);
        this.activity = activity;
        this.model = model;
        subscribeToModel();
    }

    @Override
    public void subscribeToModel() {
        super.subscribeToModel();
        model.reloadGroupList();
        model.getGroups().observe(lifecycleOwner, new Observer<List<TaskGroup>>() {
            @Override
            public void onChanged(@Nullable List<TaskGroup> response) {
                activity.reloadGroups(response);
            }
        });
    }

    @Override
    public void getGroups() {
        model.reloadGroupList();
    }
}