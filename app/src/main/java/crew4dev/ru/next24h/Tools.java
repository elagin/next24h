package crew4dev.ru.next24h;

import java.util.List;

import crew4dev.ru.next24h.data.TaskGroup;

public class Tools {
    public static CharSequence[] toCharSequenceArray(List<TaskGroup> groups) {
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            return groups.stream().map(b -> b.getName()).toArray(CharSequence[]::new);
        } else {
            CharSequence[] bookTitles = new CharSequence[groups.size()];
            int index = 0;
            for (TaskGroup group : groups) {
                bookTitles[index++] = group.getName();
            }
            return bookTitles;
        }
    }
}
