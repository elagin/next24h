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
import java.util.Collections;
import java.util.List;

import crew4dev.ru.next24h.App;
import crew4dev.ru.next24h.R;
import crew4dev.ru.next24h.RemindManager;
import crew4dev.ru.next24h.data.TaskItem;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private static final String TAG = "TasksAdapter";

    private final List<TaskItem> taskList = new ArrayList<>();
    private final Context context;
    private OnTaskListClickListener listener;
    private final OnViewHolderClickListener holderClickListener = this::setSelectedPos;

    public TasksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);
        return new TasksViewHolder(view, holderClickListener);
    }

    public void setItems(Collection<TaskItem> items) {
        taskList.addAll(items);
        Collections.sort(taskList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final TasksViewHolder holder, final int position) {
        final TaskItem item = taskList.get(position);
        holder.bind(taskList.get(position));
        if (item.isComplete()) {
            holder.taskTitle.setTextColor(Color.rgb(100, 100, 100));
            holder.taskDescr.setVisibility(View.GONE);
        } else {
            holder.taskTitle.setTextColor(Color.rgb(0, 0, 0));
            holder.taskDescr.setVisibility(View.VISIBLE);
        }
        holder.cbSelect.setChecked(item.isComplete());
        holder.cbSelect.setOnClickListener(v -> {
            if (holder.cbSelect.isChecked()) {
                item.setComplete(true);
                if (item.isRemind()) {
                    RemindManager.cancelNotify(context, item.getId());
                    item.setRemind(false);
                }
            } else {
                item.setComplete(false);
            }
            Collections.sort(taskList);
            //item.setComplete(!item.isComplete());
            notifyDataSetChanged();
            App.db().collectDao().update(item);
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
        TaskItem item = taskList.get(position);
        if (!item.isComplete() && item.isRemind()) {
            RemindManager.cancelNotify(context, item.getId());
        }
        App.db().collectDao().delete(item);
        taskList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, taskList.size());
        Collections.sort(taskList);
    }

    /*
        private void setNotify(TaskItem item) {
            final Calendar how = Calendar.getInstance();
            Calendar taskTime = (Calendar) how.clone();
            if (!item.isComplete() && item.isRemind() && item.getTime() != null) {
                String[] data = item.getTime().split(":");
                Integer hours;
                Integer minutes;
                if (data.length == 2) {
                    hours = Integer.valueOf(data[0]);
                    minutes = Integer.valueOf(data[1]);
                    taskTime.set(Calendar.HOUR, hours);
                    taskTime.set(Calendar.MINUTE, minutes);
                    if (taskTime.after(how)) {
                        Log.d(TAG, item.getTitle() + " cегодня в " + String.format("%1$tA %1$tb %1$td %1$tY at %1$tI:%1$tM %1$Tp", taskTime));
                    } else {
                        taskTime.add(Calendar.DAY_OF_MONTH, 1);
                        Log.d(TAG, item.getTitle() + " завтра в " + String.format("%1$tA %1$tb %1$td %1$tY at %1$tI:%1$tM %1$Tp", taskTime));
                    }
                    Intent notifyIntent = new Intent(context, MyReceiver.class);
                    ///notifyIntent.putExtra(Constants.COMMAND_CREATE_NOTIF, item.getTitle());
                    notifyIntent.putExtra(Constants.TASK_TITLE, item.getTitle());
                    notifyIntent.putExtra(Constants.TASK_DESC, item.getDescr());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast
                            (context, (int) item.getId(), notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.notify();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, taskTime.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
                }
            }
        }

        private void cancelNotify(long itemId) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent notifyIntent = new Intent(context.getApplicationContext(), MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context.getApplicationContext(), (int) (itemId), notifyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(pendingIntent);
        }
    */
    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private View item;
        private final TextView taskTitle;
        private final TextView taskDescr;
        private final TextView taskTime;

        private final CheckBox cbSelect;
        private final ImageView timeImage;

        private final MenuItem.OnMenuItemClickListener onEditMenu = menuItem -> {
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
            itemView.setOnClickListener(view -> clickListener.OnViewHolderClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                //int  choosedId1 = product.getId();
                //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
                return false;

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
