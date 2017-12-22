package com.example.shivang.todo1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shivang on 22/12/17.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDo> mTodo;
    public ToDoAdapter(List<ToDo> todos) {
        mTodo = todos;
    }
    @Override
    public ToDoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ToDoAdapter.MyViewHolder holder, int position) {
        ToDo item = mTodo.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDate.setText(item.getDate());
        holder.tvTime.setText(item.getTime());
        holder.tvCategory.setText(item.getCategory());
    }

    @Override
    public int getItemCount() {
        return mTodo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvDate,tvTime,tvCategory;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}