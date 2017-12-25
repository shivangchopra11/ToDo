package com.example.shivang.todo1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
    private Paint p = new Paint();

    public ListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list,container,false);
        recyclerView = rootView.findViewById(R.id.list_todo);
        database = AppDatabase.getDatabase(getContext().getApplicationContext());

//        prepareSample();
        toDoList = database.todoDao().getAllTodos(false);
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
                if(direction == ItemTouchHelper.RIGHT) {
                    String curTitle = toDoList.get(position).title;
                    String descr = toDoList.get(position).description;
                    String date1 = toDoList.get(position).date;
                    String time1 = toDoList.get(position).time;
                    String cate = toDoList.get(position).category;
                    Boolean seta = toDoList.get(position).setAlarm;
                    Boolean don = toDoList.get(position).done;
                    ToDo cur1 = new ToDo(curTitle,descr,cate,date1,time1,seta);
                    cur1.done = true;
                    database.todoDao().updateTodo(cur1);
                    toDoList.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
                else {
                    database.todoDao().delete(toDoList.get(position));
                    toDoList.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
                if(toDoList.size()==0) {
                    final EmptyListFragment frag2 = new EmptyListFragment();
                    getFragmentManager().beginTransaction().replace(R.id.container,frag2).commit();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        return rootView;
    }



//    void prepareSample() {
//        database.todoDao().addTodo(new ToDo("Shivang", "hey","H","00/00/0000","00:00",false));
//        toDoList = database.todoDao().getAllTodos();
//        Log.v("TAG",toDoList.get(0).title);
//        database.todoDao().addTodo(new ToDo("Shivang Chopra", "hello","W","00/00/0000","00:00",true));
//        toDoList = database.todoDao().getAllTodos();
//        Log.v("TAG",toDoList.get(0).title);
////        mAdapter.notifyDataSetChanged();
//        mAdapter = new ToDoAdapter(toDoList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);
//    }
}
