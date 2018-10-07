package crew4dev.ru.next24h.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

    public void removeAt(int position) {
        App.db().tasks().delete(taskList.get(position));
        taskList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, taskList.size());
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private View item;
        private final TextView taskTitle;
        private final TextView taskDescr;
        private final TextView taskTime;

        private final CheckBox cbSelect;
        private final ImageView timeImage;

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
//                DBHandler dbHandler = new DBHandler(ctx);
//                List<WishMen> data = dbHandler.getWishmen();
                if (menuItem.getTitle().equals("Delete")) {
                    removeAt(getAdapterPosition());
                }
//                switch (menuItem.getItemId()) {
//                    case 0:
//                        //getAdapterPosition();
//                        removeAt(getAdapterPosition());
//                        //Do stuff
//                        break;
//                }
                return true;
            }
        };


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
/*
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu,menu);
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            int id = item.getItemId();
            default:
            return super.onContextItemSelected(item);
        }
    }
*/

        public TasksViewHolder(@NonNull View itemView, final OnViewHolderClickListener clickListener) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
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
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //int  choosedId1 = product.getId();
                    //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
                    return false;

                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(this.getAdapterPosition(), v.getId(), 0, "Delete");
            //MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
            //MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
            //Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }
    }
}
