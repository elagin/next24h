package crew4dev.ru.next24h;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import crew4dev.ru.next24h.data.TaskItem;

public class App extends Application {

    private static App instance;
    private static List<TaskItem> taskList;

    public static void addTask(TaskItem task) {
        taskList.add(task);
    }

    public static List<TaskItem> getTaskList() {
        return taskList;
    }

    public static void updateTask(int id, TaskItem taskItem) {
        taskList.set(id, taskItem);
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        taskList = new ArrayList<>();
    }
}
