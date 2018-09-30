package crew4dev.ru.next24h;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import crew4dev.ru.next24h.data.TaskItem;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private final List<TaskItem> taskList = new ArrayList<>();
    private final Context context;

    public TasksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);
        return new TasksViewHolder(view);
    }

    public void setItems(Collection<TaskItem> items) {
        taskList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        //if (taskList.get(position).isComplete()) {
        //holder.taskTitle.setTextColor(Color.rgb(0, 210, 0));
        holder.bind(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        if (taskList != null)
            return taskList.size();
        return 0;
    }

    public void clearItems() {
        taskList.clear();
        notifyDataSetChanged();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskTitle;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            this.taskTitle = itemView.findViewById(R.id.taskTitle);
        }

        void bind(TaskItem item) {
            String name = item.getTitle() + " : " + item.getDescr();
            taskTitle.setText(name);
        }
    }
}
