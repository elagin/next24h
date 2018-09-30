package crew4dev.ru.next24h.data;

public class TaskItem {

    String title;
    String descr;

    public TaskItem(String title, String descr) {
        this.title = title;
        this.descr = descr;
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
}
