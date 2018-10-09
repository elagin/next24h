package crew4dev.ru.next24h.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import crew4dev.ru.next24h.App;
import crew4dev.ru.next24h.Constants;
import crew4dev.ru.next24h.R;
import crew4dev.ru.next24h.RemindManager;
import crew4dev.ru.next24h.data.TaskItem;

public class TaskDetailsActivity extends AppCompatActivity {

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

    private TaskItem oldTaskItem;
    private Menu menu;
    private final int NOTIFICATION_REMINDER_NIGHT = 10;

    final TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            remindTime.setText(hourOfDay + ":" + minute);
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
            oldTaskItem = App.db().tasks().getById(taskId);
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

        checkRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
            }
        });
        setInitialDateTime();
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

            if (oldTaskItem != null) {
                storedTask.setId(oldTaskItem.getId());
                if (!checkRemind.isChecked() && oldTaskItem.isRemind())
                    RemindManager.cancelNotify(this, oldTaskItem.getId());
                App.db().tasks().update(storedTask);
            } else
                App.db().tasks().insert(storedTask);
            if (checkRemind.isChecked())
                RemindManager.setNotify(this, storedTask);
            finish();
        } else if (id == R.id.task_delete) {
            if (oldTaskItem != null) {
                if (checkRemind.isChecked())
                    RemindManager.cancelNotify(this, oldTaskItem.getId());
                App.db().tasks().delete(oldTaskItem);
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
}
