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

import butterknife.BindView;
import butterknife.ButterKnife;
import crew4dev.ru.next24h.App;
import crew4dev.ru.next24h.Constants;
import crew4dev.ru.next24h.R;
import crew4dev.ru.next24h.data.TaskItem;

public class MainActivity extends AppCompatActivity implements OnTaskListClickListener {

    @BindView(R.id.workLayout)
    CoordinatorLayout workTable;

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
        if (adapter.getItemCount() > 0) {
            adapter.clearItems();
        }
        adapter.setItems(App.db().tasks().getTasks());
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
}
