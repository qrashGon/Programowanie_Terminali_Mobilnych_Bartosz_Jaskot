package com.example.HW1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.HW1.tasks.TaskList;

public class TaskFragment extends Fragment {


    private int mColumnCount = 1;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private OnListFragmentInteractionListener mListener;
    private MyTaskRecyclerViewAdapter mRecyclerViewAdapter;

    public TaskFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        if (getArguments () != null) {
            mColumnCount = getArguments ().getInt ( ARG_COLUMN_COUNT );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_task_list, container, false );

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext ();
            RecyclerView recyclerView = ( RecyclerView ) view;
            recyclerView.setLayoutManager ( new LinearLayoutManager ( context ) );
            mRecyclerViewAdapter = new MyTaskRecyclerViewAdapter ( TaskList.ITEMS, mListener );
            recyclerView.setAdapter ( mRecyclerViewAdapter );
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnDeleteClick(int position);
        void OnListFragmentClickInteraction(TaskList.Task task, int position); //click interaction
    }

    public void notifyDataChange()
    {
        mRecyclerViewAdapter.notifyDataSetChanged ();

    }
}
