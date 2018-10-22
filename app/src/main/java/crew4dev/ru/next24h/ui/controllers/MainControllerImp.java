package crew4dev.ru.next24h.ui.controllers;

import android.arch.lifecycle.LifecycleOwner;

import javax.inject.Inject;

import crew4dev.ru.next24h.SharedPrefApi;
import crew4dev.ru.next24h.ui.controllers.interfaces.MainControllerContract;
import crew4dev.ru.next24h.ui.interfaces.MainActivityContract;
import crew4dev.ru.next24h.ui.models.MainModel;

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
        model.reloadTaskList();
        model.getTasks().observe(lifecycleOwner, activity::reloadItems);
    }

    public void getTaskList() {
        model.reloadTaskList();
    }
}
