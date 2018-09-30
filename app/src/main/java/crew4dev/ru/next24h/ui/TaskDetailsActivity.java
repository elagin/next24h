package crew4dev.ru.next24h.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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

    private TaskItem oldTaskItem;
    private Menu menu;

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

    }

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

//    @Override
//    protected void onResume() {
//        super.onResume();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.task_save) {
            if (oldTaskItem != null) {
                oldTaskItem.setTitle(editDetailsTitle.getText().toString());
                oldTaskItem.setDescr(editDetailsDescr.getText().toString());
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
}
