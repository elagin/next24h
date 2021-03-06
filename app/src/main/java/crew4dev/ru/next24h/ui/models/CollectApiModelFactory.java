package crew4dev.ru.next24h.ui.models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import crew4dev.ru.next24h.db.interfaces.CollectDbApiContract;

public class CollectApiModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final CollectDbApiContract collectDbApi;

    public CollectApiModelFactory(CollectDbApiContract collectDbApi) {
        super();
        this.collectDbApi = collectDbApi;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MainModel.class) {
            return (T) new MainModel(collectDbApi);
        } else if (modelClass == TaskDetailsModel.class) {
            return (T) new TaskDetailsModel(collectDbApi);
        } else {
            return super.create(modelClass);
        }
    }
}
