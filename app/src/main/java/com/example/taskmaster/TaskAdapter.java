package com.example.taskmaster;

import android.util.Log;
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

    public  TaskAdapter (ArrayList<Task> allTaskData){
        this.allTaskData = allTaskData;

    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        public Task task;
        View itemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("my Adapter", "Element "+ getAdapterPosition() + " clicked");
                }
            });
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent , false);
        return  new TaskViewHolder(view);
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


    public interface onTaskListner{
        void  onTaskListener(int position);
    }

}
