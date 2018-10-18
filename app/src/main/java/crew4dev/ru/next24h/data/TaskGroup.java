package crew4dev.ru.next24h.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "taskGroups")
public class TaskGroup {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private boolean isVisible = true;

    public TaskGroup() {}

    public TaskGroup(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
