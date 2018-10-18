package crew4dev.ru.next24h.ui.controllers;

import android.arch.lifecycle.LifecycleOwner;

import crew4dev.ru.next24h.SharedPrefApi;
import crew4dev.ru.next24h.ui.controllers.interfaces.DefaultControllerContract;
import crew4dev.ru.next24h.ui.interfaces.DefaultActivityContract;
import crew4dev.ru.next24h.ui.models.interfaces.DefaultModelContract;

public class DefaultControllerImp implements DefaultControllerContract {
    protected SharedPrefApi sharedPrefApi;
    protected LifecycleOwner lifecycleOwner;
    private DefaultActivityContract defaultActivity;
    private DefaultModelContract defaultModel;

    public DefaultControllerImp(SharedPrefApi sharedPrefApi, DefaultActivityContract defaultActivity, DefaultModelContract defaultModel, LifecycleOwner lifecycleOwner) {
        this.sharedPrefApi = sharedPrefApi;
        this.lifecycleOwner = lifecycleOwner;
        this.defaultActivity = defaultActivity;
        this.defaultModel = defaultModel;

    }

    @Override
    public void subscribeToModel() {
        /*
        defaultModel.getWait().observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean){
                    defaultActivity.showWaitDialog();
                }else{
                    defaultActivity.hideWaitDialog();
                }
            }
        });
        defaultModel.getClearAllData().observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null && aBoolean){
                    OnClearAllData();
                }else{
                    defaultActivity.showErrorDbDialog();
                }
            }
        });
        defaultModel.getClearCollectData().observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    OnClearCollectData();
                }else{
                    defaultActivity.showErrorDbDialog();
                }
            }
        });
        defaultModel.getClearCollectItems().observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    OnClearCollectItemsData();
                }else{
                    defaultActivity.showErrorDbDialog();
                }
            }
        });
        */
    }
}
