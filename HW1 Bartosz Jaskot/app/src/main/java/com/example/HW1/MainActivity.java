package com.example.HW1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.HW1.tasks.Delete;
import com.example.HW1.tasks.TaskList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener, Delete.OnDeleteDialogInteractionListener{

    private String mCurrentPhotoPath;
    public static final String taskExtra = "taskExtra";
    private int currentItemPosition = -1;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private TaskList.Task currentTask;
    private final String CURRENT_TASK_KEY = "CurrentTask";
    private final String TASKS_JSON_FILE = "tasks.json";
    public static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentTask = savedInstanceState.getParcelable(CURRENT_TASK_KEY);
        }
        restoreFromJson();

    }

    private void startSecondActivity(TaskList.Task task, int position)
    {
        Intent intent =  new Intent ( this, TaskInfoActivity.class );
        intent.putExtra ( taskExtra, (Parcelable) task );
        startActivity ( intent );
    }

    @Override
    public void OnDeleteClick(int position) {
        showDeleteDialog ();
        currentItemPosition=position;
    }



    private void showDeleteDialog()
    {
        Delete.newInstance().show(getSupportFragmentManager (), getString ( R.string.hello_blank_fragment ));
    }




    @Override
    public void OnListFragmentClickInteraction(TaskList.Task task, int position)
    {
        currentTask = task;
        if (getResources ().getConfiguration ().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            displayTaskInFragment( task );
        } else {
            startSecondActivity ( task, position );
        }
    }

    private void displayTaskInFragment(TaskList.Task task)
    {
        TaskInfoFragment taskInfoFragment = ((TaskInfoFragment) getSupportFragmentManager ().findFragmentById ( R.id.displayFragment ));
        if(taskInfoFragment != null)
        {
            taskInfoFragment.displayTask ( task );
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < TaskList.ITEMS.size ())
        {
            TaskList.removeItem(currentItemPosition);
            ((TaskFragment) getSupportFragmentManager ().findFragmentById ( R.id.taskFragment )).notifyDataChange ();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void addNextItem(View view) {
        Intent intent = new Intent ( getApplicationContext (), AddContact.class );
        startActivityForResult ( intent, SECOND_ACTIVITY_REQUEST_CODE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        ((TaskFragment) getSupportFragmentManager ().findFragmentById ( R.id.taskFragment )).notifyDataChange ();
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            Intent intent = new Intent(getApplicationContext(), AddContact.class);
            intent.putExtra("photo", mCurrentPhotoPath);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(currentTask != null)
            outState.putParcelable ( CURRENT_TASK_KEY, currentTask );
        super.onSaveInstanceState ( outState );
    }

    @Override
    protected void onResume()
    {
        super.onResume ();
        ((TaskFragment) getSupportFragmentManager ().findFragmentById ( R.id.taskFragment )).notifyDataChange ();
        if(getResources ().getConfiguration ().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if(currentTask != null)
            {
                displayTaskInFragment ( currentTask );
            }
        }
    }

    private void saveTasksToJson()
    {
        Gson gson = new Gson ();
        String listJson = gson.toJson ( TaskList.ITEMS );
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput ( TASKS_JSON_FILE, MODE_PRIVATE );
            FileWriter writer = new FileWriter ( outputStream.getFD ());
            writer.write ( listJson );
            writer.close ();

        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public void restoreFromJson(){
        FileInputStream inputStream;
        int DEFAULT_BUFFER_SIZE = 10000;
        Gson gson = new Gson ();
        String readJson;

        try{
            inputStream = openFileInput ( TASKS_JSON_FILE );
            FileReader reader = new FileReader ( inputStream.getFD () );
            char[] buf = new char[DEFAULT_BUFFER_SIZE];
            int n;
            StringBuilder builder = new StringBuilder (  );
            while ((n = reader.read (buf)) >= 0)
            {
                String tmp = String.valueOf ( buf );
                String substring = (n<DEFAULT_BUFFER_SIZE) ? tmp.substring ( 0, n ) : tmp;
                builder.append ( substring );
            }
            reader.close ();
            readJson = builder.toString ();
            Type collectionType = new TypeToken<List<TaskList.Task>>(){}.getType ();
            List<TaskList.Task> o = gson.fromJson ( readJson, collectionType );
            if(o != null)
            {
                TaskList.clearList ();
                for(TaskList.Task task : o)
                    TaskList.addItem ( task );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public void call(View view){
        setContentView(R.layout.calling_dialog);
    }

    @Override
    public void onDestroy()
    {
        saveTasksToJson ();
        super.onDestroy();
    }
}
