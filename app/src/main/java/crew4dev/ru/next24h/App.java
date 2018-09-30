package crew4dev.ru.next24h;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import crew4dev.ru.next24h.data.TaskItem;

public class App extends Application {

    private static App app;
    private static List<TaskItem> taskList;

    @Override
    public void onCreate() {
        app = this;
        super.onCreate();
        taskList = new ArrayList<>();
    }

    public void AddTask(TaskItem task){
        taskList.add(task);
    }
}
