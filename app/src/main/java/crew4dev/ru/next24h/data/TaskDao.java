package crew4dev.ru.next24h.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("select * from tasks where id = :id")
    TaskItem getById(long id);

    @Insert()
    long insert(TaskItem item);

    @Update
    void update(TaskItem item);

    @Delete
    void delete(TaskItem item);

    @Query("select * from tasks")
    List<TaskItem> getTasks();

    @Query("select count() from tasks")
    int count();
}
