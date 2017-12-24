package com.example.shivang.todo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class TasksDone extends AppCompatActivity {
    RecyclerView rv2;
    ToDoAdapter mAdapter;
    List<ToDo> toDoList = new ArrayList<>();
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_done);
        setTitle("Tasks Completed");
        rv2 = findViewById(R.id.rv2);
        database = AppDatabase.getDatabase(this.getApplicationContext());
        toDoList = database.todoDao().getAllTodos(true);
        mAdapter = new ToDoAdapter(toDoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv2.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        rv2.setAdapter(mAdapter);
        rv2.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        rv2.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN|ItemTouchHelper.UP,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                ToDo fromExpense = toDoList.get(from);
                ToDo toExpense = toDoList.get(to);
                toDoList.set(from,toExpense);
                toDoList.set(to,fromExpense);
                mAdapter.notifyItemMoved(from,to);

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                database.todoDao().delete(toDoList.get(position));
                toDoList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });

        itemTouchHelper.attachToRecyclerView(rv2);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv2);

    }
}
