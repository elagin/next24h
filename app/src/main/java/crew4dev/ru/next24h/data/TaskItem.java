package crew4dev.ru.next24h.data;

public class TaskItem {

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
}
