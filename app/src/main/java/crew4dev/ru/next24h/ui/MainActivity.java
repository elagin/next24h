package crew4dev.ru.next24h.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import crew4dev.ru.next24h.App;
import crew4dev.ru.next24h.Constants;
import crew4dev.ru.next24h.R;
import crew4dev.ru.next24h.SharedPrefApi;
import crew4dev.ru.next24h.data.TaskGroup;
import crew4dev.ru.next24h.data.TaskItem;
import crew4dev.ru.next24h.di.components.DaggerControllerComponent;
import crew4dev.ru.next24h.di.modules.ActivityModule;
import crew4dev.ru.next24h.ui.controllers.interfaces.MainControllerContract;
import crew4dev.ru.next24h.ui.interfaces.MainActivityContract;

public class MainActivity extends AppCompatActivity implements MainActivityContract, OnTaskListClickListener {

    private static final String TAG = "MainActivity";
    //private boolean isHideCompletedTask = false;

    @BindView(R.id.workLayout)
    CoordinatorLayout workTable;

    @BindView(R.id.taskRecyclerView)
    RecyclerView taskRecyclerView;

    @BindView(R.id.textEmptyTask)
    TextView textEmptyTask;

    @Inject
    public SharedPrefApi sharedPrefApi;

    @Inject
    public MainControllerContract mainController;

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
        menu.findItem(R.id.hide_completed_task).setChecked(sharedPrefApi.getHideCompletedTask());
        return true;
    }

    public void reloadItems(List<TaskItem> totalItems) {
        List<TaskItem> showItems = new ArrayList<>();

        TasksAdapter adapter = (TasksAdapter) taskRecyclerView.getAdapter();
        if (Objects.requireNonNull(adapter).getItemCount() > 0) {
            adapter.clearItems();
        }
        if (totalItems.size() > 0) {
            for (TaskItem item : totalItems) {
                if ((sharedPrefApi.getHideCompletedTask() && !item.isComplete()) || !sharedPrefApi.getHideCompletedTask()) {
                    if (isVisibleGroup(item.getTaskGroupId()))
                        showItems.add(item);
                }
            }
            adapter.setItems(showItems);
        }

        if (showItems.isEmpty()) {
            textEmptyTask.setVisibility(View.VISIBLE);
            taskRecyclerView.setVisibility(View.GONE);
        } else {
            textEmptyTask.setVisibility(View.GONE);
            taskRecyclerView.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groups = App.db().collectDao().getGroups();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerControllerComponent.builder().activityModule(new ActivityModule(this)).utilsComponent(App.getApplication().getUtilsComponent()).build().inject(this);

        RecyclerView recyclerView = workTable.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TasksAdapter adapter = new TasksAdapter(this);
        adapter.setOnCollectGroupClickListener(this);
        recyclerView.setAdapter(adapter);
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
            case R.id.hide_completed_task:
                if (item.isChecked()) {
                    item.setChecked(false);
                    sharedPrefApi.setHideCompletedTask(false);
                } else {
                    item.setChecked(true);
                    sharedPrefApi.setHideCompletedTask(true);
                }
                mainController.getTaskList();
                break;
            case R.id.filter_group:
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
            App.db().collectDao().update(group);
        }
        mainController.getTaskList();
    }

    public MainControllerContract getMainController() {
        return mainController;
    }

    @Override
    public void closeActivity() {

    }
}