package com.example.lpy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lpy.myapplication.BaseActivity;
import com.example.lpy.myapplication.R;
import com.example.lpy.myapplication.custom.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DragRecyclerActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private RadioButton listView_type, gridView_type;
    private RecyclerView recyclerView;

    private List<Integer> list = new ArrayList<>();
    private DragRecyclerAdapter adapter;
    private ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycler);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        listView_type = (RadioButton) findViewById(R.id.listView_type);
        gridView_type = (RadioButton) findViewById(R.id.gridView_type);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(DragRecyclerActivity.this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(15));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.listView_type:
                        recyclerView.setLayoutManager(new LinearLayoutManager(DragRecyclerActivity.this));
                        break;
                    case R.id.gridView_type:
                        recyclerView.setLayoutManager(new GridLayoutManager(DragRecyclerActivity.this, 2));
                        break;
                }
            }
        });
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
        adapter = new DragRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //首先回调的方法 返回int表示是否监听该方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;//拖拽
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//侧滑删除
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(list, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(list, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                list.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

            @Override
            public boolean isLongPressDragEnabled() {
                //是否可拖拽
                return true;
            }

            @Override
            public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
                return true;
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    public class DragRecyclerAdapter extends RecyclerView.Adapter<DragRecyclerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DragRecyclerActivity.this).inflate(R.layout.item_drag_recycler_layout, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.item_tv.setText(list.get(position) + "");
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView item_tv;

            public ViewHolder(View itemView) {
                super(itemView);
                item_tv = (TextView) itemView.findViewById(R.id.item_tv);
            }
        }
    }

}
