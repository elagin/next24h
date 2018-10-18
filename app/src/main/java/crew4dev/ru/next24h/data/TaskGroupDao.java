package crew4dev.ru.next24h.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskGroupDao {
    @Insert()
    long insert(TaskGroup item);

    @Delete
    void delete(TaskGroup item);

    @Query("select * from taskGroups")
    List<TaskGroup> getGroups();
}