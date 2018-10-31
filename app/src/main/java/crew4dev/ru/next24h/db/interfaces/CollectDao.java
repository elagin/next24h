package crew4dev.ru.next24h.db.interfaces;

import android.arch.persistence.room.*;
import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.data.TaskItem;
import io.reactivex.Single;

import java.util.List;

@Dao
public abstract class CollectDao {

    @Query("select * from tasks where id = :id")
    public abstract TaskItem getById(long id);

    @Query("select * from tasks")
    public abstract Single<List<TaskItem>> getTasks();

    @Insert
    public abstract Long insert(TaskItem item);

    @Update
    public abstract void update(TaskItem item);

    @Delete
    public abstract void delete(TaskItem item);

    @Query("DELETE from tasks where isComplete = 1")
    public abstract void deleteComplete();

    //==========

    @Insert
    public abstract long insert(TaskGroup item);

    @Update
    public abstract void update(TaskGroup item);

    @Delete
    public abstract void delete(TaskGroup item);

    @Query("select * from taskGroups")
    public abstract Single<List<TaskGroup>> getGroups();

    @Query("select count(*) from taskGroups")
    public abstract Integer groupCount();
}
