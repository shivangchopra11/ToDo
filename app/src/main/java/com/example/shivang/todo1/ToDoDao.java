package com.example.shivang.todo1;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by shivang on 22/12/17.
 */

@Dao
public interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTodo(ToDo todo);

    @Query("select * from todo where done=:status")
    public List<ToDo> getAllTodos(boolean status);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTodo(ToDo todo);

    @Query("delete from todo")
    void removeAllTodos();

    @Delete
    void delete(ToDo todo);
}
