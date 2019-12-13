package com.ec327.scheducation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ec327.scheducation.db.TaskContract;
import com.ec327.scheducation.db.TaskDbHelper;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    public static final String TAG = "TaskActivity";

    // t<var name> = Task related variables
    private TaskDbHelper tHelper;
    private ListView tTaskListView;
    private ArrayAdapter<String> tAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // CONTROLS EVERYTHING
        super.onCreate(savedInstanceState);
        tHelper = new TaskDbHelper(this);
        tTaskListView = (ListView) findViewById(R.id.list_tasks);

        // FIX THIS TO SHOW activity_menu INSTEAD
        setContentView(R.layout.activity_task); // Sets Initial View

        // Initializing Database
        SQLiteDatabase db = tHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TABLE_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TABLE_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }
        cursor.close();
        db.close();

        // Updating UI
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_menu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // adds task to db
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a New Task")
                        // Set Label System Here
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                //Log.d(TAG, "Task to add: " + task); // EDITED OUT FOR DB
                                SQLiteDatabase db = tHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TABLE_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                // Updating UI
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() { // Updates UI after adding task to db
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = tHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TABLE_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TABLE_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (tAdapter == null) {
            tAdapter = new ArrayAdapter<>(this,
                    R.layout.item_task, // Where to put the tasks in
                    R.id.task_title, // Where to put the String of data
                    taskList); // Looks inside db for data
            tTaskListView.setAdapter(tAdapter); // set it as adapter of
                                                // ListView instance in Task Window
        }
        else {
            tAdapter.clear();
            tAdapter.addAll(taskList);
            tAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    public void deleteTask(View view) { // Deletes task from db
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = tHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TABLE_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }
}
