package crew4dev.ru.next24h;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import crew4dev.ru.next24h.data.TaskItem;
import crew4dev.ru.next24h.ui.TaskDetailsActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.workLayout)
    CoordinatorLayout workTable;

    private static List<TaskItem> taskList = new ArrayList<>();

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

        taskList.add(new TaskItem("Завтрак", "Не забыть купить кашку"));
        taskList.add(new TaskItem("Иголочка", "Резинка, молнии"));
        taskList.add(new TaskItem("Масло", "Iponee"));

        //Создаем RecyclerView
        RecyclerView recyclerView = workTable.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TasksAdapter(this));

        TasksAdapter adapter = (TasksAdapter) ((RecyclerView) workTable.findViewById(R.id.taskRecyclerView)).getAdapter();
        if (adapter.getItemCount() > 0) {
            adapter.clearItems();
        }
        adapter.setItems(taskList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
