package com.example.shivang.todo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    public ListFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list,container,false);
        recyclerView = rootView.findViewById(R.id.list_todo);
        mAdapter = new ToDoAdapter(toDoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareSample();
        return rootView;
    }

    void prepareSample() {
        ToDo t1 = new ToDo("Shivang", "hey","H","00/00/0000","00:00",false);
        toDoList.add(t1);
        mAdapter.notifyDataSetChanged();
    }
}
