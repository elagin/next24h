package crew4dev.ru.next24h.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import crew4dev.ru.next24h.Constants;
import crew4dev.ru.next24h.R;
import crew4dev.ru.next24h.data.TaskItem;

import static crew4dev.ru.next24h.App.db;

public class MainActivity extends AppCompatActivity implements OnTaskListClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.workLayout)
    CoordinatorLayout workTable;

    private final int NOTIFICATION_REMINDER_NIGHT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TaskDetailsActivity.class));
            }
        });

        RecyclerView recyclerView = workTable.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TasksAdapter adapter = new TasksAdapter(this);
        adapter.setOnCollectGroupClickListener(this);
        recyclerView.setAdapter(adapter);
        //RemindManager.setAllNotifes(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        TasksAdapter adapter = (TasksAdapter) ((RecyclerView) workTable.findViewById(R.id.taskRecyclerView)).getAdapter();
        if (Objects.requireNonNull(adapter).getItemCount() > 0) {
            adapter.clearItems();
        }
        adapter.setItems(db().tasks().getTasks());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean OnTaskListClick(TaskItem task, int selectedPos) {
        Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
        intent.putExtra(Constants.ID_PARAM, task.getId());
        startActivity(intent);
        return true;
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
