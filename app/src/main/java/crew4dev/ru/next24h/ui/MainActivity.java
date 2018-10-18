package crew4dev.ru.next24h.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import crew4dev.ru.next24h.App;
import crew4dev.ru.next24h.Constants;
import crew4dev.ru.next24h.R;
import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.data.TaskItem;

import static crew4dev.ru.next24h.App.db;

public class MainActivity extends AppCompatActivity implements OnTaskListClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.workLayout)
    CoordinatorLayout workTable;

    List<TaskGroup> groups = new ArrayList<>();

    private boolean isVisibleGroup(Long id) {
        for (TaskGroup group : groups) {
            if (group.getId() == id)
                return group.isVisible();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void reloadItems() {
        TasksAdapter adapter = (TasksAdapter) ((RecyclerView) workTable.findViewById(R.id.taskRecyclerView)).getAdapter();
        if (Objects.requireNonNull(adapter).getItemCount() > 0) {
            adapter.clearItems();
        }

        List<TaskItem> totalItems = db().tasks().getTasks();
        List<TaskItem> showItems = new ArrayList<>();
        for (TaskItem item : totalItems) {
            if (isVisibleGroup(item.getTaskGroupId()))
                showItems.add(item);
        }
        adapter.setItems(showItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groups = App.db().taskGroups().getGroups();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecyclerView recyclerView = workTable.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TasksAdapter adapter = new TasksAdapter(this);
        adapter.setOnCollectGroupClickListener(this);
        recyclerView.setAdapter(adapter);

        reloadItems();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.add_task:
                startActivity(new Intent(MainActivity.this, TaskDetailsActivity.class));
                break;
            case R.id.filter_group:
/*
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // Set a title for alert dialog
                builder.setTitle("Choose a color...");

                // Initializing an array of colors
                final String[] colors = new String[]{
                        "Red",
                        "Green",
                        "Blue",
                        "Yellow"
                };

                List<String> groups = new ArrayList<String>();
                groups.add("Автозапчасти");
                groups.add("Магазин");
              // Set the list of items for alert dialog
                final String[] charSequences = groups.toArray(new String[groups.size()]);
                builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedColor = Arrays.asList(charSequences).get(which);
                        // Set the layout background color as user selection
                        //rl.setBackgroundColor(Color.parseColor(selectedColor));
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                */
/*
                DialogFragment dialog = new GroupDialog();
                List<String> groups = new ArrayList<String>();
                groups.add("Автозапчасти");
                groups.add("Магазин");
                ((GroupDialog) dialog).setData(groups);
                //dialog.setTargetFragment(MainActivity.this, 0);
                Bundle args = new Bundle();
                args.putString(GroupDialog.ARG_TITLE, "title");
                args.putString(GroupDialog.ARG_MESSAGE, "message");
                dialog.setArguments(args);
                //dialog.setTargetFragment(this, YES_NO_CALL);
                dialog.show(getSupportFragmentManager(), "tag");
*/
                //GroupDialog.showSelectGroups(this, searchItems ,listener);
                //List<String> groups = new ArrayList<String>();
                //List<TaskGroup> groups = App.db().taskGroups().getGroups();

                //groups.add("Автозапчасти");
                //groups.add("Магазин");
                GroupDialog.showSelectGroups(this, groups, searchItem -> {});
                break;
        }
        if (id == R.id.action_settings) {
            return true;
        } else
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean OnTaskListClick(TaskItem task, int selectedPos) {
        Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
        intent.putExtra(Constants.ID_PARAM, task.getId());
        startActivity(intent);
        return true;
    }

    public void doPositiveClick(boolean[] mSelectedItems) {
        for (int i = 0; i < groups.size(); i++) {
            TaskGroup group = groups.get(i);
            if (mSelectedItems[i])
                group.setVisible(true);
            else
                group.setVisible(false);
            App.db().taskGroups().update(group);
        }
        reloadItems();
    }

    /*
        private void cancelNotify(long itemId){
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent notifyIntent = new Intent(getApplicationContext(), MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getApplicationContext(), (int)(itemId), notifyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(pendingIntent);
        }

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
                    Intent notifyIntent = new Intent(this, MyReceiver.class);
                    ///notifyIntent.putExtra(Constants.COMMAND_CREATE_NOTIF, item.getTitle());
                    notifyIntent.putExtra(Constants.TASK_TITLE, item.getTitle());
                    notifyIntent.putExtra(Constants.TASK_DESC, item.getDescr());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast
                            (this, (int)item.getId(), notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.notify();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, taskTime.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
                }
            }
        }
    */
//    private void setNotifes() {
//        List<TaskItem> tasks = App.db().tasks().getTasks();
//        for (TaskItem item : tasks) {
//            if (!item.isComplete() && item.isRemind() && item.getTime() != null) {
//                RemindManager.setNotify(this, item);
//            }
//        }
//    }
}
