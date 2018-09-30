package crew4dev.ru.next24h.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
    private Integer taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null && getIntent().getExtras().get(Constants.ID_PARAM) != null) {
            taskId = getIntent().getIntExtra(Constants.ID_PARAM, 0);
            if (App.getTaskList().size() > taskId) {
                oldTaskItem = App.getTaskList().get(taskId);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_details, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (oldTaskItem != null) {
            editDetailsTitle.setText(oldTaskItem.getTitle());
            editDetailsDescr.setText(oldTaskItem.getDescr());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.task_save) {
            if (oldTaskItem != null) {
                oldTaskItem.setTitle(editDetailsTitle.getText().toString());
                oldTaskItem.setDescr(editDetailsDescr.getText().toString());
                App.updateTask(taskId, oldTaskItem);
            } else {
                TaskItem taskItem = new TaskItem(editDetailsTitle.getText().toString(), editDetailsDescr.getText().toString(), false);
                App.addTask(taskItem);
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
