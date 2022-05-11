package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    List<Task> allTaskData = new ArrayList<>();
    private  OnTaskListner mOnTaskListener;

    public  TaskAdapter (ArrayList<Task> allTaskData){
        this.allTaskData = allTaskData;

    }

    public  TaskAdapter (ArrayList<Task> allTaskData, OnTaskListner mOnTaskListner){
        this.allTaskData = allTaskData;
        this.mOnTaskListener = mOnTaskListner;

    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public Task task;
        View itemView;
        OnTaskListner onTaskListner;

        public TaskViewHolder(@NonNull View itemView, OnTaskListner onTaskListner) {
            super(itemView);
            this.itemView = itemView;
            this.onTaskListner = onTaskListner;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            onTaskListner.onTaskListener(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent , false);
        return  new TaskViewHolder(view, mOnTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.task = allTaskData.get(position);
        TextView title = holder.itemView.findViewById(R.id.title_frag);
        TextView body = holder.itemView.findViewById(R.id.body_frag);
        TextView state = holder.itemView.findViewById(R.id.state_frag);

        title.setText(holder.task.getTitle());
        body.setText(holder.task.getBody());
        state.setText(holder.task.getState().toString());

    }

    @Override
    public int getItemCount() {
        return allTaskData.size();
    }


    public interface OnTaskListner {
        void  onTaskListener(int position);
    }

}
