package crew4dev.ru.next24h.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import crew4dev.ru.next24h.App;
import crew4dev.ru.next24h.R;
import crew4dev.ru.next24h.data.TaskItem;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private final List<TaskItem> taskList = new ArrayList<>();
    private final Context context;
    private OnTaskListClickListener listener;
    private final OnViewHolderClickListener holderClickListener = new OnViewHolderClickListener() {
        @Override
        public void OnViewHolderClick(int current) {
            setSelectedPos(current);
        }
    };

    public TasksAdapter(Context context) {
        this.context = context;
    }

    private boolean onBind;

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);
        return new TasksViewHolder(view, holderClickListener);
    }

    public void setItems(Collection<TaskItem> items) {
        taskList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, final int position) {
        final TaskItem item = taskList.get(position);
        holder.bind(taskList.get(position));
        if (item.isComplete()) {
            holder.taskTitle.setTextColor(Color.rgb(0, 210, 0));
        } else {
            holder.taskTitle.setTextColor(Color.rgb(0, 00, 0));
        }
        holder.cbSelect.setChecked(item.isComplete());
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setComplete(!item.isComplete());
                notifyDataSetChanged();
                App.db().tasks().update(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void clearItems() {
        taskList.clear();
        notifyDataSetChanged();
    }

    public void setOnCollectGroupClickListener(OnTaskListClickListener groupClickListener) {
        listener = groupClickListener;
    }

    private void setSelectedPos(int selected) {
        if (listener != null && taskList.size() > 0 && listener.OnTaskListClick(taskList.get(selected), selected)) {
            //updateSelectedPos(selected);
        }
    }

    class TasksViewHolder extends RecyclerView.ViewHolder {
        private View item;
        private final TextView taskTitle;
        private final TextView taskDescr;
        private final TextView taskTime;

        private final CheckBox cbSelect;
        private final ImageView timeImage;

        public TasksViewHolder(@NonNull View itemView, final OnViewHolderClickListener clickListener) {
            super(itemView);
            this.taskTitle = itemView.findViewById(R.id.taskTitle);
            this.taskDescr = itemView.findViewById(R.id.taskDescr);
            this.taskTime = itemView.findViewById(R.id.taskTime);
            this.cbSelect = itemView.findViewById(R.id.checkBox);
            this.timeImage = itemView.findViewById(R.id.timeImage);
            item = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.OnViewHolderClick(getAdapterPosition());
                }
            });
        }

        void bind(TaskItem item) {
            taskTitle.setText(item.getTitle());

            if (item.getDescr().length() > 0) {
                taskDescr.setText(item.getDescr());
                taskDescr.setVisibility(View.VISIBLE);
            } else
                taskDescr.setVisibility(View.GONE);

            if (item.isRemind()) {
                timeImage.setVisibility(View.VISIBLE);
                taskTime.setVisibility(View.VISIBLE);
                taskTime.setText(item.getTime());
            } else {
                timeImage.setVisibility(View.GONE);
                taskTime.setVisibility(View.GONE);
            }
        }
    }
}
