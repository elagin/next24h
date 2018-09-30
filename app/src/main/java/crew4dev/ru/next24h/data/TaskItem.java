package crew4dev.ru.next24h.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskItem {

    @PrimaryKey(autoGenerate = true)
    private long id;
    String title;
    String descr;
    boolean isComplete;

    public TaskItem(String title, String descr, boolean isComplete) {
        this.title = title;
        this.descr = descr;
        this.isComplete = isComplete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete() {
        isComplete = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
