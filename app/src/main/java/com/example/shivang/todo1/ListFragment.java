package com.example.shivang.todo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivang on 22/12/17.
 */

public class ListFragment extends Fragment {
    RecyclerView recyclerView;
    List<ToDo> toDoList = new ArrayList<>();
    ToDoAdapter mAdapter;
    private AppDatabase database;

    public ListFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list,container,false);
        recyclerView = rootView.findViewById(R.id.list_todo);
        database = AppDatabase.getDatabase(getContext().getApplicationContext());

//        prepareSample();
        toDoList = database.todoDao().getAllTodos();
        mAdapter = new ToDoAdapter(toDoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
                if(toDoList.size()==0) {
                    final EmptyListFragment frag2 = new EmptyListFragment();
                    getFragmentManager().beginTransaction().replace(R.id.container,frag2).commit();
                }
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        return rootView;
    }



    void prepareSample() {
        database.todoDao().addTodo(new ToDo("Shivang", "hey","H","00/00/0000","00:00",false));
        toDoList = database.todoDao().getAllTodos();
        Log.v("TAG",toDoList.get(0).title);
        database.todoDao().addTodo(new ToDo("Shivang Chopra", "hello","W","00/00/0000","00:00",true));
        toDoList = database.todoDao().getAllTodos();
        Log.v("TAG",toDoList.get(0).title);
//        mAdapter.notifyDataSetChanged();
        mAdapter = new ToDoAdapter(toDoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
