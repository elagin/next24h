package crew4dev.ru.next24h.ui.controllers;

import android.arch.lifecycle.LifecycleOwner;

import javax.inject.Inject;

import crew4dev.ru.next24h.SharedPrefApi;
import crew4dev.ru.next24h.ui.controllers.interfaces.MainControllerContract;
import crew4dev.ru.next24h.ui.interfaces.MainActivityContract;
import crew4dev.ru.next24h.ui.models.MainModel;

public class MainControllerImp extends DefaultControllerImp implements MainControllerContract {
    private MainActivityContract mainActivity;
    private MainModel mainModel;

    @Inject
    public MainControllerImp(SharedPrefApi sharedPrefApi, MainActivityContract mainActivity, MainModel mainModel, LifecycleOwner lifecycleOwner) {
        super(sharedPrefApi, mainActivity, mainModel, lifecycleOwner);
        this.mainActivity = mainActivity;
        this.mainModel = mainModel;
        subscribeToModel();
    }

    @Override
    public void subscribeToModel() {

    }

    @Override
    public void loadUser() {

    }
}
