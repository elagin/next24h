package crew4dev.ru.next24h;

import crew4dev.ru.next24h.data.TaskItem;

public interface OnTaskListClickListener {
    boolean OnTaskListClick(TaskItem task, int selectedPos);
}