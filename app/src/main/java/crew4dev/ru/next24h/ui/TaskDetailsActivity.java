package crew4dev.ru.next24h.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import crew4dev.ru.next24h.App;
import crew4dev.ru.next24h.Constants;
import crew4dev.ru.next24h.R;
import crew4dev.ru.next24h.RemindManager;
import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.data.TaskItem;

import static crew4dev.ru.next24h.Constants.REMINDE_TIME_FORMAT;
import static crew4dev.ru.next24h.Tools.toCharSequenceArray;
import static crew4dev.ru.next24h.ui.GroupDialog.showNewGroupName;

public class TaskDetailsActivity extends AppCompatActivity {

    private static final String TAG = "TaskDetailsActivity";

    @BindView(R.id.editDetailsTitle)
    EditText editDetailsTitle;
    @BindView(R.id.editDetailsDescr)
    EditText editDetailsDescr;
    @BindView(R.id.currentDateTime)
    TextView remindTime;
    @BindView(R.id.check_remind)
    CheckBox checkRemind;
    @BindView(R.id.timeButton)
    ImageButton timeButton;

    @BindView(R.id.spinnerGroup)
    Spinner spinnerGroup;

    private TaskItem oldTaskItem;
    List<TaskGroup> groups = new ArrayList<>();
    private Menu menu;
    private final int NOTIFICATION_REMINDER_NIGHT = 10;
    private TaskGroup currentGroup;
    private CharSequence[] charSequences;

    final TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            remindTime.setText(String.format(REMINDE_TIME_FORMAT, hourOfDay, minute));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_details, menu);
        this.menu = menu;

        if (oldTaskItem == null) {
            menu.findItem(R.id.task_delete).setVisible(false);
        }
        menu.findItem(R.id.task_save).setEnabled(editDetailsTitle.getText().length() > 0);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null && getIntent().getExtras().get(Constants.ID_PARAM) != null) {
            Long taskId = getIntent().getLongExtra(Constants.ID_PARAM, 0);
            oldTaskItem = App.db().collectDao().getById(taskId);
            if (oldTaskItem == null) {
                Toast.makeText(this, "Задача не найдена", Toast.LENGTH_SHORT).show();
            }
        }

        if (oldTaskItem != null) {
            editDetailsTitle.setText(oldTaskItem.getTitle());
            editDetailsDescr.setText(oldTaskItem.getDescr());
            checkRemind.setChecked(oldTaskItem.isRemind());
            if (!checkRemind.isChecked()) {
                remindTime.setEnabled(false);
                timeButton.setEnabled(false);
            }
        }

        editDetailsTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                menu.findItem(R.id.task_save).setEnabled(s.length() > 0);
            }
        });

        checkRemind.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (checkRemind.isChecked()) {
                remindTime.setEnabled(true);
                timeButton.setEnabled(true);
                if (remindTime.getText().length() == 0) {
                    setTime(checkRemind);
                }
            } else {
                remindTime.setEnabled(false);
                timeButton.setEnabled(false);
            }
        });
        setInitialDateTime();
        //groups = App.db().collectDao().getGroups();
        setSpinner();
        spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == App.db().collectDao().groupCount()) {
                    createNewGroup();
                }
                Log.d(TAG, "onItemSelected: " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d(TAG, "onNothingSelected");
            }
        });
    }

    private int getGroupIndex(long groupId) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId() == groupId)
                return i;
        }
        return 0;
    }

    private void setSpinner() {
        String newName = "Новая категория";
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getName().equals(newName)) {
                groups.remove(i);
                break;
            }
        }

        TaskGroup newGroup = new TaskGroup();
        newGroup.setId(Integer.MAX_VALUE);
        newGroup.setName(newName);
        groups.add(newGroup);
        charSequences = toCharSequenceArray(groups);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, charSequences);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroup.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (oldTaskItem != null) {
            int index = getGroupIndex(oldTaskItem.getTaskGroupId());
            spinnerGroup.setSelection(index, false);
        } else if (currentGroup != null) {
            //oldTaskItem.setTaskGroupId(currentGroup.getId());
            int index = getGroupIndex(currentGroup.getId());
            spinnerGroup.setSelection(index, false);

        } else {
            spinnerGroup.setSelection(0, false);
        }
    }

    private void createNewGroup() {
        showNewGroupName(this, "", searchItem -> {});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.task_save) {
            TaskItem storedTask = new TaskItem();
            storedTask.setTitle(editDetailsTitle.getText().toString());
            storedTask.setDescr(editDetailsDescr.getText().toString());
            storedTask.setRemind(checkRemind.isChecked());
            storedTask.setTime(remindTime.getText().toString());
            TaskGroup group = groups.get(spinnerGroup.getSelectedItemPosition());
            storedTask.setTaskGroupId(group.getId());

            if (oldTaskItem != null) {
                storedTask.setId(oldTaskItem.getId());
                if (!checkRemind.isChecked() && oldTaskItem.isRemind())
                    storedTask.clearRemindTime();
                    RemindManager.cancelNotify(this, oldTaskItem.getId());
                App.db().collectDao().update(storedTask);
            } else
                App.db().collectDao().insert(storedTask);

            if (checkRemind.isChecked())
                RemindManager.setNotify(this, storedTask);
            finish();
        } else if (id == R.id.task_delete) {
            if (oldTaskItem != null) {
                if (checkRemind.isChecked())
                    RemindManager.cancelNotify(this, oldTaskItem.getId());
                App.db().collectDao().delete(oldTaskItem);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
//
//    private void notifService(String title) {
//        Intent notifyIntent = new Intent(this, MyReceiver.class);
//        notifyIntent.putExtra(Constants.COMMAND_DELETE_NOTIF, title);
//        //notifyIntent.putExtra(Constants.TASK_TITLE, item.getTitle());
//        PendingIntent pendingIntent = PendingIntent.getBroadcast
//                (this, NOTIFICATION_REMINDER_NIGHT, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        try {
//            pendingIntent.send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//        }
//
//    }

    /*
    public void sendNotification(String title, String body) {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this,
                0 // Request code
                    ,   i,
                PendingIntent.FLAG_ONE_SHOT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                //getString(R.string.default_notification_channel_id))
                "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pi);

        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }
*/
//    private void notif() {
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("Title")
//                        .setContentText("Notification text");
//
//        Notification notification = builder.build();
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(1, notification);
//    }

    public void setTime(View v) {
        String time = remindTime.getText().toString();
        String timeArray[] = time.split(":");
        if (timeArray.length == 2) {
            new TimePickerDialog(this, t,
                    Integer.valueOf(timeArray[0]),
                    Integer.valueOf(timeArray[1]), true)
                    .show();
            return;
        }
        Calendar dateAndTime = Calendar.getInstance();
        new TimePickerDialog(this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void setInitialDateTime() {
        if (oldTaskItem != null && oldTaskItem.getTime() != null)
            remindTime.setText(oldTaskItem.getTime());
    }

    public void doPositiveClick(String name) {
        currentGroup = new TaskGroup(name);
        currentGroup.setId(App.db().collectDao().insert(currentGroup));
        groups.add(currentGroup);
        if (oldTaskItem != null)
            oldTaskItem.setTaskGroupId(currentGroup.getId());
        setSpinner();
    }
}