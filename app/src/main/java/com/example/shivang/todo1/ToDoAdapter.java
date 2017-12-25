package com.example.shivang.todo1;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
        this.parent = parent;
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
        switch(item.getCategory()) {
            case "H":
                holder.tvCategory.setBackgroundResource(R.drawable.category_view_blue);
                break;
            case "W":
                holder.tvCategory.setBackgroundResource(R.drawable.category_view_green);
                break;
            case "C":
                holder.tvCategory.setBackgroundResource(R.drawable.category_view_orange);
                break;
            case "T":
                holder.tvCategory.setBackgroundResource(R.drawable.category_view_purple);
                break;
            case "P":
                holder.tvCategory.setBackgroundResource(R.drawable.category_view_red);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTodo.size();
    }


    static ViewGroup parent;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvDate,tvTime,tvCategory;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getLayoutPosition();
                    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                    View alertLayout = inflater.inflate(R.layout.activity_show_todo, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                    builder.setView(alertLayout);
                    final TextView showTitle,showDesc,showDate,showTime,showCat;
                    showTitle = alertLayout.findViewById(R.id.showTitle);
                    showDesc = alertLayout.findViewById(R.id.showDesc);
                    showDate = alertLayout.findViewById(R.id.showDate);
                    showTime = alertLayout.findViewById(R.id.showTime);
                    showCat = alertLayout.findViewById(R.id.showCat);
                    switch(mTodo.get(pos).getCategory()) {
                        case "H":
                            showCat.setBackgroundResource(R.drawable.category_view_blue);
                            break;
                        case "W":
                            showCat.setBackgroundResource(R.drawable.category_view_green);
                            break;
                        case "C":
                            showCat.setBackgroundResource(R.drawable.category_view_orange);
                            break;
                        case "T":
                            showCat.setBackgroundResource(R.drawable.category_view_purple);
                            break;
                        case "P":
                            showCat.setBackgroundResource(R.drawable.category_view_red);
                            break;
                    }
                    showTitle.setText(mTodo.get(pos).getTitle());
//                    showTitle.setText("Hello");
//                    Log.d("TAG", mTodo.get(pos).getTitle());
                    showDesc.setText(mTodo.get(pos).getDescription());
                    showDate.setText(mTodo.get(pos).getDate());
                    showTime.setText(mTodo.get(pos).getTime());
                    showCat.setText(mTodo.get(pos).getCategory());
//                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            //Do nothing
//                        }
//                    });
                    Button showDone = alertLayout.findViewById(R.id.showDone);
                    final AlertDialog dialog = builder.create();
                    showDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}