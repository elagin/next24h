package crew4dev.ru.next24h.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;

import static crew4dev.ru.next24h.Constants.REMINDE_TIME_FORMAT;

@Entity(tableName = "tasks")
public class TaskItem implements Comparable<TaskItem> {

    private static final String TAG = "TaskItem";

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String descr;
    private boolean isComplete;
    @Deprecated
    private String time;
    private boolean isRemind;
    private Integer hour;
    private Integer minute;
    private long taskGroupId;

    public TaskItem() {}

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
        if (hour != null && minute != null)
            return String.format(REMINDE_TIME_FORMAT, hour, minute);
        else {
            return "";
        }
    }

    public void setTime(String time) {
        if (time != null) {
            String[] data = time.split(":");
            if (data.length == 2) {
                hour = Integer.valueOf(data[0]);
                minute = Integer.valueOf(data[1]);
            }
        }
    }

    public boolean isRemind() {
        return isRemind;
    }

    public void setRemind(boolean remind) {
        isRemind = remind;
        if (!remind) {
            hour = null;
            minute = null;
        }
    }

    public void clearRemindTime() {
        hour = null;
        minute = null;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    @Override
    public int compareTo(@NonNull TaskItem o) {
        Log.d(TAG, "Compare: " + this.title + " / " + o.title);
        if (isComplete && !o.isComplete) {
            return 1;
        } else if (!isComplete && o.isComplete) {
            return -1;
        }

        if (isRemind) {
            if (!o.isRemind) {
                return 1;
            } else if (hour > o.hour) {
                return 1;
            } else if (hour < o.hour) {
                return -1;
            } else {
                if (minute > o.minute)
                    return 1;
                else
                    return -1;
            }
        } else {
            return -1;
        }
    }

    public long getTaskGroupId() {
        return taskGroupId;
    }

    public void setTaskGroupId(long taskGroupId) {
        this.taskGroupId = taskGroupId;
    }
}
