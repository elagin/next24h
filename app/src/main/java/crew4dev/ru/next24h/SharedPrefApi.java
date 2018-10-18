package crew4dev.ru.next24h;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefApi {

    private static final String NAME_PREF = "APP_DATA";
    private static final String HIDE_COMPLETED_TASK = "HIDE_COMPLETED_TASK";
    private SharedPreferences sharedPref;

    public SharedPrefApi(Context context) {
        sharedPref = context.getSharedPreferences(NAME_PREF, Context.MODE_MULTI_PROCESS);
    }

    public synchronized boolean getHideCompletedTask() {
        return sharedPref.getBoolean(HIDE_COMPLETED_TASK, false);
    }

    public synchronized void setHideCompletedTask(boolean hideCompletedTask) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(HIDE_COMPLETED_TASK, hideCompletedTask);
        editor.apply();
    }
}
