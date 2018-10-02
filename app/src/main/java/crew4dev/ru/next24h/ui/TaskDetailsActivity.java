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
            if (oldTaskItem != null) {
                oldTaskItem.setTitle(editDetailsTitle.getText().toString());
                oldTaskItem.setDescr(editDetailsDescr.getText().toString());
                oldTaskItem.setRemind(checkRemind.isChecked());
                oldTaskItem.setTime(remindTime.getText().toString());
                App.db().tasks().update(oldTaskItem);
            } else {
                TaskItem taskItem = new TaskItem(editDetailsTitle.getText().toString(), editDetailsDescr.getText().toString(), false);
                App.db().tasks().insert(taskItem);
            }
            finish();
        } else if (id == R.id.task_delete) {
            if (oldTaskItem != null) {
                App.db().tasks().delete(oldTaskItem);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

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
