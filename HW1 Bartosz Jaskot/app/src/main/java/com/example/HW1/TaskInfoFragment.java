package com.example.HW1;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HW1.tasks.TaskList;


public class TaskInfoFragment extends Fragment implements View.OnClickListener{

    private TaskList.Task mDiplayedTask;
    public TaskInfoFragment() {
        // Required empty public constructor
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        FragmentActivity activity = getActivity ();
        super.onActivityCreated ( savedInstanceState );
        activity.findViewById(R.id.displayFragment).setVisibility(View.INVISIBLE);
        activity.findViewById(R.id.taskInfoImage).setOnClickListener(this);
        Intent intent = getActivity ().getIntent ();
        if(intent!=null)
        {
            TaskList.Task receivedTask = intent.getParcelableExtra ( MainActivity.taskExtra );
            if(receivedTask!= null)
            {
                displayTask ( receivedTask );
            }
        }
    }

    public void displayTask(TaskList.Task task)
    {
        FragmentActivity activity = getActivity ();
        (activity.findViewById ( R.id.displayFragment )).setVisibility ( View.VISIBLE );
        TextView taskInfoModel = activity.findViewById ( R.id.taskInfoModel);
        TextView taskInfoDescription = activity.findViewById ( R.id.taskInfoDescription );
        final ImageView taskInfoImage = activity.findViewById ( R.id.taskInfoImage );


        taskInfoModel.setText ( task.name);
        taskInfoDescription.setText ( "Name: "+ task.name + "\nSurname: " + task.surname + "\nPhone: " + task.phone +"\nBirthday: " + task.birthday);

        Drawable taskDrawable;
        taskDrawable = activity.getResources ().getDrawable ( R.drawable.awatar );
        taskInfoImage.setImageDrawable ( taskDrawable );

        mDiplayedTask = task;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_task_info, container, false );
    }

    @Override
    public void onClick(View v) { }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    { }
}
