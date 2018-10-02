package crew4dev.ru.next24h.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskItem {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String descr;
    private boolean isComplete;
    private String time;
    private boolean isRemind;

    public TaskItem(String title, String descr, boolean isComplete) {
        this.title = title;
        this.descr = descr;
        this.isComplete = isComplete;
        this.isRemind = false;
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

    public void setComplete(boolean value) {
        isComplete = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRemind() {
        return isRemind;
    }

    public void setRemind(boolean remind) {
        isRemind = remind;
    }
}
