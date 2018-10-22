package crew4dev.ru.next24h.db.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.data.TaskItem;
import io.reactivex.Single;

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
