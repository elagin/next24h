package crew4dev.ru.next24h.ui.models;

import android.arch.lifecycle.ViewModel;

import crew4dev.ru.next24h.ui.models.interfaces.DefaultModelContract;

public class DefaultModel extends ViewModel implements DefaultModelContract {
    //protected CollectDbApiContract dbApi;

    public DefaultModel(/*CollectDbApiContract dbApi*/) {
        //this.dbApi = dbApi;
    }

    @Override
    public void clearCollectItems() {

    }
}
